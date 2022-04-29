package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.MissingParametersException
import ru.neexol.repositories.NotesRepository

fun Route.notesRoute() {
    route("notes/{id?}") {
        putNoteEndpoint()
        deleteNoteEndpoint()
    }
}

private fun Route.putNoteEndpoint() {
    put {
        val response = NotesRepository.putNote(call.parameters["id"], call.receive())
        call.respond(response)
    }
}

private fun Route.deleteNoteEndpoint() {
    delete {
        val response = call.parameters["id"]?.let {  id ->
            call.parameters["authorId"]?.let {  authorId ->
                NotesRepository.deleteNote(id, authorId)
            } ?: throw MissingParametersException("authorId")
        } ?: throw MissingParametersException("id")
        call.respond(response)
    }
}