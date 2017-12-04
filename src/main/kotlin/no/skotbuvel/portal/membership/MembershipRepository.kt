package no.skotbuvel.portal.membership

import no.skotbuvel.portal.config.DbConfig
import no.skotbuvel.portal.jooq.Sequences.MEMBERSHIP_ID_SEQ
import no.skotbuvel.portal.jooq.tables.Membership.MEMBERSHIP
import no.skotbuvel.portal.util.JavaTimeUtil
import org.jooq.TransactionalRunnable
import java.sql.Date

class MembershipRepository(private val dbConfig: DbConfig) {

    fun save(membershipRegistration: MembershipRegistration, createdBy: String) {
        val dslContext = dbConfig.dslContext()

        dslContext.transaction(TransactionalRunnable {
            dslContext.insertInto(MEMBERSHIP,
                    MEMBERSHIP.ID,
                    MEMBERSHIP.ORIGINAL_ID,
                    MEMBERSHIP.ACTIVE,
                    MEMBERSHIP.PERSON_ID,
                    MEMBERSHIP.MEMBERSHIP_TYPE_ID,
                    MEMBERSHIP.PAYMENT_DATE,
                    MEMBERSHIP.CREATED_BY,
                    MEMBERSHIP.CREATED_DATE
            ).values(
                    dslContext.nextval(MEMBERSHIP_ID_SEQ).toInt(),
                    dslContext.currval(MEMBERSHIP_ID_SEQ).toInt(),
                    true,
                    membershipRegistration.personId,
                    membershipRegistration.membershipTypeId,
                    Date.valueOf(membershipRegistration.paymentDate),
                    createdBy,
                    JavaTimeUtil.nowEuropeOslo()
            )
                    .execute()
        })
    }

    fun delete(id: Int, deletedBy: String) {
        val dslContext = dbConfig.dslContext()

        dslContext.transaction(TransactionalRunnable {
            dslContext.update(MEMBERSHIP)
                    .set(MEMBERSHIP.ACTIVE, false)
                    .set(MEMBERSHIP.DELETED_BY, deletedBy)
                    .set(MEMBERSHIP.DELETED_DATE, JavaTimeUtil.nowEuropeOslo())
                    .where(MEMBERSHIP.ID.eq(id))
                    .execute()
        })
    }
}