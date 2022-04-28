package ru.neexol.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.neexol.routes.apiRoute

fun Application.installRouting() {
    install(Routing) {
        apiRoute()
    }
}