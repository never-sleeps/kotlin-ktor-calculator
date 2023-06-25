package me.neversleeps.component

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.neversleeps.CalculatorAction
import me.neversleeps.CalculatorData
import me.neversleeps.services.CalculatorService
import react.FC
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.useState
import web.html.InputType
import kotlin.random.Random

external interface CalculatorProps : Props {
    var first: String
    var second: String
    var calculatorService: CalculatorService
}

@OptIn(DelicateCoroutinesApi::class)
val CalculatorComponent = FC<CalculatorProps> {
    var first by useState(it.first)
    var second by useState(it.second)
    var action by useState(CalculatorAction.ADD)
    var result by useState<String?>(undefined)

    val calculatorService = it.calculatorService

    h2 {
        +"Calculator"
    }

    input {
        type = InputType.number
        value = first
        onChange = { e ->
            first = e.target.value
        }
    }

    input {
        type = InputType.number
        value = second
        onChange = { e ->
            second = e.target.value
        }
    }

    select {
        option {
            value = CalculatorAction.ADD
            +CalculatorAction.ADD.code
        }
        option {
            value = CalculatorAction.SUBTRACT
            +CalculatorAction.SUBTRACT.code
        }
        option {
            value = CalculatorAction.MULTIPLY
            +CalculatorAction.MULTIPLY.code
        }
        option {
            value = CalculatorAction.DIVIDE
            +CalculatorAction.DIVIDE.code
        }
        onChange = { e ->
            action = CalculatorAction.valueOf(e.target.value)
        }
    }

    div {
        button {
            +"Compute"
            onClick = {
                GlobalScope.launch {
                    val firstValue = first.toDoubleOrNull() ?: 0.0
                    val secondValue = second.toDoubleOrNull() ?: 0.0

                    val res = when (action) {
                        CalculatorAction.ADD -> firstValue + secondValue
                        CalculatorAction.SUBTRACT -> firstValue - secondValue
                        CalculatorAction.MULTIPLY -> firstValue * secondValue
                        CalculatorAction.DIVIDE -> firstValue / secondValue
                    }

                    calculatorService.addData(
                        CalculatorData(
                            Random.nextInt(),
                            firstValue,
                            secondValue,
                            res,
                            action,
                        ),
                    )

                    result = res.toString()
                }
            }
        }
    }

    br { }

    if (result != null) {
        div {
            +result
        }
    }
}
