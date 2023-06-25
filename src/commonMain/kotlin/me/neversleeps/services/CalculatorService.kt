package me.neversleeps.services

import me.neversleeps.CalculatorData

expect class CalculatorService {
    suspend fun addData(sample: CalculatorData)

    suspend fun getAll(): List<CalculatorData>
}
