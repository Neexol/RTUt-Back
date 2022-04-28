package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.repositories.AuthorsRepository

fun Route.authorsRoute() {
    route("authors") {
        postAuthorEndpoint()
    }
}

private fun Route.postAuthorEndpoint() {
    post {
        val response = AuthorsRepository.getNewAuthorId()
        call.respond(response)
    }
}