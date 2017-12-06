package no.skotbuvel.portal.role

import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.tables.Role.ROLE
import no.skotbuvel.portal.subject.Role
import org.jooq.Record

class RoleRepository(private val dbHelper: DbHelper) {

    fun findAll(): List<Role> {
        return dbHelper.dslContext()
                .select(
                        ROLE.ID,
                        ROLE.NAME,
                        ROLE.ACTIVE,
                        ROLE.CREATED_BY,
                        ROLE.CREATED_DATE
                )
                .from(ROLE)
                .fetch()
                .map { mapRole(it) }
    }

    private fun mapRole(record: Record) =
            Role(
                    id = record[ROLE.ID],
                    name = record[ROLE.NAME],
                    active = record[ROLE.ACTIVE],
                    createdBy = record[ROLE.CREATED_BY],
                    createdDate = record[ROLE.CREATED_DATE].toZonedDateTime()
            )
}