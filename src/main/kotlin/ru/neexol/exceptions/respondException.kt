package ru.neexol.exceptions

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.respondThrowable(t: Throwable) = when(t) {
    is HttpException -> respond(t.httpCode, t.text)
    else             -> respond(HttpStatusCode.BadRequest, t.toString())
}