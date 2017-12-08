package no.skotbuvel.portal.subject

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.Sequences.SUBJECT_ID_SEQ
import no.skotbuvel.portal.jooq.Sequences.SUBJECT_ROLE_ID_SEQ
import no.skotbuvel.portal.jooq.tables.Role.ROLE
import no.skotbuvel.portal.jooq.tables.Subject.SUBJECT
import no.skotbuvel.portal.jooq.tables.SubjectRole.SUBJECT_ROLE
import no.skotbuvel.portal.role.Role
import no.skotbuvel.portal.util.JavaTimeUtil
import org.jooq.Record
import org.jooq.Result
import org.jooq.TransactionalRunnable
import java.util.concurrent.TimeUnit

class SubjectRepository(private val dbHelper: DbHelper) {

    private val selectParameters = listOf(
            SUBJECT.ID,
            SUBJECT.FIRST_NAME,
            SUBJECT.LAST_NAME,
            SUBJECT.EMAIL,
            SUBJECT.PHONE,
            SUBJECT.CREATED_BY,
            SUBJECT.CREATED_DATE,
            ROLE.ID,
            ROLE.NAME,
            ROLE.DESCRIPTION,
            ROLE.ACTIVE,
            ROLE.CREATED_BY,
            ROLE.CREATED_DATE
    )

    private val subjectByEmailCache = loadingCacheByEmail()
    private val subjectByIdCache = loadingCacheById()

