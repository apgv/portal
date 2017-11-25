package no.skotbuvel.portal.membership

import java.time.LocalDate

data class MembershipRegistration(
        val personId: Int,
        val membershipTypeId: Int,
        val paymentDate: LocalDate
)