package ru.neexol.exceptions.notfound

import io.ktor.http.*
import ru.neexol.exceptions.HttpException

class LessonNotFoundException: HttpException(HttpStatusCode.NotFound, "Lesson not found.")