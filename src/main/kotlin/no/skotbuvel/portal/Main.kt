package no.skotbuvel.portal

import com.google.gson.Gson
import com.zaxxer.hikari.HikariDataSource
import no.skotbuvel.portal.auth.JwtUtil
import no.skotbuvel.portal.auth.Role
import no.skotbuvel.portal.auth.RoleChecker
import no.skotbuvel.portal.config.Auth0Config
import no.skotbuvel.portal.domain.Person
import org.flywaydb.core.Flyway
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.no.skotbuvel.portal.Tables.person
import spark.Request
import spark.Spark.*
import java.util.*
import javax.sql.DataSource

object Application {
    const val SERVER_PORT = "server.port"
    const val PROFILE = "profile"
}

fun main(args: Array<String>) {
    val argsMap = mapAndCheckArguments(args)
    val properties = properties(argsMap[Application.PROFILE] as String)

    port(argsMap[Application.SERVER_PORT]!!.toInt())
    staticFiles.location("/frontend")

    val flyway = Flyway()
    flyway.dataSource = datasource()
    flyway.migrate()

    path("api", {
        get("/hello", { _, response ->
            println("called /hello")
            response.type("application/json")
            Gson().toJson(mapOf("greeting" to "hello world"))
        })

        get("/persons", { request, response ->
            verifyTokenAndCheckRole(request)

            val dsl = DSL.using(datasource(), SQLDialect.POSTGRES)

            val persons = dsl.selectFrom(person).fetch().map {
                Person(
                        id = it.id(),
                        firstName = it.first_name(),
                        lastName = it.last_name(),
                        email = it.email(),
                        phone = it.phone()
                )
            }

            response.type("application/json")
            Gson().toJson(persons)
        })

        get("/auth0/config", { _, response ->
            response.type("application/json")
            Gson().toJson(auth0Config(properties))
        })
    })

}

private fun verifyTokenAndCheckRole(request: Request) {
    val decodedJWT = JwtUtil.verifyAndDecode(request)
    val roles = JwtUtil.roles(decodedJWT)
    if (!RoleChecker.hasRole(Role.BOARD_MEMBER, roles)) {
        val exceptionMessage = String.format(
                "User %s (%s) is missing role %s, has roles %s",
                decodedJWT.getClaim("email").asString(),
                decodedJWT.subject,
                Role.BOARD_MEMBER,
                roles
        )
        throw IllegalAccessException(exceptionMessage)
    }
}

fun datasource(): DataSource {
    return HikariDataSource().apply {
        dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
        username = "postgres"
        password = "mysecretpassword"
        schema = "public"
        addDataSourceProperty("databaseName", "postgres")
        addDataSourceProperty("portNumber", "5432")
        addDataSourceProperty("serverName", "172.17.0.2")
    }
}

private fun mapAndCheckArguments(args: Array<String>): Map<String, String> {
    val argsMap = args.associate { s ->
        val split = s.split("=")
        Pair(split[0], split[1])
    }

    listOf(Application.SERVER_PORT, Application.PROFILE).forEach { arg ->
        if (!argsMap.containsKey(arg)) {
            throw IllegalArgumentException("Missing required argument '$arg'")
        }
    }

    return argsMap
}

private fun properties(profile: String) = Properties().apply {
    val fileName = "application-$profile.properties"
    Application.javaClass.classLoader.getResource(fileName).openStream().use { fis ->
        load(fis)
    }
}

private fun auth0Config(props: Properties) =
        Auth0Config(
                domain = props.getProperty(Auth0Config.DOMAIN),
                clientID = props.getProperty(Auth0Config.CLIENT_ID),
                redirectUri = props.getProperty(Auth0Config.REDIRECT_URI),
                audience = props.getProperty(Auth0Config.AUDIENCE),
                responseType = props.getProperty(Auth0Config.RESPONSE_TYPE),
                scope = props.getProperty(Auth0Config.SCOPE)
        )