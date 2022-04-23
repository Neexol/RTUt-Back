package ru.neexol.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object FilesTable : IntIdTable() {
    val name     = text("name")
    val checksum = text("checksum")
}