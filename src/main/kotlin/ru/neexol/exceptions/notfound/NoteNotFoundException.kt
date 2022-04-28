package ru.neexol.exceptions.notfound

import io.ktor.http.*
import ru.neexol.exceptions.HttpException

class NoteNotFoundException: HttpException(HttpStatusCode.NotFound, "Note not found.")