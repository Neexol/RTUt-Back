package ru.neexol.repositories

import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.AuthorEntity
import ru.neexol.exceptions.NotFoundException
import java.util.*

object AuthorsRepository {
    suspend fun putAuthorId(authorId: String?) = dbQuery {
        if (authorId == null) {
            AuthorEntity.new {}
        } else {
            AuthorEntity.findById(UUID.fromString(authorId)) ?: throw NotFoundException("author")
        }.id.toString()
    }
}