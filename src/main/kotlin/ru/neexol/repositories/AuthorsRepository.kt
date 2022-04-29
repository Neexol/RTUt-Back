package ru.neexol.repositories

import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.AuthorEntity

object AuthorsRepository {
    suspend fun getNewAuthorId() = dbQuery {
        AuthorEntity.new {}.id.toString()
    }
}