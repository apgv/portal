package no.skotbuvel.portal

import com.auth0.jwt.interfaces.DecodedJWT
import com.squareup.moshi.Types
import no.skotbuvel.portal.auth.JwtUtil
import no.skotbuvel.portal.auth.Role
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
import no.skotbuvel.portal.subject.Subject
import no.skotbuvel.portal.subject.SubjectRegistration
import no.skotbuvel.portal.subject.SubjectRepository
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

    get("/auth0callback", { _, response ->
        response.redirect("/")
    })

    path("api", {
        get("/persons", { request, _ ->
            verifyTokenAndCheckRole(request)

            val persons = personRepository.findAll()

            val parameterizedType = Types.newParameterizedType(List::class.java, Person::class.java)
            JsonUtil.moshi.adapter<List<Person>>(parameterizedType).toJson(persons)
        })

        get("/persons/:id", { request, _ ->
            verifyTokenAndCheckRole(request)

            val id = request.params(":id")

            val person = personRepository.findById(id.toInt())

            JsonUtil.moshi.adapter(Person::class.java).toJson(person)
        })

        post("/persons", { request, response ->
            val decodedJWT = verifyTokenAndCheckRole(request)

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
            val decodedJWT = verifyTokenAndCheckRole(request)

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
            val decodedJWT = verifyTokenAndCheckRole(request)

            val id = request.params(":id")
            membershipRepository.delete(id.toInt(), JwtUtil.email(decodedJWT))
        })

        get("/membershiptypes", { request, _ ->
            verifyTokenAndCheckRole(request)
            val queryParamActive = request.queryParams("active")
            val activeOnly = queryParamActive?.toBoolean() ?: false
            val membershiptTypes = membershipTypeRepository.findAll(activeOnly)
            val parameterizedType = Types.newParameterizedType(List::class.java, MembershipType::class.java)
            JsonUtil.moshi.adapter<List<MembershipType>>(parameterizedType).toJson(membershiptTypes)
        })

        post("/membershiptypes", { request, response ->
            val decodedJWT = verifyTokenAndCheckRole(request)
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
            verifyTokenAndCheckRole(request)
            val subjects = subjectRepository.findAll()
            val parameterizedType = Types.newParameterizedType(List::class.java, Subject::class.java)
            JsonUtil.moshi.adapter<List<Subject>>(parameterizedType).toJson(subjects)
        })

        post("/subjects", { request, response ->
            val decodedJWT = verifyTokenAndCheckRole(request)
            val jsonAdapter = JsonUtil.moshi.adapter(SubjectRegistration::class.java)
            val subjectRegistration = jsonAdapter.fromJson(request.body())

            if (subjectRegistration != null) {
                subjectRepository.save(subjectRegistration, JwtUtil.email(decodedJWT))
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

private fun verifyTokenAndCheckRole(request: Request): DecodedJWT {
    val decodedJWT = JwtUtil.verifyAndDecode(request)
    val user = userFromJWT(decodedJWT)
    if (!user.hasRole(Role.BOARD_MEMBER)) {
        val exceptionMessage = String.format(
                "User %s (%s) is missing role %s, has roles %s",
                user.email,
                user.subject,
                Role.BOARD_MEMBER,
                user.roles
        )
        throw IllegalAccessException(exceptionMessage)
    }

    return decodedJWT
}
