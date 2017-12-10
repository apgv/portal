package no.skotbuvel.portal.role

import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.tables.Role.ROLE
import org.jooq.Record

class RoleRepository(private val dbHelper: DbHelper) {

    fun findAll(): List<Role> {
        return dbHelper.dslContext()
                .select(
                        ROLE.ID,
                        ROLE.NAME,
                        ROLE.DESCRIPTION
                )
                .from(ROLE)
                .fetch()
                .map { mapRole(it) }
    }

    private fun mapRole(record: Record) =
            Role(
                    id = record[ROLE.ID],
                    name = record[ROLE.NAME],
                    description = record[ROLE.DESCRIPTION]
            )
}