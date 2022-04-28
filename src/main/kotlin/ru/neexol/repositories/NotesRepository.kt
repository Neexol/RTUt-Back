package ru.neexol.repositories

import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.AuthorEntity
import ru.neexol.db.entities.LessonEntity
import ru.neexol.db.entities.NoteEntity
import ru.neexol.exceptions.ConflictException
import ru.neexol.exceptions.notfound.AuthorNotFoundException
import ru.neexol.exceptions.notfound.LessonNotFoundException
import ru.neexol.exceptions.notfound.NoteNotFoundException
import ru.neexol.models.requests.PutNoteRequest
import java.util.*

object NotesRepository {
    suspend fun putNote(request: PutNoteRequest) = dbQuery {
        if (request.id == null) {
            createNote(request)
        } else {
            editNote(request)
        }.toNoteResponse()
    }

    private fun createNote(request: PutNoteRequest): NoteEntity {
        val lesson = LessonEntity.findById(UUID.fromString(request.lessonId)) ?: throw LessonNotFoundException()
        val author = AuthorEntity.findById(UUID.fromString(request.authorId)) ?: throw AuthorNotFoundException()
        val isConflict = author.notes.any {
            it.lesson == lesson && it.weeks == request.weeks && it.type == request.type
        }
        if (!isConflict) {
            return NoteEntity.new {
                this.text   = request.text
                this.lesson = lesson
                this.weeks  = request.weeks
                this.author = author
                this.type   = request.type
            }
        } else throw ConflictException()
    }

    private fun editNote(request: PutNoteRequest) = NoteEntity.findById(UUID.fromString(request.id!!))?.apply {
        text = request.text
        weeks = request.weeks
        type = request.type
    } ?: throw NoteNotFoundException()
}