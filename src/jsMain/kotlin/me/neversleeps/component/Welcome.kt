package me.neversleeps.component

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useState
import web.html.InputType

external interface WelcomeProps : Props {
    var name: String
}

val Welcome = FC<WelcomeProps> {
    var name by useState(it.name)

    div {
        +"Hello from Kotlin+JS, $name!"
    }
    input {
        type = InputType.text
        value = name
        onChange = { e ->
            name = e.target.value
        }
    }
}
