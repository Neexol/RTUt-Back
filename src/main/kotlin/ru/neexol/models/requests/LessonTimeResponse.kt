package ru.neexol.models.requests

@kotlinx.serialization.Serializable
data class LessonTimeResponse(
    val begin: String,
    val end: String
)