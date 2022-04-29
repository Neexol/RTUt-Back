package ru.neexol.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import ru.neexol.exceptions.HttpException

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when(cause) {
                is HttpException -> call.respond(cause.httpCode, cause.toString())
                else             -> call.respond(HttpStatusCode.InternalServerError, cause.toString())
            }
        }
    }
}