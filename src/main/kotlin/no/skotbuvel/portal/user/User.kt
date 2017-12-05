package no.skotbuvel.portal.user

import java.time.ZonedDateTime

class User(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String,
        val roles: List<Role>,
        val createdBy: String,
        val createdDate: ZonedDateTime
)