package ru.neexol.exceptions

import io.ktor.http.*

class UnauthorizedException(text: String) : HttpException(HttpStatusCode.Unauthorized, "No access: $text")