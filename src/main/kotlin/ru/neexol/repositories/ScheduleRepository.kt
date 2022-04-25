package ru.neexol.repositories

import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.GroupEntity
import ru.neexol.db.entities.LessonEntity
import ru.neexol.db.tables.GroupsTable
import ru.neexol.db.tables.LessonsTable
import ru.neexol.exceptions.GroupNotFoundException
import ru.neexol.models.Schedule
import ru.neexol.utils.ilike

object ScheduleRepository {
    suspend fun getScheduleByGroup(groupName: String) = dbQuery {
        GroupEntity.find {
            GroupsTable.name eq groupName
        }.singleOrNull()?.run {
            Schedule(name, file.checksum, lessons.map { it.toLesson() })
        } ?: throw GroupNotFoundException()
    }

    suspend fun getScheduleByTeacher(teacher: String) = dbQuery {
        LessonEntity.find {
            LessonsTable.teacher ilike "%$teacher%"
        }.distinctBy {
            "${it.teacher}${it.classroom}${it.day}${it.number}"
        }.map {
            it.toLesson()
        }
    }
}