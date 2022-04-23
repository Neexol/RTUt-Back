package ru.neexol.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.neexol.db.tables.GroupsTable
import ru.neexol.db.tables.LessonsTable

class GroupEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GroupEntity>(GroupsTable)
    var name    by GroupsTable.name
    var file    by FileEntity referencedOn GroupsTable.file
    val lessons by LessonEntity referrersOn LessonsTable.group
}