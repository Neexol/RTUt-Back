package ru.neexol.models

@kotlinx.serialization.Serializable
data class LessonTime(
    val begin: String,
    val end: String
)
