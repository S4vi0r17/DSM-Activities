// 02 - Código síncrono
// Ejemplos: llamadas síncronas, uso de delay en una corrutina con runBlocking y medición de tiempo.

import kotlin.system.*
import kotlinx.coroutines.*

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            println("Weather forecast")
            // Llamada suspendible dentro de runBlocking
            delay(1000)
            println("Sunny")
        }
    }
    println("Execution time: ${time / 1000.0} seconds")
}

// Salida esperada:
// Weather forecast
// Sunny
// Execution time: ~1.0 seconds
