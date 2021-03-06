package ru.neexol.db.entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.neexol.db.tables.LessonsTable
import ru.neexol.db.tables.NotesTable
import ru.neexol.models.responses.LessonResponse
import java.util.*

class LessonEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<LessonEntity>(LessonsTable) {
        const val SEPARATOR = " "
    }
    var name      by LessonsTable.name
    var type      by LessonsTable.type
    var teacher   by LessonsTable.teacher
    var classroom by LessonsTable.classroom
    var group     by GroupEntity referencedOn LessonsTable.group
    var day       by LessonsTable.day
    var number    by LessonsTable.number
    val notes     by NoteEntity referrersOn NotesTable.lesson
    var weeks     by LessonsTable.weeks.transform(
        { it.joinToString(SEPARATOR) },
        { it.split(SEPARATOR).mapNotNull(String::toIntOrNull) }
    )

    fun toLessonResponse() = LessonResponse(id.toString(), name, type, teacher, classroom, day, number, weeks)
}