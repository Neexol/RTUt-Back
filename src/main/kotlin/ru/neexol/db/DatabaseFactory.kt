package ru.neexol.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(hikari())

        transaction {
            with(SchemaUtils) {
                // tables
            }
            addLogger(StdOutSqlLogger)
        }
    }

    private fun hikari() = HikariDataSource(HikariConfig().apply {
        jdbcUrl = System.getenv("JDBC_DATABASE_URL")
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })
}