package ru.neexol.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.neexol.routes.apiRoute
import ru.neexol.routes.staticRoute

fun Application.installRouting() {
    install(Routing) {
        apiRoute()
        staticRoute()
    }
}