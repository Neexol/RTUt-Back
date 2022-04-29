package ru.neexol.models.responses

import ru.neexol.utils.NoteType

@kotlinx.serialization.Serializable
data class NoteResponse(
    val id: String,
    val text: String,
    val lessonId: String,
    val weeks: String,
    val authorId: String,
    val type: NoteType
)