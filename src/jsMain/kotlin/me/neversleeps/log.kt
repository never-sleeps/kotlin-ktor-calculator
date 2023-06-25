package me.neversleeps

actual fun log(message: String, level: LogLevel) {
    console.log("$level: $message")
}
