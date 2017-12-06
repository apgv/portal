package no.skotbuvel.portal.membershiptype

import java.time.ZonedDateTime

data class MembershipType(
        val id: Int,
        val active: Boolean,
        val type: String,
        val year: Int,
        val price: Int,
        val createdBy: String,
        val createdDate: ZonedDateTime
)