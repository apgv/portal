package no.skotbuvel.portal.user

import java.time.ZonedDateTime

class Role(
        val id: Int,
        val name: String,
        val createdBy: String,
        val createdDate: ZonedDateTime
)