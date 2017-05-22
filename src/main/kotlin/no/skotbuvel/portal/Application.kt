package no.skotbuvel.portal

import com.google.gson.Gson
import spark.Spark.get
import spark.Spark.port

fun main(args: Array<String>) {

    port(8080)

    get("/", { request, response ->
        response.type("application/json")
        Gson().toJson(mapOf("greeting" to "hello world"))
    })
}