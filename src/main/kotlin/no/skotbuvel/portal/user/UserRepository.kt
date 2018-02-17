package no.skotbuvel.portal.user

import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.Sequences.USER_ID_SEQ
import no.skotbuvel.portal.jooq.Sequences.USER_ROLE_ID_SEQ
import no.skotbuvel.portal.jooq.tables.Role.ROLE
import no.skotbuvel.portal.jooq.tables.User.USER
import no.skotbuvel.portal.jooq.tables.UserRole.USER_ROLE
import no.skotbuvel.portal.role.Role
import no.skotbuvel.portal.util.JavaTimeUtil
import org.jooq.Record
import org.jooq.Result
import org.jooq.TransactionalRunnable

class UserRepository(private val dbHelper: DbHelper) {

    private val selectParameters = listOf(
            USER.ID,
            USER.FIRST_NAME,
            USER.LAST_NAME,
            USER.EMAIL,
            USER.PHONE,
            USER.CREATED_BY,
            USER.CREATED_DATE,
            ROLE.ID,
            ROLE.NAME,
            ROLE.DESCRIPTION
    )

    fun findAll(): List<User> {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(USER)
                .leftJoin(USER_ROLE)
                .on(USER.ID.eq(USER_ROLE.USER_ID))
                .and(USER_ROLE.ACTIVE.eq(true))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                .fetchGroups(USER.ID)
                .values
                .map { mapUserWithRoles(it) }
    }

    fun findByEmail(email: String): User {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(USER)
                .leftJoin(USER_ROLE)
                .on(USER.ID.eq(USER_ROLE.USER_ID))
                .and(USER_ROLE.ACTIVE.eq(true))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                .where(USER.EMAIL.eq(email))
                .fetchGroups(USER.ID)
                .values
                .map { mapUserWithRoles(it) }
                .first()
    }

    fun findById(id: Int): User {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(USER)
                .leftJoin(USER_ROLE)
                .on(USER.ID.eq(USER_ROLE.USER_ID))
                .and(USER_ROLE.ACTIVE.eq(true))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                .where(USER.ID.eq(id))
                .fetchGroups(USER.ID)
                .values
                .map { mapUserWithRoles(it) }
                .first()
    }

    fun save(userRegistration: UserRegistration, createdBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            dslContext.insertInto(USER,
                    USER.ID,
                    USER.ORIGINAL_ID,
                    USER.ACTIVE,
                    USER.FIRST_NAME,
                    USER.LAST_NAME,
                    USER.EMAIL,
                    USER.PHONE,
                    USER.CREATED_BY,
                    USER.CREATED_DATE
            ).values(
                    dslContext.nextval(USER_ID_SEQ).toInt(),
                    dslContext.currval(USER_ID_SEQ).toInt(),
                    true,
                    userRegistration.firstName,
                    userRegistration.lastName,
                    userRegistration.email,
                    userRegistration.phone,
                    createdBy,
                    JavaTimeUtil.nowEuropeOslo()
            )
                    .execute()
        })
    }

    fun updateRoles(userRoleRegistration: UserRoleRegistration, createdBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            val existingRoleIds = dslContext
                    .select(USER_ROLE.ROLE_ID)
                    .from(USER_ROLE)
                    .where(USER_ROLE.USER_ID.eq(userRoleRegistration.userId))
                    .and(USER_ROLE.ACTIVE.eq(true))
                    .fetch()
                    .map { it.value1() }

            val deletedRoleIds = existingRoleIds.filter { i -> !userRoleRegistration.roleIds.contains(i) }

            if (deletedRoleIds.isNotEmpty()) {
                dslContext.update(USER_ROLE)
                        .set(USER_ROLE.ACTIVE, false)
                        .set(USER_ROLE.CHANGED_BY, createdBy)
                        .set(USER_ROLE.CHANGED_DATE, JavaTimeUtil.nowEuropeOslo())
                        .where(USER_ROLE.USER_ID.eq(userRoleRegistration.userId))
                        .and(USER_ROLE.ROLE_ID.`in`(deletedRoleIds))
                        .execute()
            }

            val newRoleIds = userRoleRegistration.roleIds.filter { i -> !existingRoleIds.contains(i) }

            newRoleIds.forEach {
                dslContext.insertInto(USER_ROLE,
                        USER_ROLE.ID,
                        USER_ROLE.USER_ID,
                        USER_ROLE.ROLE_ID,
                        USER_ROLE.ACTIVE,
                        USER_ROLE.CREATED_BY,
                        USER_ROLE.CREATED_DATE
                ).values(
                        dslContext.nextval(USER_ROLE_ID_SEQ).toInt(),
                        userRoleRegistration.userId,
                        it,
                        true,
                        createdBy,
                        JavaTimeUtil.nowEuropeOslo()
                )
                        .execute()
            }
        })
    }

    private fun mapUserWithRoles(result: Result<Record>): User {
        val roles = mapRoles(result)

        val record = result.first()

        return User(
                id = record[USER.ID],
                firstName = record[USER.FIRST_NAME],
                lastName = record[USER.LAST_NAME],
                email = record[USER.EMAIL],
                phone = record[USER.PHONE],
                roles = roles,
                createdBy = record[USER.CREATED_BY],
                createdDate = record[USER.CREATED_DATE].toZonedDateTime()
        )
    }

    private fun mapRoles(result: Result<Record>): List<Role> {
        return result
                .filter { it[ROLE.ID] != null }
                .map {
                    Role(
                            id = it[ROLE.ID],
                            name = it[ROLE.NAME],
                            description = it[ROLE.DESCRIPTION]
                    )
                }
    }
}