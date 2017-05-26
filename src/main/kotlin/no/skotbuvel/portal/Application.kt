package no.skotbuvel.portal

import com.google.gson.Gson
import spark.Spark.*

fun main(args: Array<String>) {

    port(8080)
    staticFiles.location("/frontend")

    get("/hello", { request, response ->
        response.type("application/json")
        Gson().toJson(mapOf("greeting" to "hello world"))
    })
}