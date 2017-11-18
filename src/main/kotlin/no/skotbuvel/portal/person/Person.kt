package no.skotbuvel.portal.person

data class Person(
        val id: Int? = null,
        val fullName: String,
        val email: String,
        val phone: String
)