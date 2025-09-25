// Ejemplo 1: when con cadenas (semáforo)
fun ejemploSemaforo() {
    val trafficLightColor = "Yellow"
    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow", "Amber" -> println("Slow")
        "Green" -> println("Go")
        else -> println("Invalid traffic-light color")
    }
}

// Ejemplo 2: when con valores concretos (primo: caso verdadero)
fun ejemploPrimos_Primo() {
    val x = 3
    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}

// Ejemplo 3: when con rangos (no primo pero dentro de 1..10)
fun ejemploPrimos_NoPrimoEnRango() {
    val x = 4
    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        else -> println("x isn't a prime number between 1 and 10.")
    }
}

// Ejemplo 4: when con comprobación de tipo
fun ejemploTipos() {
    val x: Any = 20
    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        is Int -> println("x is an integer number, but not between 1 and 10.")
        else -> println("x isn't an integer number.")
    }
}

// Punto de entrada único
fun main() {
    println("--- Ejemplo 1: Semáforo ---")
    ejemploSemaforo()

    println("\n--- Ejemplo 2: Primo entre 1 y 10 (caso primo) ---")
    ejemploPrimos_Primo()

    println("\n--- Ejemplo 3: Primo entre 1 y 10 (no primo pero dentro del rango) ---")
    ejemploPrimos_NoPrimoEnRango()

    println("\n--- Ejemplo 4: When con tipos ---")
    ejemploTipos()
}
