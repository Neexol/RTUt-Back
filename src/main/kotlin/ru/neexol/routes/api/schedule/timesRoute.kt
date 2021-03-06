package ru.neexol.routes.api.schedule

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.utils.lessonTimes

fun Route.timesRoute() {
    route("times") {
        getTimesEndpoint()
    }
}

private fun Route.getTimesEndpoint() {
    get {
        call.respond(lessonTimes)
    }
}