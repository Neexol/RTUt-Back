package ru.neexol.exceptions

import io.ktor.http.*

class NotFoundException(text: String): HttpException(HttpStatusCode.NotFound, "Not found: $text")