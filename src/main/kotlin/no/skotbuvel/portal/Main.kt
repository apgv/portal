package no.skotbuvel.portal

import com.auth0.jwt.exceptions.JWTVerificationException
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
import no.skotbuvel.portal.role.RoleRepository
import no.skotbuvel.portal.user.*
import no.skotbuvel.portal.util.JsonUtil
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.flywaydb.core.Flyway
import spark.Request
import spark.Spark.*
import java.net.URI
import java.net.URLEncoder
import javax.sql.DataSource

fun main(args: Array<String>) {
    port(System.getenv("PORT").toInt())
    staticFiles.location("/frontend")

    val logger = logger()

    val dbHelper = DbHelper(HerokuPostgresConfig(URI(System.getenv("DATABASE_URL"))))

    migrateDatabase(dbHelper.dataSource)

    val personRepository = PersonRepository(dbHelper)
    val membershipTypeRepository = MembershipTypeRepository(dbHelper)
    val membershipRepository = MembershipRepository(dbHelper)
    val userRepository = UserRepository(dbHelper)
    val roleRepository = RoleRepository(dbHelper)

    before("/*", { request, response ->
        val herokuOriginatingProtocol = request.headers("X-Forwarded-Proto")
        val secureProtocol = herokuOriginatingProtocol?.equals("https") ?: true

        if (!secureProtocol) {
            val url = request.url()
            val secureUrl = url.replaceRange(0, 4, "https")
            val queryString = request.queryString()

            when {
                queryString.isNullOrBlank() -> response.redirect(secureUrl)
                else -> response.redirect(secureUrl + "?" + queryString)
            }
        }
    })

    path("api", {
        get("/persons", { request, _ ->
            verifyTokenAndCheckRoles(request, listOf(Role.STYREMEDLEM), userRepository)

            val persons = personRepository.findAll()

            val parameterizedType = Types.newParameterizedType(List::class.java, Person::class.java)
            JsonUtil.moshi.adapter<List<Person>>(parameterizedType).toJson(persons)
        })

        get("/persons/:id", { request, _ ->
            verifyTokenAndCheckRoles(request, listOf(Role.STYREMEDLEM), userRepository)

            val id = request.params(":id")

            val person = personRepository.findById(id.toInt())

            JsonUtil.moshi.adapter(Person::class.java).toJson(person)
        })

        post("/persons", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, listOf(Role.STYREMEDLEM), userRepository)

            val jsonAdapter = JsonUtil.moshi.adapter(PersonRegistration::class.java)
            val personRegistration = jsonAdapter.fromJson(request.body())

            if (personRegistration != null) {
                personRepository.save(personRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        put("/persons", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, listOf(Role.STYREMEDLEM), userRepository)
            val jsonAdapter = JsonUtil.moshi.adapter(PersonRegistration::class.java)
            val personRegistration = jsonAdapter.fromJson(request.body())

            if (personRegistration?.id != null) {
                personRepository.update(personRegistration, JwtUtil.email(decodedJWT))
            } else {
                response.status(400)
            }
        })

        post("/memberships", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, listOf(Role.KASSERER, Role.STYRELEDER), userRepository)

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
            val decodedJWT = verifyTokenAndCheckRoles(request, listOf(Role.KASSERER, Role.STYRELEDER), userRepository)

            val id = request.params(":id")
            membershipRepository.delete(id.toInt(), JwtUtil.email(decodedJWT))
        })

        get("/membershiptypes", { request, _ ->
            verifyTokenAndCheckRoles(request, listOf(Role.STYREMEDLEM), userRepository)
            val queryParamActive = request.queryParams("active")
            val activeOnly = queryParamActive?.toBoolean() ?: false
            val membershiptTypes = membershipTypeRepository.findAll(activeOnly)
            val parameterizedType = Types.newParameterizedType(List::class.java, MembershipType::class.java)
            JsonUtil.moshi.adapter<List<MembershipType>>(parameterizedType).toJson(membershiptTypes)
        })

        post("/membershiptypes", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), userRepository)
            val jsonAdapter = JsonUtil.moshi.adapter(MembershipTypeRegistration::class.java)
            val membershipTypeRegistration = jsonAdapter.fromJson(request.body())

            if (membershipTypeRegistration != null) {
                membershipTypeRepository.save(membershipTypeRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        get("/users", { request, _ ->
            verifyTokenAndCheckRoles(request, listOf(Role.STYREMEDLEM), userRepository)
            val users = userRepository.findAll()
            val parameterizedType = Types.newParameterizedType(List::class.java, User::class.java)
            JsonUtil.moshi.adapter<List<User>>(parameterizedType).toJson(users)
        })

        get("/users/:id", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), userRepository)
            val id = request.params(":id")
            val jsonAdapter = JsonUtil.moshi.adapter(User::class.java)
            jsonAdapter.toJson(userRepository.findById(id.toInt()))
        })

        post("/users", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, listOf(Role.STYRELEDER, Role.ADMIN), userRepository)
            val jsonAdapter = JsonUtil.moshi.adapter(UserRegistration::class.java)
            val userRegistration = jsonAdapter.fromJson(request.body())

            if (userRegistration != null) {
                userRepository.save(userRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        post("/userroles", { request, response ->
            val decodedJWT = verifyTokenAndCheckRoles(request, emptyList(), userRepository)

            val jsonAdapter = JsonUtil.moshi.adapter(UserRoleRegistration::class.java)
            val userRoleRegistration = jsonAdapter.fromJson(request.body())
            if (userRoleRegistration != null) {
                userRepository.updateRoles(userRoleRegistration, JwtUtil.email(decodedJWT))
                response.status(201)
            } else {
                response.status(400)
            }
        })

        get("/roles", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), userRepository)
            val roles = roleRepository.findAll()
            val parameterizedType = Types.newParameterizedType(List::class.java, no.skotbuvel.portal.role.Role::class.java)
            JsonUtil.moshi.adapter<List<no.skotbuvel.portal.role.Role>>(parameterizedType).toJson(roles)
        })

        get("/subjects/:email", { request, _ ->
            verifyTokenAndCheckRoles(request, emptyList(), userRepository)
            val email = request.params(":email")
            val jsonAdapter = JsonUtil.moshi.adapter(Subject::class.java)
            jsonAdapter.toJson(toSubject(userRepository.findByEmail(email)))
        })

        after("/*", { _, response ->
            response.type("application/json")
        })
    })

    get("/auth0callback", { _, response ->
        response.redirect("/")
    })

    get("/*", { request, response ->
        response.redirect("/?unknown_api_path=${encodeURL(request.uri())}")
    })

    exception(JWTVerificationException::class.java, { exception, _, response ->
        logger.error(exception)
        response.redirect("/?unknown_api_path=${encodeURL("/reauthenticate")}")
    })

}

fun logger(): Logger = LogManager.getLogger()

private fun migrateDatabase(dataSource: DataSource) {
    val flyway = Flyway()
    flyway.dataSource = dataSource
    flyway.migrate()
}

private fun verifyTokenAndCheckRoles(request: Request,
                                     requiredRoles: List<Role>,
                                     userRepository: UserRepository): DecodedJWT {
    val decodedJWT = JwtUtil.verifyAndDecode(request)
    val jwtUser = userFromJWT(decodedJWT)
    val user = userRepository.findByEmail(jwtUser.email)
    val userRoles = user.roles.map { Role.valueOfIgnoreCase(it.name) }

    return when {
        requiredRoles.isEmpty() -> decodedJWT
        userRoles.intersect(requiredRoles).isNotEmpty() -> decodedJWT
        else -> {
            val exceptionMessage = String.format(
                    "User %s (%s) is missing one of the roles %s, has roles %s",
                    jwtUser.email,
                    jwtUser.subject,
                    requiredRoles,
                    userRoles
            )
            throw IllegalAccessException(exceptionMessage)
        }
    }
}

private fun encodeURL(uri: String) = URLEncoder.encode(uri, "UTF-8")
