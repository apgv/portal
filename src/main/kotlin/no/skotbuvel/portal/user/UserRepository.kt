package no.skotbuvel.portal.user

import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.tables.Role.ROLE
import no.skotbuvel.portal.jooq.tables.User.USER
import no.skotbuvel.portal.jooq.tables.UserRole.USER_ROLE
import org.jooq.Record
import org.jooq.Result

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
            ROLE.NAME
    )

    fun findAll(): List<User> {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(USER)
                .leftJoin(USER_ROLE)
                .on(USER.ID.eq(USER_ROLE.USER_ID))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                .fetchGroups(USER.ID)
                .values
                .map { mapUserWithRoles(it) }
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
                            createdBy = it[ROLE.CREATED_BY],
                            createdDate = it[ROLE.CREATED_DATE].toZonedDateTime()
                    )
                }
    }
}