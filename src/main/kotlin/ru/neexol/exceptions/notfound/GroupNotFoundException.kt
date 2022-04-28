package ru.neexol.exceptions.notfound

import io.ktor.http.*
import ru.neexol.exceptions.HttpException

class GroupNotFoundException: HttpException(HttpStatusCode.NotFound, "Group not found.")