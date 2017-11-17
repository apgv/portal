package no.skotbuvel.portal.person

import no.skotbuvel.portal.DbUtil
import no.skotbuvel.portal.domain.Person
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.no.skotbuvel.portal.Tables

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

}