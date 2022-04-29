package ru.neexol.repositories

import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.AuthorEntity
import ru.neexol.db.entities.LessonEntity
import ru.neexol.db.entities.NoteEntity
import ru.neexol.exceptions.ConflictException
import ru.neexol.exceptions.NotFoundException
import ru.neexol.exceptions.UnauthorizedException
import ru.neexol.models.requests.PutNoteRequest
import ru.neexol.utils.NoteType
import java.util.*

object NotesRepository {
    suspend fun getNotes(lessonId: String, week: String, authorId: String) = dbQuery {
        LessonEntity.findById(UUID.fromString(lessonId))?.let { lesson ->
            lesson.notes.filter {
                it.weeks.contains(week) && (it.type == NoteType.PUBLIC || it.author.id.toString() == authorId)
            }.map { it.toNoteResponse() }
        } ?: throw NotFoundException("lesson")
    }

    suspend fun putNote(id: String?, request: PutNoteRequest) = dbQuery {
        val lesson = LessonEntity.findById(UUID.fromString(request.lessonId)) ?: throw NotFoundException("lesson")
        val author = AuthorEntity.findById(UUID.fromString(request.authorId)) ?: throw NotFoundException("author")
        val isConflict = author.notes.any {
            it.lesson == lesson && it.weeks == request.weeks && it.type == request.type
        }

        if (id == null) {
            if (isConflict) {
                throw ConflictException("notes limit")
            }

            NoteEntity.new {
                this.text   = request.text
                this.lesson = lesson
                this.weeks  = request.weeks
                this.author = author
                this.type   = request.type
            }
        } else {
            val note = NoteEntity.findById(UUID.fromString(id)) ?: throw NotFoundException("note")
            if (isConflict && (note.weeks != request.weeks || note.type != request.type)) {
                throw ConflictException("notes limit")
            }

            note.apply {
                text = request.text
                weeks = request.weeks
                type = request.type
            }
        }.toNoteResponse()
    }

    suspend fun deleteNote(id: String, authorId: String) = dbQuery {
        NoteEntity.findById(UUID.fromString(id))?.run {
            if (author.id.toString() == authorId) {
                delete()
                id
            } else throw UnauthorizedException("note")
        } ?: throw NotFoundException("note")
    }
}