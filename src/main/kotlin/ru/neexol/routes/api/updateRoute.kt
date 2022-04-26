package ru.neexol.routes.api

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.repositories.ParserRepository

fun Route.updateRoute() {
    get("/update") {
        ParserRepository.updateSchedule()
        call.respondText("Updated!")
    }
    get("/force-update") {
        ParserRepository.forceUpdateSchedule()
        call.respondText("Updated!")
    }
}