package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.utils.lessonTimes

fun Route.timesRoute() {
    get("/times") {
        call.respond(lessonTimes)
    }
}