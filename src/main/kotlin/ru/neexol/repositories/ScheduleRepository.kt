package ru.neexol.repositories

import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.GroupEntity
import ru.neexol.db.entities.LessonEntity
import ru.neexol.db.tables.GroupsTable
import ru.neexol.db.tables.LessonsTable
import ru.neexol.exceptions.NotFoundException
import ru.neexol.models.responses.ScheduleResponse
import ru.neexol.utils.ilike

object ScheduleRepository {
    suspend fun getScheduleByGroup(groupName: String) = dbQuery {
        GroupEntity.find {
            GroupsTable.name eq groupName
        }.singleOrNull()?.run {
            ScheduleResponse(name, file.checksum, lessons.map { it.toLessonResponse() })
        } ?: throw NotFoundException("group")
    }

    suspend fun getScheduleByTeacher(teacher: String) = dbQuery {
        LessonEntity.find {
            LessonsTable.teacher ilike "%$teacher%"
        }.distinctBy {
            "${it.teacher}${it.classroom}${it.day}${it.number}"
        }.map {
            it.toLessonResponse()
        }
    }

    suspend fun getChecksumByGroup(groupName: String) = dbQuery {
        GroupEntity.find {
            GroupsTable.name eq groupName
        }.singleOrNull()?.file?.checksum ?: throw NotFoundException("group")
    }
}