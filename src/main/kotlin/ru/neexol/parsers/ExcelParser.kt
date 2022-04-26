package ru.neexol.parsers

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import ru.neexol.models.Lesson
import java.io.InputStream

class ExcelParser(fileStream: InputStream) {
    private companion object {
        val GROUP_PATTERN = "[А-ЯЁ]{4}(-\\d{2}){2}".toRegex()
        val TEACHER_PATTERN = "[а-яёА-ЯЁ]+ [А-Я]\\.[А-Я]\\.".toRegex()
        val WEEKS_RANGE_PATTERN = "\\d+-\\d+".toRegex()
        val EMPTY_LESSON_PATTERN = "[…. ]*".toRegex()
        val WHITESPACES_PATTERN = " +".toRegex()
        val WEEKS_PREFIX_PATTERN = "(кр\\.? )?[ \\d,-]+(н +|н\\. *)".toRegex()

        const val EXCLUDE_SIGN = "кр"
        const val NEW_LINE_SYMBOL = '\n'

        const val START_PAYLOAD_ROW_INDEX = 3
        const val GROUP_ROW_INDEX = 1
        const val DAYS_NUMBER = 6
        const val ROWS_IN_LESSON_NUMBER = 2
        const val MIN_LESSONS_IN_DAY_NUMBER = 6
        const val MAX_LESSONS_IN_DAY_NUMBER = 9
        const val DEFAULT_LESSONS_IN_DAY_NUMBER = 6
        const val DEFAULT_ROWS_IN_DAY_NUMBER = ROWS_IN_LESSON_NUMBER * DEFAULT_LESSONS_IN_DAY_NUMBER
    }

    private enum class WeeksType { INCLUDE, EXCLUDE }

    private val wb = XSSFWorkbook(fileStream)
    private val wbSheet = wb.first()

    private val lessonInDayNumber = run {
        for (lessonNumber in MIN_LESSONS_IN_DAY_NUMBER..MAX_LESSONS_IN_DAY_NUMBER) {
            val rowIndex = START_PAYLOAD_ROW_INDEX + lessonNumber * ROWS_IN_LESSON_NUMBER
            if (wbSheet.getRow(rowIndex).first().toString().lowercase() == "вторник") {
                return@run lessonNumber
            }
        }
        throw RuntimeException("Tuesday not found.")
    }
    private val rowsInDayNumber = ROWS_IN_LESSON_NUMBER * lessonInDayNumber

    fun parse(): Map<String, List<Lesson>> = mutableMapOf<String, List<Lesson>>().apply {
        wbSheet.getRow(GROUP_ROW_INDEX).cellIterator().forEachRemaining { cell ->
            GROUP_PATTERN.find(cell.toString())?.let {
                put(it.value, getGroupLessons(cell.columnIndex))
            }
        }
    }.also { wb.close() }

    private fun getGroupLessons(colBias: Int): List<Lesson> = mutableListOf<Lesson>().apply {
        repeat((DAYS_NUMBER - 1) * rowsInDayNumber + DEFAULT_ROWS_IN_DAY_NUMBER) { row ->
            getLessonData(row, colBias).forEach { (rawName, type, teacher, classroom) ->
                if (!rawName.isEmptyLesson()) {
                    val (name, lessonWeeks) = getWeeks(row, rawName)
                    val day = row / rowsInDayNumber
                    val number = row % rowsInDayNumber / ROWS_IN_LESSON_NUMBER
                    add(Lesson(name.trim(), type.trim(), teacher.trim(), classroom.trim(), day, number, lessonWeeks))
                }
            }
        }
    }

    private fun getLessonData(rowIndex: Int, colBias: Int): List<List<String>> {
        val row = wbSheet.getRow(START_PAYLOAD_ROW_INDEX + rowIndex)
        val (name, type, teacher, classroom) = List(4) { col ->
            row.getCell(colBias + col)?.toString()
                ?.removeSuffix(".0")
                ?.replace(WHITESPACES_PATTERN, " ")
                ?: ""
        }

        val splitName = name.split(NEW_LINE_SYMBOL)
        val splitType = type.split(NEW_LINE_SYMBOL)
        val splitTeacher = if (TEACHER_PATTERN.containsMatchIn(teacher)) {
            TEACHER_PATTERN.findAll(teacher).map(MatchResult::value).toList()
        } else listOf(teacher)
        val splitClassroom = classroom.split(NEW_LINE_SYMBOL)

        return if (splitName.size == 1) {
            List(1) { listOf(
                splitName.first(),
                splitType.joinToString(" "),
                splitTeacher.joinToString(" "),
                splitClassroom.joinToString(" ")
            ) }
        } else {
            List(splitName.size) { listOf(
                splitName[it],
                splitType.getOrNull(it) ?: splitType.first(),
                splitTeacher.getOrNull(it) ?: splitTeacher.first(),
                splitClassroom.getOrNull(it) ?: splitClassroom.first()
            ) }
        }
    }

    private fun String.isEmptyLesson() = this matches EMPTY_LESSON_PATTERN

    private fun getWeeks(index: Int, rawName: String): Pair<String, List<Int>> {
        var name = rawName
        var resultWeeks = (index % 2 + 1..index % 2 + 15 step 2).toSet()

        WEEKS_PREFIX_PATTERN.find(name)?.let { match ->
            val weeksStr = match.value
                .dropLastWhile { !it.isDigit() }
                .replace(WHITESPACES_PATTERN, "")
            val (type, weeks) = parseWeeks(weeksStr)
            resultWeeks = if (type == WeeksType.INCLUDE) weeks else resultWeeks.minus(weeks)
            name = name.drop(match.value.length)
        }

        return name to resultWeeks.toList()
    }


    private fun parseWeeks(rawWeeks: String): Pair<WeeksType, Set<Int>> {
        var weeks = rawWeeks.dropWhile { !it.isDigit() }
        val type = if (rawWeeks.startsWith(EXCLUDE_SIGN)) {
            WeeksType.EXCLUDE
        } else {
            WeeksType.INCLUDE
        }

        WEEKS_RANGE_PATTERN.findAll(weeks).toList().reversed().forEach {
            weeks = weeks.replaceRange(it.range, it.value.expandWeeks())
        }

        return type to weeks.split(',').map(String::toInt).toSet()
    }

    private fun String.expandWeeks() = split('-').map(String::toInt).let {
        (it[0]..it[1] step 2).joinToString(",")
    }
}