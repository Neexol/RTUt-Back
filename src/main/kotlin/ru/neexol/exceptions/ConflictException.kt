package ru.neexol.exceptions

import io.ktor.http.*

class ConflictException(text: String): HttpException(HttpStatusCode.Conflict, "Conflict: $text")