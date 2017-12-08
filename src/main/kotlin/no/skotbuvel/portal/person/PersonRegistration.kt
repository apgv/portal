package no.skotbuvel.portal.person

data class PersonRegistration(
        val fullName: String,
        val email: String,
        val phone: String,
        val address: String
)