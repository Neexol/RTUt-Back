package ru.neexol.routes

import io.ktor.server.routing.*
import ru.neexol.routes.api.notes.notesRoute
import ru.neexol.routes.api.schedule.scheduleRoute

fun Route.apiRoute() {
    route("api") {
        scheduleRoute()
        notesRoute()
    }
}