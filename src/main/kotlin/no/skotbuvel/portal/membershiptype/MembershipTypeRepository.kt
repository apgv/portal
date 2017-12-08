package no.skotbuvel.portal.membershiptype

import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.Sequences.MEMBERSHIP_TYPE_ID_SEQ
import no.skotbuvel.portal.jooq.tables.MembershipType.MEMBERSHIP_TYPE
import no.skotbuvel.portal.util.JavaTimeUtil
import org.jooq.Record
import org.jooq.TransactionalRunnable

class MembershipTypeRepository(private val dbHelper: DbHelper) {

    fun findAll(activeOnly: Boolean) = if (activeOnly) findAllActive() else findAll()

    private fun findAllActive(): List<MembershipType> {
        return findAll().filter { membershipType -> membershipType.active }
    }

    private fun findAll(): List<MembershipType> {
        return dbHelper.dslContext()
                .select(
                        MEMBERSHIP_TYPE.ID,
                        MEMBERSHIP_TYPE.ACTIVE,
                        MEMBERSHIP_TYPE.TYPE,
                        MEMBERSHIP_TYPE.YEAR,
                        MEMBERSHIP_TYPE.PRICE,
                        MEMBERSHIP_TYPE.CREATED_BY,
                        MEMBERSHIP_TYPE.CREATED_DATE
                )
                .from(MEMBERSHIP_TYPE)
                .fetch()
                .map { toMembershipType(it) }
    }

    fun save(membershipTypeRegistration: MembershipTypeRegistration, createdBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            dslContext.insertInto(MEMBERSHIP_TYPE,
                    MEMBERSHIP_TYPE.ID,
                    MEMBERSHIP_TYPE.ORIGINAL_ID,
                    MEMBERSHIP_TYPE.ACTIVE,
                    MEMBERSHIP_TYPE.TYPE,
                    MEMBERSHIP_TYPE.YEAR,
                    MEMBERSHIP_TYPE.PRICE,
                    MEMBERSHIP_TYPE.CREATED_BY,
                    MEMBERSHIP_TYPE.CREATED_DATE
            ).values(
                    dslContext.nextval(MEMBERSHIP_TYPE_ID_SEQ).toInt(),
                    dslContext.currval(MEMBERSHIP_TYPE_ID_SEQ).toInt(),
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

    private fun toMembershipType(record: Record) =
            MembershipType(
                    id = record[MEMBERSHIP_TYPE.ID],
                    active = record[MEMBERSHIP_TYPE.ACTIVE],
                    type = record[MEMBERSHIP_TYPE.TYPE],
                    year = record[MEMBERSHIP_TYPE.YEAR],
                    price = record[MEMBERSHIP_TYPE.PRICE],
                    createdBy = record[MEMBERSHIP_TYPE.CREATED_BY],
                    createdDate = record[MEMBERSHIP_TYPE.CREATED_DATE].toZonedDateTime()
            )
}