package me.neversleeps

import me.neversleeps.component.CalculatorComponent
import me.neversleeps.services.CalculatorService
import react.create
import react.dom.client.createRoot

fun main() {
    web.window.window.onload = {
        val container = web.dom.document.getElementById("root")!!

        createRoot(container).render(
            CalculatorComponent.create {
                first = "2"
                second = "3"
                calculatorService = CalculatorService()
            },
//            Welcome.create {
//                name = "Kotlin'er"
//            }
        )
    }
}
