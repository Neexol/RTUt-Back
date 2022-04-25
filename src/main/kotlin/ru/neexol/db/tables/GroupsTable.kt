package ru.neexol.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object GroupsTable : IntIdTable() {
    val name = text("name").uniqueIndex()
    val file = reference("file", FilesTable, onDelete = ReferenceOption.CASCADE)
}