package no.skotbuvel.portal.person

import no.skotbuvel.portal.config.DbConfig
import no.skotbuvel.portal.jooq.Sequences.PERSON_ID_SEQ
import no.skotbuvel.portal.jooq.tables.Membership.MEMBERSHIP
import no.skotbuvel.portal.jooq.tables.MembershipType.MEMBERSHIP_TYPE
import no.skotbuvel.portal.jooq.tables.Person.PERSON
import no.skotbuvel.portal.membership.MembershipInfo
import no.skotbuvel.portal.util.JavaTimeUtil
import org.jooq.Record
import org.jooq.Result
import org.jooq.TransactionalRunnable

class PersonRepository {
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
        return DbConfig.dslContext()
                .select(selectParameters)
                .from(PERSON)
                .leftJoin(MEMBERSHIP)
                .on(PERSON.ID.eq(MEMBERSHIP.PERSON_ID))
                .and(MEMBERSHIP.ACTIVE.eq(true))
                .leftJoin(MEMBERSHIP_TYPE)
                .on(MEMBERSHIP.MEMBERSHIP_TYPE_ID.eq(MEMBERSHIP_TYPE.ID))
                .fetchGroups(PERSON.ID)
                .values
                .map { mapPersonWithMembership(it) }
    }

    fun findById(id: Int): Person {
        return DbConfig.dslContext()
                .select(selectParameters)
                .from(PERSON)
                .leftJoin(MEMBERSHIP)
                .on(PERSON.ID.eq(MEMBERSHIP.PERSON_ID))
                .and(MEMBERSHIP.ACTIVE.eq(true))
                .leftJoin(MEMBERSHIP_TYPE)
                .on(MEMBERSHIP.MEMBERSHIP_TYPE_ID.eq(MEMBERSHIP_TYPE.ID))
                .where(PERSON.ID.eq(id))
                .fetchGroups(PERSON.ID)
                .values
                .map { mapPersonWithMembership(it) }
                .first()
    }

    fun save(personRegistration: PersonRegistration, createdBy: String) {
        val dslContext = DbConfig.dslContext()

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

    private fun mapPersonWithMembership(result: Result<Record>): Person {
        val memberships = mapMemberships(result)

        val record = result.first()

        return Person(
                id = record[PERSON.ID],
                fullName = record[PERSON.FULL_NAME],
                email = record[PERSON.EMAIL],
                phone = record[PERSON.PHONE],
                address = record[PERSON.ADDRESS],
                memberships = memberships,
                createdBy = record[PERSON.CREATED_BY],
                createdDate = record[PERSON.CREATED_DATE].toZonedDateTime()
        )
    }

    private fun mapMemberships(it: Result<Record>): List<MembershipInfo> {
        return it
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