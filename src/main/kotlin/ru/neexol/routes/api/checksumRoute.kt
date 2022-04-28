package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.MissingParametersException
import ru.neexol.exceptions.respondThrowable
import ru.neexol.repositories.ScheduleRepository

fun Route.checksumRoute() {
    route("checksum") {
        getChecksumEndpoint()
    }
}

private fun Route.getChecksumEndpoint() {
    get {
        runCatching {
            call.request.queryParameters["group"]?.let {
                ScheduleRepository.getChecksumByGroup(it)
            } ?: throw MissingParametersException()
        }.onSuccess {
            call.respond(it)
        }.onFailure {
            call.respondThrowable(it)
        }
    }
}