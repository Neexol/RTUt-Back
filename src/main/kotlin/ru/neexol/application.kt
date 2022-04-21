package ru.neexol

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.db.DatabaseFactory
import ru.neexol.plugins.*

fun main() {
    embeddedServer(Netty) {
        DatabaseFactory.init()

        installCallLogging()
        installContentNegotiation()

        routing {
            get("/") {
                call.respondText("Hello!")
            }
        }
    }.start(wait = true)
}
