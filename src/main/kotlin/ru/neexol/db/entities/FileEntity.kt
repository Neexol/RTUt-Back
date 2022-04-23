package ru.neexol.db.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.neexol.db.tables.FilesTable
import ru.neexol.db.tables.GroupsTable

class FileEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FileEntity>(FilesTable)
    var name     by FilesTable.name
    var checksum by FilesTable.checksum
    val groups   by GroupEntity referrersOn GroupsTable.file
//    val lessons  by LessonEntity via GroupsTable
}