package ru.neexol.routes.api.schedule

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.repositories.ParserRepository

fun Route.updateRoute() {
    route("update") {
        updateScheduleEndpoint()
    }
}

private fun Route.updateScheduleEndpoint() {
    get {
        call.parameters["force"]?.let {
            ParserRepository.forceUpdateSchedule()
        } ?: ParserRepository.updateSchedule()
        call.respondText("Updated!")
    }
}