package ru.neexol.routes.api.notes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.MissingParametersException
import ru.neexol.repositories.NotesRepository

fun Route.notesRoute() {
    route("notes") {
        authorsRoute()
        getNotesEndpoint()
        route("{noteId?}") {
            putNoteEndpoint()
            deleteNoteEndpoint()
        }
    }
}

private fun Route.getNotesEndpoint() {
    get {
        val response = call.parameters["lessonId"]?.let { lessonId ->
            call.parameters["week"]?.let { week ->
                call.parameters["authorId"]?.let { authorId ->
                    NotesRepository.getNotes(lessonId, week, authorId)
                } ?: throw MissingParametersException("authorId")
            } ?: throw MissingParametersException("week")
        } ?: throw MissingParametersException("lessonId")
        call.respond(response)
    }
}

private fun Route.putNoteEndpoint() {
    put {
        val response = NotesRepository.putNote(call.parameters["noteId"], call.receive())
        call.respond(response)
    }
}

private fun Route.deleteNoteEndpoint() {
    delete {
        val response = call.parameters["noteId"]?.let {  noteId ->
            call.parameters["authorId"]?.let {  authorId ->
                NotesRepository.deleteNote(noteId, authorId)
            } ?: throw MissingParametersException("authorId")
        } ?: throw MissingParametersException("noteId")
        call.respond(response)
    }
}