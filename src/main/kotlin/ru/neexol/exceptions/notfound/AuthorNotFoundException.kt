package ru.neexol.exceptions.notfound

import io.ktor.http.*
import ru.neexol.exceptions.HttpException

class AuthorNotFoundException: HttpException(HttpStatusCode.NotFound, "Author not found.")