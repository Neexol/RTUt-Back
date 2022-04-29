package ru.neexol.routes.api.notes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.repositories.AuthorsRepository

fun Route.authorsRoute() {
    route("authors/{authorId?}") {
        postAuthorEndpoint()
    }
}

private fun Route.postAuthorEndpoint() {
    put {
        val response = AuthorsRepository.putAuthorId(call.parameters["authorId"])
        call.respond(response)
    }
}