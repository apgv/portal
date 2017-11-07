package no.skotbuvel.portal.domain

data class Person(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String
)