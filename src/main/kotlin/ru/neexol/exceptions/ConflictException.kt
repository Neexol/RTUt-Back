package ru.neexol.exceptions

import io.ktor.http.*

class ConflictException: HttpException(HttpStatusCode.Conflict, "Conflict")