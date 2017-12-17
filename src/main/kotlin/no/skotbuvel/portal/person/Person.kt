package no.skotbuvel.portal.person

import no.skotbuvel.portal.membership.MembershipInfo
import java.time.ZonedDateTime

data class Person(
        val id: Int,
        val fullName: String,
        val email: String,
        val phone: String,
        val address: String,
        val memberships: List<MembershipInfo>,
        val createdBy: String,
        val createdDate: ZonedDateTime
)