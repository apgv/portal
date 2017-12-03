package no.skotbuvel.portal.membershiptype

import no.skotbuvel.portal.config.DbConfig
import no.skotbuvel.portal.jooq.tables.MembershipType.MEMBERSHIP_TYPE

class MembershipTypeRepository {

    fun findAllActive(): List<MembershipType> {
        return DbConfig.dslContext()
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