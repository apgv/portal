package no.skotbuvel.portal.user

import no.skotbuvel.portal.role.Role
import java.time.ZonedDateTime

data class User(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String?,
        val roles: List<Role>,
        val createdBy: String,
        val createdDate: ZonedDateTime
)