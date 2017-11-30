package no.skotbuvel.portal.person

import no.skotbuvel.portal.membership.MembershipInfo
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.jooq.TransactionalRunnable
import org.jooq.no.skotbuvel.portal.Sequences.PERSON_ID_SEQ
import org.jooq.no.skotbuvel.portal.Tables.*
import java.time.ZoneId
import java.time.ZonedDateTime

class PersonRepository(private val dslContext: DSLContext) {
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
        return dslContext
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
        return dslContext
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
                    ZonedDateTime.now(ZoneId.of("Europe/Oslo")).toOffsetDateTime()
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