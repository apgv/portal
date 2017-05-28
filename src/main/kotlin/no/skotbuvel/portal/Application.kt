package no.skotbuvel.portal

import com.google.gson.Gson
import no.skotbuvel.portal.config.Auth0Config
import spark.Spark.*
import java.io.FileInputStream
import java.util.*

fun properties() = Properties().apply {
    FileInputStream("src/main/resources/application-dev.properties").use { fis ->
        load(fis)
    }
}

fun auth0Config(props: Properties) =
        Auth0Config(
                domain = props.getProperty(Auth0Config.DOMAIN),
                clientID = props.getProperty(Auth0Config.CLIENT_ID),
                redirectUri = props.getProperty(Auth0Config.REDIRECT_URI),
                audience = props.getProperty(Auth0Config.AUDIENCE),
                responseType = props.getProperty(Auth0Config.RESPONSE_TYPE),
                scope = props.getProperty(Auth0Config.SCOPE)
        )

fun main(args: Array<String>) {

    port(8080)
    staticFiles.location("/frontend")
    val properties = properties()

    get("/hello", { request, response ->
        response.type("application/json")
        Gson().toJson(mapOf("greeting" to "hello world"))
    })

    get("/auth0/config", { request, response ->
        response.type("application/json")
        Gson().toJson(auth0Config(properties))
    })

}