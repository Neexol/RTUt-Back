package ru.neexol.db.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption

object LessonsTable : UUIDTable() {
    val name      = text("name")
    val type      = text("type")
    val teacher   = text("teacher")
    val classroom = text("classroom")
    val group     = reference("group", GroupsTable, onDelete = ReferenceOption.CASCADE)
    val day       = integer("day")
    val number    = integer("number")
    val weeks     = text("weeks")
}