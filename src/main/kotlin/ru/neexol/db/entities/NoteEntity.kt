package ru.neexol.db.entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.neexol.db.tables.NotesTable
import java.util.*

class NoteEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<NoteEntity>(NotesTable)
    var text   by NotesTable.text
    var lesson by LessonEntity referencedOn NotesTable.lesson
    var weeks  by NotesTable.weeks
    var author by AuthorEntity referencedOn NotesTable.author
    var type   by NotesTable.type
}