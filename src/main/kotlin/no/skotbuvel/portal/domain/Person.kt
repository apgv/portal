package no.skotbuvel.portal.domain

data class Person(
        val id: Int? = null,
        val fullName: String,
        val email: String,
        val phone: String
)