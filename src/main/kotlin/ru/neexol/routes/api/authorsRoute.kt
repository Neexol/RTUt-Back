package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.respondThrowable
import ru.neexol.repositories.AuthorsRepository

fun Route.authorsRoute() {
    get("authors") {
        runCatching {
            AuthorsRepository.getNewAuthorId()
        }.onSuccess {
            call.respond(it)
        }.onFailure {
            call.respondThrowable(it)
        }
    }
}