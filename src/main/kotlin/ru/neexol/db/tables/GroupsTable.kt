package ru.neexol.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object GroupsTable : IntIdTable() {
    val name = text("name")
    val file = reference("file", FilesTable)
}