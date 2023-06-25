package me.neversleeps

import io.ktor.http.* // ktlint-disable no-wildcard-imports
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import me.neversleeps.database.CalculatorSample
import me.neversleeps.database.database
import me.neversleeps.services.CalculatorService
import org.jetbrains.exposed.sql.SchemaUtils

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            id = "root"
        }
        script(src = "/static/kotlin-ktor-calculator.js") {}
    }
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(ContentNegotiation) {
            jackson {}
        }

        database {
            SchemaUtils.create(CalculatorSample)
        }

        val calculatorService = CalculatorService()

        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            route("/api/calc") {
                post {
                    val request = call.receive<CalculatorData>()
                    calculatorService.addData(request)
                    call.respondText("OK")
                }
                get {
                    val samples = calculatorService.getAll()
                    call.respond(HttpStatusCode.OK, samples)
                }
            }
            static("/static") {
                resources()
            }
        }
    }.start(wait = true)
}
