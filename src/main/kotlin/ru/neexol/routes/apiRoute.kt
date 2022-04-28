package ru.neexol.routes

import io.ktor.server.routing.*
import ru.neexol.routes.api.*

fun Route.apiRoute() {
    timesRoute()
    updateRoute()
    checksumRoute()
    scheduleRoute()
    notesRoute()
    authorsRoute()
}