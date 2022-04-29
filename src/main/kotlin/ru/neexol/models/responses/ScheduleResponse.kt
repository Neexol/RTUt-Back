package ru.neexol.models.responses

@kotlinx.serialization.Serializable
data class ScheduleResponse(
    val group: String,
    val checksum: String,
    val lessons: List<LessonResponse>
)