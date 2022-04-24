package ru.neexol.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.neexol.exceptions.MissingParametersException
import ru.neexol.exceptions.respondThrowable
import ru.neexol.repositories.ScheduleRepository

fun Route.scheduleRoute() {
    get("/schedule") {
        runCatching {
            val (group, teacher, week) = listOf("group", "teacher", "week").map {
                call.request.queryParameters[it]
            }
            if (group != null) {
                ScheduleRepository.getScheduleByGroup(group)
            } else if (teacher != null && week != null) {
                ScheduleRepository.getScheduleByTeacher(teacher, week.toInt())
            } else {
                throw MissingParametersException()
            }
        }.onSuccess {
            call.respond(it)
        }.onFailure {
            call.respondThrowable(it)
        }
    }
}