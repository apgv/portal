package no.skotbuvel.portal.subject

import no.skotbuvel.portal.role.Role
import java.time.ZonedDateTime

data class Subject(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String?,
        val roles: List<Role>,
        val createdBy: String,
        val createdDate: ZonedDateTime
)