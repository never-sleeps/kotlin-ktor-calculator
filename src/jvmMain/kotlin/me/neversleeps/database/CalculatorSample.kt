package me.neversleeps.database

import me.neversleeps.CalculatorAction
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction

object CalculatorSample : IntIdTable() {
    val calcId = integer("calc_id")

    val first = double("first")
    val second = double("second")
    val result = double("result")
    val action = enumeration("action", CalculatorAction::class)
}

fun <T> database(statement: Transaction.() -> T): T {
    Database.connect("jdbc:h2:calc-h2:8082./data/db;DB_CLOSE_DELAY=1", "org.h2.Driver")
    return transaction {
        statement()
    }
}
