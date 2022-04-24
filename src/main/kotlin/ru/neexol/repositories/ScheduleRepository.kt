package ru.neexol.repositories

import org.jetbrains.exposed.sql.and
import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.GroupEntity
import ru.neexol.db.entities.LessonEntity
import ru.neexol.db.tables.GroupsTable
import ru.neexol.db.tables.LessonsTable
import ru.neexol.exceptions.GroupNotFoundException
import ru.neexol.utils.ilike

object ScheduleRepository {
    suspend fun getScheduleByGroup(groupName: String) = dbQuery {
        GroupEntity.find {
            GroupsTable.name eq groupName
        }.singleOrNull()?.lessons?.map {
            it.toLesson()
        } ?: throw GroupNotFoundException()
    }

    suspend fun getScheduleByTeacher(teacher: String, week: Int) = dbQuery {
        LessonEntity.find {
            (LessonsTable.teacher ilike "%$teacher%") and (LessonsTable.weeks like "%$week%")
        }.distinctBy {
            "${it.teacher}${it.classroom}${it.day}${it.number}"
        }.map {
            it.toLesson()
        }
    }
}