package no.skotbuvel.portal.person

import no.skotbuvel.portal.DbUtil
import no.skotbuvel.portal.domain.Person
import org.jooq.SQLDialect
import org.jooq.TransactionalCallable
import org.jooq.impl.DSL
import org.jooq.no.skotbuvel.portal.Sequences.PERSON_ID_SEQ
import org.jooq.no.skotbuvel.portal.Tables
import org.jooq.no.skotbuvel.portal.Tables.PERSON
import java.time.ZoneId
import java.time.ZonedDateTime

class PersonRepository {

    fun findAll(): List<Person> {
        val dsl = DSL.using(DbUtil.datasource, SQLDialect.POSTGRES)

        return dsl.selectFrom(Tables.PERSON).fetch().map {
            Person(
                    id = it.id,
                    fullName = it.fullName,
                    email = it.email,
                    phone = it.phone
            )
        }.toList()
    }

    fun save(person: Person, createdBy: String): Int {
        val dsl = DSL.using(DbUtil.datasource, SQLDialect.POSTGRES)

        val personRecord = dsl.transactionResult(TransactionalCallable {
            return@TransactionalCallable dsl.insertInto(PERSON,
                    PERSON.ID,
                    PERSON.ORIGINAL_ID,
                    PERSON.ACTIVE,
                    PERSON.FULL_NAME,
                    PERSON.EMAIL,
                    PERSON.PHONE,
                    PERSON.CREATED_BY,
                    PERSON.CREATED_DATE
            ).values(
                    dsl.nextval(PERSON_ID_SEQ).toInt(),
                    dsl.currval(PERSON_ID_SEQ).toInt(),
                    true,
                    person.fullName,
                    person.email,
                    person.phone,
                    createdBy,
                    ZonedDateTime.now(ZoneId.of("Europe/Oslo")).toOffsetDateTime()
            )
                    .returning(PERSON.ID)
                    .fetchOne()
        })
        println("person got ID: ${personRecord.id}")

        return personRecord.id
    }

}