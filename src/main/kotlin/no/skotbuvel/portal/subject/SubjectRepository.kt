package no.skotbuvel.portal.subject

import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.Sequences.SUBJECT_ID_SEQ
import no.skotbuvel.portal.jooq.tables.Role.ROLE
import no.skotbuvel.portal.jooq.tables.Subject.SUBJECT
import no.skotbuvel.portal.jooq.tables.SubjectRole.SUBJECT_ROLE
import no.skotbuvel.portal.util.JavaTimeUtil
import org.jooq.Record
import org.jooq.Result
import org.jooq.TransactionalRunnable

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

    fun findAll(): List<Subject> {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(SUBJECT)
                .leftJoin(SUBJECT_ROLE)
                .on(SUBJECT.ID.eq(SUBJECT_ROLE.SUBJECT_ID))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(SUBJECT_ROLE.ROLE_ID))
                .fetchGroups(SUBJECT.ID)
                .values
                .map { mapSubjectWithRoles(it) }
    }

    fun findByEmail(email: String): Subject {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(SUBJECT)
                .leftJoin(SUBJECT_ROLE)
                .on(SUBJECT.ID.eq(SUBJECT_ROLE.SUBJECT_ID))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(SUBJECT_ROLE.ROLE_ID))
                .where(SUBJECT.EMAIL.eq(email))
                .fetchGroups(SUBJECT.ID)
                .values
                .map { mapSubjectWithRoles(it) }
                .first()
    }

    fun findById(id: Int): Subject {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(SUBJECT)
                .leftJoin(SUBJECT_ROLE)
                .on(SUBJECT.ID.eq(SUBJECT_ROLE.SUBJECT_ID))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(SUBJECT_ROLE.ROLE_ID))
                .where(SUBJECT.ID.eq(id))
                .fetchGroups(SUBJECT.ID)
                .values
                .map { mapSubjectWithRoles(it) }
                .first()
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
}