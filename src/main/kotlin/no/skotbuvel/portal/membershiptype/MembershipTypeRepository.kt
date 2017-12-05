package no.skotbuvel.portal.membershiptype

import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.Sequences.MEMBERSHIP_TYPE_ID_SEQ
import no.skotbuvel.portal.jooq.tables.MembershipType.MEMBERSHIP_TYPE
import no.skotbuvel.portal.util.JavaTimeUtil
import org.jooq.TransactionalRunnable

class MembershipTypeRepository(private val dbHelper: DbHelper) {

    fun findAllActive(): List<MembershipType> {
        return dbHelper.dslContext()
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

    fun save(membershipTypeRegistration: MembershipTypeRegistration, createdBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            dslContext.insertInto(MEMBERSHIP_TYPE,
                    MEMBERSHIP_TYPE.ID,
                    MEMBERSHIP_TYPE.ACTIVE,
                    MEMBERSHIP_TYPE.TYPE,
                    MEMBERSHIP_TYPE.YEAR,
                    MEMBERSHIP_TYPE.PRICE,
                    MEMBERSHIP_TYPE.CREATED_BY,
                    MEMBERSHIP_TYPE.CREATED_DATE
            ).values(
                    dslContext.nextval(MEMBERSHIP_TYPE_ID_SEQ).toInt(),
                    true,
                    membershipTypeRegistration.type,
                    membershipTypeRegistration.year,
                    membershipTypeRegistration.price,
                    createdBy,
                    JavaTimeUtil.nowEuropeOslo()
            )
                    .execute()
        })
    }
}