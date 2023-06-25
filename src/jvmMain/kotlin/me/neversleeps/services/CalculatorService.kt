package me.neversleeps.services

import me.neversleeps.CalculatorData
import me.neversleeps.database.CalculatorSample
import me.neversleeps.database.database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

actual class CalculatorService {
    actual suspend fun addData(sample: CalculatorData) {
        return database {
            CalculatorSample.insert {
                it[calcId] = sample.id
                it[first] = sample.first
                it[second] = sample.second
                it[result] = sample.result
                it[action] = sample.action
            }
        }
    }

    actual suspend fun getAll(): List<CalculatorData> {
        return database {
            CalculatorSample.selectAll().map {
                CalculatorData(
                    it[CalculatorSample.calcId],
                    it[CalculatorSample.first],
                    it[CalculatorSample.second],
                    it[CalculatorSample.result],
                    it[CalculatorSample.action],
                )
            }
        }
    }
}
