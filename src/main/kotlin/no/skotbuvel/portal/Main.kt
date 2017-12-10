package no.skotbuvel.portal

import com.auth0.jwt.interfaces.DecodedJWT
import com.squareup.moshi.Types
import no.skotbuvel.portal.auth.JwtUtil
import no.skotbuvel.portal.auth.userFromJWT
import no.skotbuvel.portal.config.HerokuPostgresConfig
import no.skotbuvel.portal.helper.DbHelper
import no.skotbuvel.portal.membership.MembershipRegistration
import no.skotbuvel.portal.membership.MembershipRepository
import no.skotbuvel.portal.membershiptype.MembershipType
import no.skotbuvel.portal.membershiptype.MembershipTypeRegistration
import no.skotbuvel.portal.membershiptype.MembershipTypeRepository
import no.skotbuvel.portal.person.Person
import no.skotbuvel.portal.person.PersonRegistration
import no.skotbuvel.portal.person.PersonRepository
import no.skotbuvel.portal.role.RoleRegistration
import no.skotbuvel.portal.role.RoleRepository
import no.skotbuvel.portal.subject.*
import no.skotbuvel.portal.util.JsonUtil
import org.flywaydb.core.Flyway
import spark.Request
import spark.Spark.*
import java.net.URI
import javax.sql.DataSource

fun main(args: Array<String>) {
    port(System.getenv("PORT").toInt())
    staticFiles.location("/frontend")

    val dbHelper = DbHelper(HerokuPostgresConfig(URI(System.getenv("DATABASE_URL"))))

    migrateDatabase(dbHelper.dataSource)

    val personRepository = PersonRepository(dbHelper)
    val membershipTypeRepository = MembershipTypeRepository(dbHelper)
    val membershipRepository = MembershipRepository(dbHelper)
    val subjectRepository = SubjectRepository(dbHelper)
    val roleRepository = RoleRepository(dbHelper)

    get("/auth0callback", { _, response ->
        response.redirect("/")
    })

    path("api", {
        get("/persons", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)

            val persons = personRepository.findAll()

            val parameterizedType = Types.newParameterizedType(List::class.java, Person::class.java)
            JsonUtil.moshi.adapter<List<Person>>(parameterizedType).toJson(persons)
        })

        get("/persons/:id", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)

            val id = request.params(":id")

            val person = personRepository.findById(id.toInt())

            JsonUtil.moshi.adapter(Person::class.java).toJson(person)
        })

        post("/persons", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)

            val jsonAdapter = JsonUtil.moshi.adapter(PersonRegistration::class.java)
            val personRegistration = jsonAdapter.fromJson(request.body())

            if (personRegistration != null) {
                personRepository.save(personRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        post("/memberships", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)

            val jsonAdapter = JsonUtil.moshi.adapter(MembershipRegistration::class.java)
            val membershipRegistration = jsonAdapter.fromJson(request.body())

            if (membershipRegistration != null) {
                membershipRepository.save(membershipRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        delete("/memberships/:id", { request, _ ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)

            val id = request.params(":id")
            membershipRepository.delete(id.toInt(), JwtUtil.email(decodedJWT))
        })

        get("/membershiptypes", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)
            val queryParamActive = request.queryParams("active")
            val activeOnly = queryParamActive?.toBoolean() ?: false
            val membershiptTypes = membershipTypeRepository.findAll(activeOnly)
            val parameterizedType = Types.newParameterizedType(List::class.java, MembershipType::class.java)
            JsonUtil.moshi.adapter<List<MembershipType>>(parameterizedType).toJson(membershiptTypes)
        })

        post("/membershiptypes", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)
            val jsonAdapter = JsonUtil.moshi.adapter(MembershipTypeRegistration::class.java)
            val membershipTypeRegistration = jsonAdapter.fromJson(request.body())

            if (membershipTypeRegistration != null) {
                membershipTypeRepository.save(membershipTypeRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        get("/subjects", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)
            val subjects = subjectRepository.findAll()
            val parameterizedType = Types.newParameterizedType(List::class.java, Subject::class.java)
            JsonUtil.moshi.adapter<List<Subject>>(parameterizedType).toJson(subjects)
        })

        get("/subjects/:id", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)
            val id = request.params(":id")
            val jsonAdapter = JsonUtil.moshi.adapter(Subject::class.java)
            jsonAdapter.toJson(subjectRepository.findById(id.toInt()))
        })

        get("/subjects/simplified/:email", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)
            val email = request.params(":email")
            val jsonAdapter = JsonUtil.moshi.adapter(SubjectSimple::class.java)
            jsonAdapter.toJson(toSubjectSimple(subjectRepository.findByEmail(email)))
        })

        post("/subjects", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)
            val jsonAdapter = JsonUtil.moshi.adapter(SubjectRegistration::class.java)
            val subjectRegistration = jsonAdapter.fromJson(request.body())

            if (subjectRegistration != null) {
                subjectRepository.save(subjectRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        post("/subjectroles", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)

            val jsonAdapter = JsonUtil.moshi.adapter(SubjectRoleRegistration::class.java)
            val subjectRoleRegistration = jsonAdapter.fromJson(request.body())
            if (subjectRoleRegistration != null) {
                subjectRepository.updateRoles(subjectRoleRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        get("/roles", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)
            val queryParamActive = request.queryParams("active")
            val activeOnly = queryParamActive?.toBoolean() ?: false
            val roles = roleRepository.findAll(activeOnly)
            val parameterizedType = Types.newParameterizedType(List::class.java, no.skotbuvel.portal.role.Role::class.java)
            JsonUtil.moshi.adapter<List<no.skotbuvel.portal.role.Role>>(parameterizedType).toJson(roles)
        })

        post("/roles", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), subjectRepository)
            val jsonAdapter = JsonUtil.moshi.adapter(RoleRegistration::class.java)
            val roleRegistration = jsonAdapter.fromJson(request.body())

            if (roleRegistration != null) {
                roleRepository.save(roleRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        after("/*", { _, response ->
            response.type("application/json")
        })
    })

}

private fun migrateDatabase(dataSource: DataSource) {
    val flyway = Flyway()
    flyway.dataSource = dataSource
    flyway.migrate()
}

private fun verifyTokenAndCheckRoles(request: Request,
                                     requiredRoles: List<String>,
                                     subjectRepository: SubjectRepository): DecodedJWT {
    val decodedJWT = JwtUtil.verifyAndDecode(request)
    val jwtUser = userFromJWT(decodedJWT)
    val subject = subjectRepository.findByEmail(jwtUser.email)
    val subjectRoles = subject.roles.map { it.name.toUpperCase() }

    return when {
        requiredRoles.isEmpty() -> decodedJWT
        subjectRoles.intersect(requiredRoles).isNotEmpty() -> decodedJWT
        else -> {
            val exceptionMessage = String.format(
                    "User %s (%s) is missing one of the roles %s, has roles %s",
                    jwtUser.email,
                    jwtUser.subject,
                    requiredRoles,
                    subjectRoles
            )
            throw IllegalAccessException(exceptionMessage)
        }
    }
}
