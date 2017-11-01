package no.skotbuvel.portal

import com.google.gson.Gson
import com.zaxxer.hikari.HikariDataSource
import no.skotbuvel.portal.config.Auth0Config
import org.flywaydb.core.Flyway
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

    get("/hello", { _, response ->
        response.type("application/json")
        Gson().toJson(mapOf("greeting" to "hello world"))
    })

    get("/auth0/config", { _, response ->
        response.type("application/json")
        Gson().toJson(auth0Config(properties))
    })

}

fun datasource(): DataSource {
    return HikariDataSource().apply {
        jdbcUrl = "jdbc:h2:mem:;MODE=PostgreSQL"
        driverClassName = "org.h2.Driver"
        username = "sa"
        password = ""
    };
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