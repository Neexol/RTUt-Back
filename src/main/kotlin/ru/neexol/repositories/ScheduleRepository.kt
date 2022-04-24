package ru.neexol.repositories

import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.GroupEntity
import ru.neexol.db.tables.GroupsTable
import ru.neexol.exceptions.GroupNotFoundException

object ScheduleRepository {
    suspend fun getScheduleByGroup(groupName: String) = dbQuery {
        GroupEntity.find {
            GroupsTable.name eq groupName
        }.singleOrNull()?.lessons?.map {
            it.toLesson()
        } ?: throw GroupNotFoundException()
    }
}