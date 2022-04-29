package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.repositories.NotesRepository

fun Route.notesRoute() {
    route("notes/{id?}") {
        putNoteEndpoint()
    }
}

private fun Route.putNoteEndpoint() {
    put {
        val response = NotesRepository.putNote(call.parameters["id"], call.receive())
        call.respond(response)
    }
}