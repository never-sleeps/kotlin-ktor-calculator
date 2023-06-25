package me.neversleeps

enum class LogLevel {
    WARN, ERROR, INFO
}

expect fun log(message: String, level: LogLevel)
