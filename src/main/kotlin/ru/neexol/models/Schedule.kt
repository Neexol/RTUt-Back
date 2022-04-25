package ru.neexol.models

@kotlinx.serialization.Serializable
data class Schedule(
    val group: String,
    val checksum: String,
    val lessons: List<Lesson>
)