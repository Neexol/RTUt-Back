package ru.neexol.routes

import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Route.staticRoute() {
    static("static") {
        staticBasePackage = "static"
        resources(".")
    }
}