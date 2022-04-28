package ru.neexol.db.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import ru.neexol.utils.NoteType

object NotesTable : UUIDTable() {
    val text   = text("text")
    val lesson = reference("lesson", LessonsTable, onDelete = ReferenceOption.CASCADE)
    val weeks  = text("weeks")
    val author = reference("author", AuthorsTable, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)
    val type   = enumerationByName<NoteType>("type", 7)
}