package no.skotbuvel.portal.person

import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.jooq.Sequences.PERSON_ID_SEQ
import no.skotbuvel.portal.jooq.tables.Membership.MEMBERSHIP
import no.skotbuvel.portal.jooq.tables.MembershipType.MEMBERSHIP_TYPE
import no.skotbuvel.portal.jooq.tables.Person.PERSON
import no.skotbuvel.portal.membership.MembershipInfo
import no.skotbuvel.portal.util.JavaTimeUtil
import org.jooq.Record
import org.jooq.Result
import org.jooq.TransactionalRunnable

class PersonRepository(private val dbHelper: DbHelper) {

    private val selectParameters = listOf(
            PERSON.ID,
            PERSON.FULL_NAME,
            PERSON.EMAIL,
            PERSON.PHONE,
            PERSON.ADDRESS,
            PERSON.CREATED_BY,
            PERSON.CREATED_DATE,
            MEMBERSHIP.ID,
            MEMBERSHIP.PAYMENT_DATE,
            MEMBERSHIP_TYPE.YEAR,
            MEMBERSHIP_TYPE.TYPE
    )

    fun findAll(): List<Person> {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(PERSON)
                .leftJoin(MEMBERSHIP)
                .on(PERSON.ID.eq(MEMBERSHIP.PERSON_ID))
                .and(MEMBERSHIP.ACTIVE.eq(true))
                .leftJoin(MEMBERSHIP_TYPE)
                .on(MEMBERSHIP.MEMBERSHIP_TYPE_ID.eq(MEMBERSHIP_TYPE.ID))
                .where(PERSON.ACTIVE.eq(true))
                .fetchGroups(PERSON.ID)
                .values
                .map { mapPersonWithMembership(it) }
    }

    fun findById(id: Int): Person {
        return dbHelper.dslContext()
                .select(selectParameters)
                .from(PERSON)
                .leftJoin(MEMBERSHIP)
                .on(PERSON.ID.eq(MEMBERSHIP.PERSON_ID))
                .and(MEMBERSHIP.ACTIVE.eq(true))
                .leftJoin(MEMBERSHIP_TYPE)
                .on(MEMBERSHIP.MEMBERSHIP_TYPE_ID.eq(MEMBERSHIP_TYPE.ID))
                .where(PERSON.ID.eq(id))
                .and(PERSON.ACTIVE.eq(true))
                .fetchGroups(PERSON.ID)
                .values
                .map { mapPersonWithMembership(it) }
                .first()
    }

    fun save(personRegistration: PersonRegistration, createdBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            dslContext.insertInto(PERSON,
                    PERSON.ID,
                    PERSON.ORIGINAL_ID,
                    PERSON.ACTIVE,
                    PERSON.FULL_NAME,
                    PERSON.EMAIL,
                    PERSON.PHONE,
                    PERSON.ADDRESS,
                    PERSON.CREATED_BY,
                    PERSON.CREATED_DATE
            ).values(
                    dslContext.nextval(PERSON_ID_SEQ).toInt(),
                    dslContext.currval(PERSON_ID_SEQ).toInt(),
                    true,
                    personRegistration.fullName,
                    personRegistration.email,
                    personRegistration.phone,
                    personRegistration.address,
                    createdBy,
                    JavaTimeUtil.nowEuropeOslo()
            )
                    .execute()
        })
    }

    fun update(personRegistration: PersonRegistration, updatedBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            val originalId = dslContext.select(PERSON.ORIGINAL_ID)
                    .from(PERSON)
                    .where(PERSON.ID.eq(personRegistration.id))
                    .fetchOne()
                    .map { it[PERSON.ORIGINAL_ID] }

            dslContext.update(PERSON)
                    .set(PERSON.ACTIVE, false)
                    .set(PERSON.CHANGED_BY, updatedBy)
                    .set(PERSON.CHANGED_DATE, JavaTimeUtil.nowEuropeOslo())
                    .where(PERSON.ID.eq(personRegistration.id))
                    .execute()

            dslContext.insertInto(PERSON,
                    PERSON.ID,
                    PERSON.ORIGINAL_ID,
                    PERSON.ACTIVE,
                    PERSON.FULL_NAME,
                    PERSON.EMAIL,
                    PERSON.PHONE,
                    PERSON.ADDRESS,
                    PERSON.CREATED_BY,
                    PERSON.CREATED_DATE
            ).values(
                    dslContext.nextval(PERSON_ID_SEQ).toInt(),
                    originalId,
                    true,
                    personRegistration.fullName,
                    personRegistration.email,
                    personRegistration.phone,
                    personRegistration.address,
                    updatedBy,
                    JavaTimeUtil.nowEuropeOslo()
            )
                    .execute()

            dslContext.update(MEMBERSHIP)
                    .set(MEMBERSHIP.PERSON_ID, dslContext.currval(PERSON_ID_SEQ).toInt())
                    .where(MEMBERSHIP.PERSON_ID.eq(personRegistration.id))
                    .and(MEMBERSHIP.ACTIVE.eq(true))
                    .execute()
        })
    }

    fun delete(id: Int, deletedBy: String) {
        val dslContext = dbHelper.dslContext()

        dslContext.transaction(TransactionalRunnable {
            val membershipCount = dslContext.selectCount()
                    .from(MEMBERSHIP)
                    .where(MEMBERSHIP.PERSON_ID.eq(id))
                    .and(MEMBERSHIP.ACTIVE.eq(true))
                    .fetchOne(0, Int::class.java)

            if (membershipCount > 0) {
                throw IllegalStateException("Alle medlemskap må slettes før personen kan slettes")
            }

            dslContext.update(PERSON)
                    .set(PERSON.ACTIVE, false)
                    .set(PERSON.CHANGED_BY, deletedBy)
                    .set(PERSON.CHANGED_DATE, JavaTimeUtil.nowEuropeOslo())
                    .where(PERSON.ID.eq(id))
                    .execute()
        })
    }

    private fun mapPersonWithMembership(result: Result<Record>): Person {
        val memberships = mapMemberships(result)

        val record = result.first()

        return Person(
                id = record[PERSON.ID],
                fullName = record[PERSON.FULL_NAME],
                email = record[PERSON.EMAIL].orEmpty(),
                phone = record[PERSON.PHONE].orEmpty(),
                address = record[PERSON.ADDRESS].orEmpty(),
                memberships = memberships,
                createdBy = record[PERSON.CREATED_BY],
                createdDate = record[PERSON.CREATED_DATE].toZonedDateTime()
        )
    }

    private fun mapMemberships(result: Result<Record>): List<MembershipInfo> {
        return result
                .filter { it[MEMBERSHIP.ID] != null }
                .map {
                    MembershipInfo(
                            id = it[MEMBERSHIP.ID],
                            year = it[MEMBERSHIP_TYPE.YEAR],
                            type = it[MEMBERSHIP_TYPE.TYPE]
                    )
                }
    }

}