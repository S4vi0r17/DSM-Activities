// 03 - Código asíncrono
// Ejemplos: launch (fire-and-forget), async/await (Deferred) y coroutineScope (descomposición paralela).

import kotlin.system.*
import kotlinx.coroutines.*

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(1000)
    return "30\u00b0C"
}

suspend fun getWeatherReport(): String = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async { getTemperature() }
    "${"\$"}{forecast.await()} ${"\$"}{temperature.await()}"
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            println("Weather forecast")

            // launch() -> inicia corrutinas independientes
            launch { println(getWeatherReport()) } // esta línea lanza una corrutina que mostrará el informe

            // Si quieres ver async/await explícito en el mismo bloque:
            val f: Deferred<String> = runBlocking { async { getForecast() } }
            val t: Deferred<String> = runBlocking { async { getTemperature() } }
            // Nota: Los dos runBlocking anteriores son solo demostrativos; normalmente no anidar así.

            // El runBlocking principal esperará a las corrutinas hijas antes de terminar.
        }
    }
    println("Execution time (approx): ${time / 1000.0} seconds")
}

// Recomendación: para ver la ventaja de async/await, sustituye el uso secuencial por llamadas async
// dentro de un mismo scope y mide el tiempo de ejecución (debería ser ~1s vs ~2s secuencialmente).
