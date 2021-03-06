package ru.neexol.routes.api.schedule

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.MissingParametersException
import ru.neexol.repositories.ScheduleRepository

fun Route.scheduleRoute() {
    route("schedule") {
        timesRoute()
        updateRoute()
        checksumRoute()
        getScheduleEndpoint()
    }
}

private fun Route.getScheduleEndpoint() {
    get {
        val response = call.parameters["group"]?.let {
            ScheduleRepository.getScheduleByGroup(it)
        } ?: call.parameters["teacher"]?.let {
            ScheduleRepository.getScheduleByTeacher(it)
        } ?: throw MissingParametersException("group or teacher")
        call.respond(response)
    }
}