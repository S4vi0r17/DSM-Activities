// Apuntes: Variables anulables (Null Safety) en Kotlin
// - Archivo organizado en funciones de demostración
// - Solo existe un main(), que ejecuta cada ejemplo
// - Las líneas que provocarían error de compilación están comentadas con explicación

fun main() {
    println("Demostraciones de Null Safety en Kotlin")
    println()

    demoNullLiteral()
    separator()

    demoNullableStringAssignment()
    separator()

    demoNullableIntAssignment()
    separator()

    demoNonNullableAccess()
    separator()

    demoNullableDirectAccessNotAllowed()
    separator()

    demoSafeCallWithValue()
    separator()

    demoSafeCallWithNull()
    separator()

    demoNonNullAssertionOk()
    separator()

    demoNonNullAssertionFailsSafely()
    separator()

    demoIfNotNullCheck()
    separator()

    demoIfElseNullCheck()
    separator()

    demoIfExpressionResult()
    separator()

    demoSafeCallResultNullable()
    separator()

    demoElvisOperatorDefault()
}

fun separator() = println("----")

// 1) Null sin tipo explícito (tipo inferido: Nothing?)
fun demoNullLiteral() {
    val favoriteActor = null // Tipo inferido: Nothing?
    println("1) Valor nulo con tipo inferido: $favoriteActor")
}

// 2) Variable String anulable: asignación y posterior null
fun demoNullableStringAssignment() {
    var favoriteActor: String? = "Sandra Oh"
    println("2) Inicial (String?): $favoriteActor")

    favoriteActor = null
    println("2) Después de asignar null: $favoriteActor")
}

// 3) Variable Int anulable: asignación y posterior null
fun demoNullableIntAssignment() {
    var number: Int? = 10
    println("3) Inicial (Int?): $number")

    number = null
    println("3) Después de asignar null: $number")
}

// 4) Acceso a propiedad en tipo NO anulable
fun demoNonNullableAccess() {
    var favoriteActor: String = "Sandra Oh"
    println("4) Longitud (no anulable): ${'$'}{favoriteActor.length}")
}

// 5) Acceso directo a propiedad en tipo anulable NO está permitido
fun demoNullableDirectAccessNotAllowed() {
    var favoriteActor: String? = "Sandra Oh"
    // println(favoriteActor.length) // Error de compilación: 'length' no accesible en tipo String?
    println("5) Acceso directo a length en String? produce error de compilación (ver comentario). Usa ?. o !!.")
}

// 6) Operador de llamada segura (?.) con valor no nulo
fun demoSafeCallWithValue() {
    var favoriteActor: String? = "Sandra Oh"
    println("6) Safe call ?. (valor no nulo): ${'$'}{favoriteActor?.length}")
}

// 7) Operador de llamada segura (?.) con null
fun demoSafeCallWithNull() {
    var favoriteActor: String? = null
    println("7) Safe call ?. (null): ${'$'}{favoriteActor?.length}")
}

// 8) Operador de aserción no nula (!!) con valor no nulo (OK)
fun demoNonNullAssertionOk() {
    var favoriteActor: String? = "Sandra Oh"
    println("8) Non-null assertion !! (OK): ${'$'}{favoriteActor!!.length}")
}

// 9) Operador de aserción no nula (!!) con null (lanza excepción) — manejado con try/catch
fun demoNonNullAssertionFailsSafely() {
    var favoriteActor: String? = null
    try {
        // Esta línea lanzaría KotlinNullPointerException si no se captura.
        println(favoriteActor!!.length)
    } catch (e: KotlinNullPointerException) {
        println("9) Non-null assertion !! con null lanza KotlinNullPointerException (capturada)")
    }
}

// 10) Comprobación explícita de null con if
fun demoIfNotNullCheck() {
    var favoriteActor: String? = "Sandra Oh"
    if (favoriteActor != null) {
        println("10) length tras comprobación != null: ${'$'}{favoriteActor.length}")
    }
}

// 11) If/else cuando el valor puede ser null
fun demoIfElseNullCheck() {
    var favoriteActor: String? = null
    if (favoriteActor != null) {
        println("11) length: ${'$'}{favoriteActor.length}")
    } else {
        println("11) No introdujiste un nombre.")
    }
}

// 12) If como expresión para obtener un valor no nulo
fun demoIfExpressionResult() {
    var favoriteActor: String? = "Sandra Oh"
    val lengthOfName: Int = if (favoriteActor != null) {
        favoriteActor.length
    } else {
        0
    }
    println("12) length usando if como expresión: ${'$'}lengthOfName")
}

// 13) Resultado de safe call (posiblemente nulo)
fun demoSafeCallResultNullable() {
    var favoriteActor: String? = "Sandra Oh"
    val lengthOfName: Int? = favoriteActor?.length
    println("13) length con ?. (Int? puede ser null): ${'$'}lengthOfName")
}

// 14) Operador Elvis (?:) para valor por defecto cuando es null
fun demoElvisOperatorDefault() {
    var favoriteActor: String? = "Sandra Oh"
    val lengthOfName: Int = favoriteActor?.length ?: 0
    println("14) length con Elvis (?:) y valor por defecto: ${'$'}lengthOfName")
}
