package ru.neexol.models

@kotlinx.serialization.Serializable
data class Lesson(
    val name: String,
    val type: String,
    val teacher: String,
    val classroom: String,
    val day: Int,
    val number: Int,
    val weeks: List<Int>
)