package ru.neexol.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object TimesTable : IntIdTable() {
    val begin = text("begin").default("")
    val end   = text("end").default("")
}