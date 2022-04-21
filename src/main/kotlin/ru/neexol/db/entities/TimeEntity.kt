package ru.neexol.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.neexol.db.tables.TimesTable

class TimeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TimeEntity>(TimesTable)
    var begin  by TimesTable.begin
    var end    by TimesTable.end
}