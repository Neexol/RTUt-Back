package ru.neexol.exceptions

import io.ktor.http.*

class MissingParametersException(text: String) : HttpException(HttpStatusCode.UnprocessableEntity, "Missing parameters: $text")