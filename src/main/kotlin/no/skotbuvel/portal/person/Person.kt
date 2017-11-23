package no.skotbuvel.portal.person

import org.jooq.Record
import org.jooq.no.skotbuvel.portal.Tables.PERSON
import java.time.ZonedDateTime

data class Person(
        val id: Int,
        val fullName: String,
        val email: String?,
        val phone: String?,
        val address: String?,
        val createdBy: String,
        val createdDate: ZonedDateTime
)

fun toPerson(record: Record) =
        Person(
                id = record[PERSON.ID],
                fullName = record[PERSON.FULL_NAME],
                email = record[PERSON.EMAIL],
                phone = record[PERSON.PHONE],
                address = record[PERSON.ADDRESS],
                createdBy = record[PERSON.CREATED_BY],
                createdDate = record[PERSON.CREATED_DATE].toZonedDateTime()
        )