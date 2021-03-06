package ru.neexol.routes.api.schedule

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.MissingParametersException
import ru.neexol.repositories.ScheduleRepository

fun Route.checksumRoute() {
    route("checksum") {
        getChecksumEndpoint()
    }
}

private fun Route.getChecksumEndpoint() {
    get {
        val response = call.parameters["group"]?.let {
            ScheduleRepository.getChecksumByGroup(it)
        } ?: throw MissingParametersException("group")
        call.respond(response)
    }
}