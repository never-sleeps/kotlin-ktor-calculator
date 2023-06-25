package me.neversleeps.services

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.neversleeps.CalculatorData
import org.w3c.fetch.RequestInit
import kotlin.coroutines.coroutineContext
import kotlin.js.json

actual class CalculatorService {
    actual suspend fun addData(sample: CalculatorData) {
        fetch("/api/calc", "POST", Json.encodeToString(sample))
        return
    }

    actual suspend fun getAll(): List<CalculatorData> {
        return parse(
            ListSerializer(CalculatorData.serializer()),
            fetch("/api/calc", "POST"),
        )
    }

    private fun <T> parse(strategy: DeserializationStrategy<T>, data: String): T {
        return Json.decodeFromString(strategy, data)
    }

    private suspend fun fetch(
        endpoint: String,
        method: String,
        body: String? = undefined,
    ): String {
        return withContext(coroutineContext) {
            val response = window.fetch(
                endpoint,
                RequestInit(
                    method = method,
                    body = body,
                    headers = json(
                        "Accept" to "application/json",
                        "Content-Type" to "application/json",
                    ),
                ),
            ).await()

            response.text().await()
        }
    }
}
