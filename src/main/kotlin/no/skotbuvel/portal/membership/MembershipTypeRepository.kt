package no.skotbuvel.portal.membership

import no.skotbuvel.portal.DbUtil.dslContext
import org.jooq.no.skotbuvel.portal.tables.MembershipType.MEMBERSHIP_TYPE

class MembershipTypeRepository {

    fun findAllActive(): List<MembershipType> {
        val dslContext = dslContext()

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