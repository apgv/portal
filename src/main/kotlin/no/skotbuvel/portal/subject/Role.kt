package no.skotbuvel.portal.subject

import java.time.ZonedDateTime

class Role(
        val id: Int,
        val name: String,
        val description: String,
        val active: Boolean,
        val createdBy: String,
        val createdDate: ZonedDateTime
)