    fun findAll(): List<Subject> {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(SUBJECT)
                .leftJoin(SUBJECT_ROLE)
                .on(SUBJECT.ID.eq(SUBJECT_ROLE.SUBJECT_ID))
                .and(SUBJECT_ROLE.ACTIVE.eq(true))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(SUBJECT_ROLE.ROLE_ID))
                .fetchGroups(SUBJECT.ID)
                .values
                .map { mapSubjectWithRoles(it) }
    }

    fun findByEmail(email: String): Subject {
        return subjectByEmailCache[email]
    }

    fun findById(id: Int): Subject {
        return subjectByIdCache[id]
    }

    fun save(subjectRegistration: SubjectRegistration, createdBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            dslContext.insertInto(SUBJECT,
                    SUBJECT.ID,
                    SUBJECT.ORIGINAL_ID,
                    SUBJECT.ACTIVE,
                    SUBJECT.FIRST_NAME,
                    SUBJECT.LAST_NAME,
                    SUBJECT.EMAIL,
                    SUBJECT.PHONE,
                    SUBJECT.CREATED_BY,
                    SUBJECT.CREATED_DATE
            ).values(
                    dslContext.nextval(SUBJECT_ID_SEQ).toInt(),
                    dslContext.currval(SUBJECT_ID_SEQ).toInt(),
                    true,
                    subjectRegistration.firstName,
                    subjectRegistration.lastName,
                    subjectRegistration.email,
                    subjectRegistration.phone,
                    createdBy,
                    JavaTimeUtil.nowEuropeOslo()
            )
                    .execute()
        })
    }

    fun updateRoles(subjectRoleRegistration: SubjectRoleRegistration, createdBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            val existingRoleIds = dslContext
                    .select(SUBJECT_ROLE.ROLE_ID)
                    .from(SUBJECT_ROLE)
                    .where(SUBJECT_ROLE.ACTIVE.eq(true))
                    .fetch()
                    .map { it.value1() }

            val deletedRoleIds = existingRoleIds.filter { i -> !subjectRoleRegistration.roleIds.contains(i) }

            if (deletedRoleIds.isNotEmpty()) {
                dslContext.update(SUBJECT_ROLE)
                        .set(SUBJECT_ROLE.ACTIVE, false)
                        .set(SUBJECT_ROLE.DELETED_BY, createdBy)
                        .set(SUBJECT_ROLE.DELETED_DATE, JavaTimeUtil.nowEuropeOslo())
                        .where(SUBJECT_ROLE.SUBJECT_ID.eq(subjectRoleRegistration.subjectId))
                        .and(SUBJECT_ROLE.ROLE_ID.`in`(deletedRoleIds))
                        .execute()
            }

            val newRoleIds = subjectRoleRegistration.roleIds.filter { i -> !existingRoleIds.contains(i) }

            newRoleIds.forEach {
                dslContext.insertInto(SUBJECT_ROLE,
                        SUBJECT_ROLE.ID,
                        SUBJECT_ROLE.SUBJECT_ID,
                        SUBJECT_ROLE.ROLE_ID,
                        SUBJECT_ROLE.ACTIVE,
                        SUBJECT_ROLE.CREATED_BY,
                        SUBJECT_ROLE.CREATED_DATE
                ).values(
                        dslContext.nextval(SUBJECT_ROLE_ID_SEQ).toInt(),
                        subjectRoleRegistration.subjectId,
                        it,
                        true,
                        createdBy,
                        JavaTimeUtil.nowEuropeOslo()
                )
                        .execute()
            }
        })

        invalidateCaches(subjectRoleRegistration.subjectId)
    }

    private fun invalidateCaches(subjectId: Int) {
        subjectByEmailCache.invalidate(subjectByIdCache[subjectId])
        subjectByIdCache.invalidate(subjectId)
    }

    private fun mapSubjectWithRoles(result: Result<Record>): Subject {
        val roles = mapRoles(result)

        val record = result.first()

        return Subject(
                id = record[SUBJECT.ID],
                firstName = record[SUBJECT.FIRST_NAME],
                lastName = record[SUBJECT.LAST_NAME],
                email = record[SUBJECT.EMAIL],
                phone = record[SUBJECT.PHONE],
                roles = roles,
                createdBy = record[SUBJECT.CREATED_BY],
                createdDate = record[SUBJECT.CREATED_DATE].toZonedDateTime()
        )
    }

    private fun mapRoles(result: Result<Record>): List<Role> {
        return result
                .filter { it[ROLE.ID] != null }
                .map {
                    Role(
                            id = it[ROLE.ID],
                            name = it[ROLE.NAME],
                            description = it[ROLE.DESCRIPTION],
                            active = it[ROLE.ACTIVE],
                            createdBy = it[ROLE.CREATED_BY],
                            createdDate = it[ROLE.CREATED_DATE].toZonedDateTime()
                    )
                }
    }

    private fun loadingCacheByEmail() = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build(
                    object : CacheLoader<String, Subject>() {
                        override fun load(key: String): Subject {
                            return dbHelper.dslContext()
                                    .select(selectParameters)
                                    .from(SUBJECT)
                                    .leftJoin(SUBJECT_ROLE)
                                    .on(SUBJECT.ID.eq(SUBJECT_ROLE.SUBJECT_ID))
                                    .and(SUBJECT_ROLE.ACTIVE.eq(true))
                                    .leftJoin(ROLE)
                                    .on(ROLE.ID.eq(SUBJECT_ROLE.ROLE_ID))
                                    .where(SUBJECT.EMAIL.eq(key))
                                    .fetchGroups(SUBJECT.ID)
                                    .values
                                    .map { mapSubjectWithRoles(it) }
                                    .first()
                        }
                    }
            )

    private fun loadingCacheById() = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build(
                    object : CacheLoader<Int, Subject>() {
                        override fun load(key: Int): Subject {
                            return dbHelper.dslContext()
                                    .select(selectParameters)
                                    .from(SUBJECT)
                                    .leftJoin(SUBJECT_ROLE)
                                    .on(SUBJECT.ID.eq(SUBJECT_ROLE.SUBJECT_ID))
                                    .and(SUBJECT_ROLE.ACTIVE.eq(true))
                                    .leftJoin(ROLE)
                                    .on(ROLE.ID.eq(SUBJECT_ROLE.ROLE_ID))
                                    .where(SUBJECT.ID.eq(key))
                                    .fetchGroups(SUBJECT.ID)
                                    .values
                                    .map { mapSubjectWithRoles(it) }
                                    .first()
                        }
                    }
            )
}