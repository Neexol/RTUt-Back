package ru.neexol.parsers

import org.jsoup.Jsoup
import java.net.URL

object WebsiteParser {
    private const val SCHEDULE_URL = "https://www.mirea.ru/schedule/"
    private const val LINKS_SELECTOR = "#tab-content > li:nth-child(1) a.uk-link-toggle"

    private fun parseFilesPaths(predicate: (String) -> Boolean) = Jsoup.connect(SCHEDULE_URL).get()
        .select(LINKS_SELECTOR)
        .eachAttr("href")
        .mapNotNull {
            it.takeIf(predicate)?.let { path ->
                URL(path)
            }
        }

    fun parseLessonsFilesURLs() = parseFilesPaths { !it.contains("экз") }
    fun parseExamsFilesURLs() = parseFilesPaths { it.contains("экз") }
}