package no.skotbuvel.portal.subject

data class SubjectSimple(
        val firstName: String,
        val email: String,
        val roles: List<String>
)

fun toSubjectSimple(subject: Subject) =
        SubjectSimple(
                firstName = subject.firstName,
                email = subject.email,
                roles = subject.roles.map { it.name }
        )