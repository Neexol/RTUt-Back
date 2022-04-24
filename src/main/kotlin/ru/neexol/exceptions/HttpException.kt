package ru.neexol.exceptions

import io.ktor.http.*

abstract class HttpException(
    val httpCode: HttpStatusCode,
    val text: String
) : Exception()