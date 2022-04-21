package ru.neexol.db.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object LessonsTable : UUIDTable() {
    val name      = text("name")
    val type      = text("type")
    val teacher   = text("teacher")
    val classroom = text("classroom")
    val group     = text("group")
    val day       = integer("day")
    val number    = integer("number")
    val weeks     = text("weeks")
    val file      = reference("file", FilesTable)
}