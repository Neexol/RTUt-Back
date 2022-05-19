package ru.neexol.repositories

import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.neexol.db.entities.FileEntity
import ru.neexol.db.entities.GroupEntity
import ru.neexol.db.entities.LessonEntity
import ru.neexol.db.tables.FilesTable
import ru.neexol.db.tables.GroupsTable
import ru.neexol.parsers.excel.ExcelParser
import ru.neexol.parsers.excel.Lesson
import ru.neexol.parsers.website.WebsiteParser

object ParserRepository {
    val periodicUpdateJob = CoroutineScope(Dispatchers.IO).launch(start = CoroutineStart.LAZY) {
        while (true) {
            updateSchedule()
            delay(60 * 60 * 1000)
        }
    }

    suspend fun forceUpdateSchedule() = withContext(Dispatchers.IO) {
        transaction {
            FilesTable.deleteAll()
        }
        updateSchedule()
    }

    suspend fun updateSchedule() = withContext(Dispatchers.IO) {
        WebsiteParser.parseLessonsFiles().map { file ->
            launch(Dispatchers.IO) {
                transaction {
                    if (findFile(file.fileName)?.checksum != file.checksum) {
                        val groups = ExcelParser(file.bytes.inputStream()).parse()
                        deleteFileLessons(groups)
                        addFileLessons(file.fileName, file.checksum, groups)
                    }
                }
            }
        }.joinAll()
    }

    private fun addFileLessons(fileName: String, checksum: String, groups: Map<String, List<Lesson>>) {
        val file = insertFile(fileName, checksum)
        groups.forEach { (groupName, lessons) ->
            findGroup(groupName) ?: run {
                val group = insertGroup(groupName, file)
                lessons.forEach { lesson ->
                    insertLesson(lesson, group)
                }
            }
        }
    }

    private fun deleteFileLessons(groups: Map<String, List<Lesson>>) {
        groups.keys.forEach { groupName ->
            GroupEntity.find {
                GroupsTable.name eq groupName
            }.forEach { it.delete() }
        }
        FileEntity.all().forEach {
            if (it.groups.empty()) {
                it.delete()
            }
        }
    }

    private fun findFile(fileName: String) = FileEntity.find {
        FilesTable.name eq fileName
    }.singleOrNull()

    private fun insertFile(fileName: String, checksum: String) = FileEntity.new {
        this.name     = fileName
        this.checksum = checksum
    }

    private fun findGroup(groupName: String) = GroupEntity.find {
        GroupsTable.name eq groupName
    }.singleOrNull()

    private fun insertGroup(groupName: String, file: FileEntity) = GroupEntity.new {
        this.name = groupName
        this.file = file
    }

    private fun insertLesson(lesson: Lesson, group: GroupEntity) = LessonEntity.new {
        this.name      = lesson.name
        this.type      = lesson.type
        this.teacher   = lesson.teacher
        this.classroom = lesson.classroom
        this.group     = group
        this.day       = lesson.day
        this.number    = lesson.number
        this.weeks     = lesson.weeks
    }
}