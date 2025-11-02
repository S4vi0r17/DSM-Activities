// 04 - Excepciones y cancelación
// Ejemplos: excepción propagada, manejo con try/catch en el llamador, manejo en el productor (async), y cancelación.

import kotlinx.coroutines.*

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperatureWithError(): String {
    delay(500)
    throw AssertionError("Temperature is invalid")
}

suspend fun getWeatherReportError(): String = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async { getTemperatureWithError() }
    "${"\$"}{forecast.await()} ${"\$"}{temperature.await()}"
}

suspend fun getWeatherReportProducerHandled(): String = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async {
        try {
            getTemperatureWithError()
        } catch (e: AssertionError) {
            println("Caught exception in producer: $e")
            "{ No temperature found }"
        }
    }
    "${"\$"}{forecast.await()} ${"\$"}{temperature.await()}"
}

fun main() {
    runBlocking {
        println("Weather forecast")

        // Ejemplo 1: capturar la excepción en el llamador (runBlocking)
        try {
            println(getWeatherReportError())
        } catch (e: AssertionError) {
            println("Caught exception in runBlocking(): $e")
            println("Report unavailable at this time")
        }

        // Ejemplo 2: manejo dentro del productor (async) - el informe imprime pronóstico aunque falle temperatura
        println(getWeatherReportProducerHandled())

        println("Have a good day!")
    }
}

// Salida esperada al ejecutar este main:
// Weather forecast
// Caught exception in runBlocking(): java.lang.AssertionError: Temperature is invalid
// Report unavailable at this time
// Caught exception in producer: java.lang.AssertionError: Temperature is invalid
// Sunny { No temperature found }
// Have a good day!
