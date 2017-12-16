package no.skotbuvel.portal.user

data class Subject(
        val firstName: String,
        val email: String,
        val roles: List<String>
)

fun toSubject(user: User) =
        Subject(
                firstName = user.firstName,
                email = user.email,
                roles = user.roles.map { it.name }
        )                                    