package ru.neexol.models.responses

@kotlinx.serialization.Serializable
data class LessonTimeResponse(
    val begin: String,
    val end: String
)