package ru.neexol.exceptions

import io.ktor.http.*

abstract class HttpException(val httpCode: HttpStatusCode, private val text: String) : Exception() {
    override fun toString() = text
}