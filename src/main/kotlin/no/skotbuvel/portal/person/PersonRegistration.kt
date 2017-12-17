package no.skotbuvel.portal.person

data class PersonRegistration(
        val id: Int?,
        val fullName: String,
        val email: String,
        val phone: String,
        val address: String
)