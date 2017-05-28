package no.skotbuvel.portal

import com.google.gson.Gson
import spark.Spark.*
import java.io.FileInputStream
import java.util.*

fun properties() = Properties().apply {
    FileInputStream("src/main/resources/application-dev.properties").use { fis ->
        load(fis)
    }
}

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
        Gson().toJson(
                mapOf("domain" to properties.getProperty("auth0.domain"),
                        "clientID" to properties.getProperty("auth0.clientID"),
                        "redirectUri" to properties.getProperty("auth0.redirectUri"),
                        "audience" to properties.getProperty("auth0.audience"),
                        "responseType" to properties.getProperty("auth0.responseType"),
                        "scope" to properties.getProperty("auth0.scope")
                )
        )
    })

}