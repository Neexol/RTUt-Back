package ru.neexol.exceptions

import io.ktor.http.*

class MissingParametersException : HttpException(HttpStatusCode.UnprocessableEntity, "Missing parameters.")