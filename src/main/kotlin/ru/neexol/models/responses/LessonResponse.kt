package ru.neexol.models.responses

@kotlinx.serialization.Serializable
data class LessonResponse(
    val id: String,
    val name: String,
    val type: String,
    val teacher: String,
    val classroom: String,
    val day: Int,
    val number: Int,
    val weeks: List<Int>
)