package me.neversleeps // ktlint-disable filename

actual fun log(message: String, level: LogLevel) {
    println("$level: $message")
}
