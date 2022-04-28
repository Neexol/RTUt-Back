package ru.neexol.plugins

import io.ktor.server.application.*

fun Application.installPlugins() {
    installCallLogging()
    installContentNegotiation()
    installStatusPages()
    installRouting()
}