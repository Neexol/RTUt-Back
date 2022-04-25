package ru.neexol.exceptions

import io.ktor.http.*

class GroupNotFoundException: HttpException(HttpStatusCode.NotFound, "Group not found.")