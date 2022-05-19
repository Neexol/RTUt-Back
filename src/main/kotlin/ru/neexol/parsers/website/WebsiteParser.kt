package ru.neexol.parsers.website

import org.jsoup.Jsoup
import java.net.URL

object WebsiteParser {
    private const val SCHEDULE_URL = "https://www.mirea.ru/schedule/"
    private const val LINKS_SELECTOR = "#tab-content > " +
            "li:nth-child(1) a.uk-link-toggle, " +
            "li:nth-child(2) a.uk-link-toggle"

    fun parseLessonsFiles() = Jsoup.connect(SCHEDULE_URL).get()
        .select(LINKS_SELECTOR)
        .eachAttr("href")
        .filter { it.endsWith(".xlsx") && (it.contains("экз") || it.contains("зач")) }
        .map { ScheduleFile(URL(it)) }
}