package ru.neexol.routes

import io.ktor.server.routing.*
import ru.neexol.routes.api.*

fun Route.apiRoute() {
    updateRoute()
    checksumRoute()
    scheduleRoute()
}