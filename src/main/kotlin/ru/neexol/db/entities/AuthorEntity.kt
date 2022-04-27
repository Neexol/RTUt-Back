package ru.neexol.db.entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.neexol.db.tables.AuthorsTable
import ru.neexol.db.tables.NotesTable
import java.util.UUID

class AuthorEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<AuthorEntity>(AuthorsTable)
    val notes by NoteEntity referrersOn NotesTable.author
}