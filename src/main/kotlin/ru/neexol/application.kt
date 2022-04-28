package ru.neexol

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.neexol.db.DatabaseFactory
import ru.neexol.plugins.installPlugins
import ru.neexol.repositories.ParserRepository

fun main() {
    embeddedServer(Netty) {
        DatabaseFactory.init()
        installPlugins()
        ParserRepository.periodicUpdateJob.start()
    }.start(wait = true)
}
