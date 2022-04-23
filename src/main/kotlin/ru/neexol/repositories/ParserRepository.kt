package ru.neexol.repositories

import org.jetbrains.exposed.sql.deleteAll
import ru.neexol.db.DatabaseFactory.dbQuery
import ru.neexol.db.entities.FileEntity
import ru.neexol.db.entities.GroupEntity
import ru.neexol.db.entities.LessonEntity
import ru.neexol.db.tables.FilesTable
import ru.neexol.db.tables.GroupsTable
import ru.neexol.db.tables.LessonsTable
import ru.neexol.parsers.ExcelParser
import ru.neexol.parsers.WebsiteParser
import kotlin.io.path.Path
import kotlin.io.path.name

object ParserRepository {
    suspend fun updateSchedule() {
        val schedule = WebsiteParser.fetchLessonsFilesURLs().associate { url ->
            Path(url.path).name to url.openStream().use { ExcelParser(it).parse() }
        }

        dbQuery {
            LessonsTable.deleteAll()
            GroupsTable.deleteAll()
            FilesTable.deleteAll()

            schedule.forEach { (fileName, groups) ->
                val file = FileEntity.new {
                    this.name = fileName
                    this.checksum = "empty"
                }
                groups.forEach { (groupName, lessons) ->
                    val group = GroupEntity.new {
                        this.name = groupName
                        this.file = file
                    }
                    lessons.forEach { lesson ->
                        LessonEntity.new {
                            this.name = lesson.name
                            this.type = lesson.type
                            this.teacher = lesson.teacher
                            this.classroom = lesson.classroom
                            this.group = group
                            this.day = lesson.day
                            this.number = lesson.number
                            this.weeks = lesson.weeks
                        }
                    }
                }
            }
        }
    }
}