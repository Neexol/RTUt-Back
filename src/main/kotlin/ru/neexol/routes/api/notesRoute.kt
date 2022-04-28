package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.respondThrowable
import ru.neexol.repositories.NotesRepository

fun Route.notesRoute() {
    put("/notes") {
        runCatching {
            NotesRepository.putNote(call.receive())
        }.onSuccess {
            call.respond(it)
        }.onFailure {
            call.respondThrowable(it)
        }
    }
}