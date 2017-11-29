package no.skotbuvel.portal.membershiptype

import org.jooq.DSLContext
import org.jooq.no.skotbuvel.portal.tables.MembershipType.MEMBERSHIP_TYPE

class MembershipTypeRepository(private val dslContext: DSLContext) {

    fun findAllActive(): List<MembershipType> {
        return dslContext
                .select(
                        MEMBERSHIP_TYPE.ID,
                        MEMBERSHIP_TYPE.ACTIVE,
                        MEMBERSHIP_TYPE.TYPE,
                        MEMBERSHIP_TYPE.YEAR,
                        MEMBERSHIP_TYPE.PRICE
                )
                .from(MEMBERSHIP_TYPE)
                .where(MEMBERSHIP_TYPE.ACTIVE.eq(true))
                .fetch()
                .map { toMembershipType(it) }
                .toList()
    }
}