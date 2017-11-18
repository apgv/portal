package no.skotbuvel.portal.person

import no.skotbuvel.portal.DbUtil
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.TransactionalCallable
import org.jooq.impl.DSL
import org.jooq.no.skotbuvel.portal.Sequences.PERSON_ID_SEQ
import org.jooq.no.skotbuvel.portal.Tables.PERSON
import java.time.ZoneId
import java.time.ZonedDateTime

class PersonRepository {
    private val selectParameters = listOf(
            PERSON.ID,
            PERSON.FULL_NAME,
            PERSON.EMAIL,
            PERSON.PHONE,
            PERSON.CREATED_BY,
            PERSON.CREATED_DATE
    )

    fun findAll(): List<Person> {
        val dslContext = dslContext()

        return dslContext
                .select(selectParameters)
                .from(PERSON)
                .fetch()
                .map { toPerson(it) }
                .toList()
    }

    fun save(personRegistration: PersonRegistration, createdBy: String): Person {
        val dslContext = dslContext()

        return dslContext.transactionResult(TransactionalCallable {
            return@TransactionalCallable dslContext.insertInto(PERSON,
                    PERSON.ID,
                    PERSON.ORIGINAL_ID,
                    PERSON.ACTIVE,
                    PERSON.FULL_NAME,
                    PERSON.EMAIL,
                    PERSON.PHONE,
                    PERSON.CREATED_BY,
                    PERSON.CREATED_DATE
            ).values(
                    dslContext.nextval(PERSON_ID_SEQ).toInt(),
                    dslContext.currval(PERSON_ID_SEQ).toInt(),
                    true,
                    personRegistration.fullName,
                    personRegistration.email,
                    personRegistration.phone,
                    createdBy,
                    ZonedDateTime.now(ZoneId.of("Europe/Oslo")).toOffsetDateTime()
            )
                    .returning(selectParameters)
                    .fetchOne()
                    .map { toPerson(it) }
        })
    }

    private fun dslContext(): DSLContext = DSL.using(DbUtil.datasource, SQLDialect.POSTGRES)

}