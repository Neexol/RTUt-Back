package ru.neexol.parsers.website

import org.jsoup.Jsoup
import java.net.URL

object WebsiteParser {
    private const val SCHEDULE_URL = "https://www.mirea.ru/schedule/"
    private const val LINKS_SELECTOR = "#tab-content > " +
            "li:nth-child(1) a.uk-link-toggle, " +
            "li:nth-child(2) a.uk-link-toggle"

    private fun isExamOrTest(s: String) = s.contains("экз") || s.contains("зач")
    private fun isNotExamOrTest(s: String) = !isExamOrTest(s)

    fun parseLessonsFilesURLs() = parseFilesPaths(::isNotExamOrTest)
    fun parseExamsOrTestFilesURLs() = parseFilesPaths(::isExamOrTest)

    private fun parseFilesPaths(predicate: (String) -> Boolean) = Jsoup.connect(SCHEDULE_URL).get()
        .select(LINKS_SELECTOR)
        .eachAttr("href")
        .filter { it.endsWith(".xlsx") }
        .filter(predicate)
        .map { ScheduleFile(URL(it)) }
}