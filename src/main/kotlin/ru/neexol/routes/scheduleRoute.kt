package ru.neexol.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.MissingParametersException
import ru.neexol.exceptions.respondThrowable
import ru.neexol.repositories.ScheduleRepository

fun Route.scheduleRoute() {
    get("/schedule") {
        runCatching {
            call.request.queryParameters["group"]?.let {
                ScheduleRepository.getScheduleByGroup(it)
            } ?: throw MissingParametersException()
        }.onSuccess {
            call.respond(it)
        }.onFailure {
            call.respondThrowable(it)
        }
    }
}