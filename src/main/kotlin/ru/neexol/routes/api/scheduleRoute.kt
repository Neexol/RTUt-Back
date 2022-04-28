package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.MissingParametersException
import ru.neexol.repositories.ScheduleRepository

fun Route.scheduleRoute() {
    route("schedule") {
        getScheduleEndpoint()
    }
}

private fun Route.getScheduleEndpoint() {
    get {
        val response = call.request.queryParameters["group"]?.let {
            ScheduleRepository.getScheduleByGroup(it)
        } ?: call.request.queryParameters["teacher"]?.let {
            ScheduleRepository.getScheduleByTeacher(it)
        } ?: throw MissingParametersException("group or teacher")
        call.respond(response)
    }
}