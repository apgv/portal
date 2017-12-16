package no.skotbuvel.portal.user

data class SubjectSimple(
        val firstName: String,
        val email: String,
        val roles: List<String>
)

fun toSubjectSimple(user: User) =
        SubjectSimple(
                firstName = user.firstName,
                email = user.email,
                roles = user.roles.map { it.name }
        )