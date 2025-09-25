// 1) if como SENTENCIA (solo ejecuta prints, no devuelve valor)
fun ifComoSentencia() {
    val trafficLightColor = "Black"

    if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else if (trafficLightColor == "Green") {
        println("Go")
    } else {
        println("Invalid traffic-light color")
    }
}

// 2) if como EXPRESIÓN, pero las ramas llaman a println (el resultado es Unit)
//    Aquí `message` es de tipo Unit, porque println(...) devuelve Unit.
fun ifComoExpresionUnit() {
    val trafficLightColor = "Black"

    val message = if (trafficLightColor == "Red") {
        println("Stop")
    } else if (trafficLightColor == "Yellow") {
        println("Slow")
    } else if (trafficLightColor == "Green") {
        println("Go")
    } else {
        println("Invalid traffic-light color")
    }
    // message es Unit
}

// 3) if como EXPRESIÓN que devuelve un String
fun ifComoExpresionValor() {
    val trafficLightColor = "Black"

    val message =
        if (trafficLightColor == "Red") "Stop"
        else if (trafficLightColor == "Yellow") "Slow"
        else if (trafficLightColor == "Green") "Go"
        else "Invalid traffic-light color"

    println(message)
}

// 4) when como SENTENCIA (solo ejecuta prints)
fun whenComoSentencia() {
    val trafficLightColor = "Amber"

    when (trafficLightColor) {
        "Red" -> println("Stop")
        "Yellow", "Amber" -> println("Slow")
        "Green" -> println("Go")
        else -> println("Invalid traffic-light color")
    }
}

// 5) when como EXPRESIÓN que devuelve un String
fun whenComoExpresionValor() {
    val trafficLightColor = "Amber"

    val message = when (trafficLightColor) {
        "Red" -> "Stop"
        "Yellow", "Amber" -> "Slow"
        "Green" -> "Go"
        else -> "Invalid traffic-light color"
    }
    println(message)
}

// ÚNICO punto de entrada
fun main() {
    println("=== if como sentencia ===")
    ifComoSentencia()

    println("\n=== if como expresión (ramas con println: resultado Unit) ===")
    ifComoExpresionUnit()

    println("\n=== if como expresión (devuelve String) ===")
    ifComoExpresionValor()

    println("\n=== when como sentencia ===")
    whenComoSentencia()

    println("\n=== when como expresión (devuelve String) ===")
    whenComoExpresionValor()
}
