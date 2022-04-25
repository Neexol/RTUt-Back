package ru.neexol.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.neexol.db.entities.FileEntity
import ru.neexol.db.entities.GroupEntity
import ru.neexol.db.entities.LessonEntity
import ru.neexol.db.tables.FilesTable
import ru.neexol.db.tables.GroupsTable
import ru.neexol.db.tables.LessonsTable
import ru.neexol.models.Lesson
import ru.neexol.parsers.ExcelParser
import ru.neexol.parsers.WebsiteParser
import kotlin.io.path.Path
import kotlin.io.path.name

object ParserRepository {
    suspend fun updateSchedule() = withContext(Dispatchers.IO) {
        transaction {
            LessonsTable.deleteAll()
            GroupsTable.deleteAll()
            FilesTable.deleteAll()
        }

        WebsiteParser.parseLessonsFilesURLs().map { url ->
            launch(Dispatchers.IO) {
                insertFileLessons(Path(url.path).name, url.openStream().use { ExcelParser(it).parse() })
            }
        }.joinAll()
    }

    private fun insertFileLessons(fileName: String, groups: Map<String, List<Lesson>>) = transaction {
        val file = insertFile(fileName)
        groups.forEach { (groupName, lessons) ->
            findGroup(groupName) ?: run {
                val group = insertGroup(groupName, file)
                lessons.forEach { lesson ->
                    insertLesson(lesson, group)
                }
            }
        }
    }

    private fun insertFile(fileName: String) = FileEntity.new {
        this.name = fileName
        this.checksum = "empty"
    }

    private fun findGroup(groupName: String) = GroupEntity.find {
        GroupsTable.name eq groupName
    }.singleOrNull()

    private fun insertGroup(groupName: String, file: FileEntity) = GroupEntity.new {
        this.name = groupName
        this.file = file
    }

    private fun insertLesson(lesson: Lesson, group: GroupEntity) = LessonEntity.new {
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