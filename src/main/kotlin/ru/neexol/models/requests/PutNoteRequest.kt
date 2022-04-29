package ru.neexol.models.requests

import ru.neexol.utils.NoteType

@kotlinx.serialization.Serializable
data class PutNoteRequest(
    val text: String,
    val lessonId: String,
    val weeks: String,
    val authorId: String,
    val type: NoteType
)