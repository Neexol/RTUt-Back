package ru.neexol.parsers.website

import org.apache.commons.codec.digest.DigestUtils
import java.net.URL
import kotlin.io.path.Path
import kotlin.io.path.name

class ScheduleFile(url: URL) {
    val fileName = Path(url.path).name
    val bytes = url.readBytes()
    val checksum: String = DigestUtils.sha256Hex(bytes)
}