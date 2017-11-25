package no.skotbuvel.portal.membershiptype

import org.jooq.Record
import org.jooq.no.skotbuvel.portal.tables.MembershipType.MEMBERSHIP_TYPE

data class MembershipType(
        val id: Int,
        val active: Boolean,
        val type: String,
        val year: Int,
        val price: Int
)

fun toMembershipType(record: Record) =
        MembershipType(
                id = record[MEMBERSHIP_TYPE.ID],
                active = record[MEMBERSHIP_TYPE.ACTIVE],
                type = record[MEMBERSHIP_TYPE.TYPE],
                year = record[MEMBERSHIP_TYPE.YEAR],
                price = record[MEMBERSHIP_TYPE.PRICE]
        )