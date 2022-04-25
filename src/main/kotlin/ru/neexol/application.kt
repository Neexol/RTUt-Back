package ru.neexol

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import ru.neexol.db.DatabaseFactory
import ru.neexol.repositories.ParserRepository
import ru.neexol.routes.scheduleRoute

fun main() {
    embeddedServer(Netty) {
        DatabaseFactory.init()

        install(CallLogging) {
            level = Level.INFO
            filter { call -> call.request.path().startsWith("/") }
        }
        install(ContentNegotiation) {
            json()
        }

        routing {
            get("/") {
                call.respondText("Hello!")
            }
            get("/update") {
                ParserRepository.updateSchedule()
                call.respondText("Updated!")
            }
            scheduleRoute()
        }
    }.start(wait = true)
}
