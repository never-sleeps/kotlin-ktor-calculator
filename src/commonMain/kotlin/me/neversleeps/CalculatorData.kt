package me.neversleeps

import kotlinx.serialization.Serializable

enum class CalculatorAction(val code: String) {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/")
}

@Serializable
data class CalculatorData(
    val id: Int,

    val first: Double,
    val second: Double,
    val result: Double,

    val action: CalculatorAction,
)
