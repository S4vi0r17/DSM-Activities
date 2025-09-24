/*
=====================================
    FUNCIONES EN KOTLIN - APUNTES
=====================================
*/

// 1. SINTAXIS BÁSICA
fun saludar() {
    println("¡Hola mundo!")
}

fun sumar(a: Int, b: Int): Int {
    return a + b
}

// 2. PARÁMETROS POR DEFECTO
fun crearUsuario(nombre: String, edad: Int = 0, activo: Boolean = true) {
    println("Usuario: $nombre, $edad años, activo: $activo")
}

// 3. FUNCIONES DE EXPRESIÓN ÚNICA
fun duplicar(numero: Int): Int = numero * 2
fun esPositivo(numero: Int): Boolean = numero > 0

// 4. FUNCIONES DE ORDEN SUPERIOR
fun operacion(a: Int, b: Int, operador: (Int, Int) -> Int): Int {
    return operador(a, b)
}

// 5. FUNCIONES LAMBDA
fun ejemplosLambda() {
    val numeros = listOf(1, 2, 3, 4, 5)
    val pares = numeros.filter { it % 2 == 0 }
    val cuadrados = numeros.map { it * it }
    println("Pares: $pares, Cuadrados: $cuadrados")
}

// 6. FUNCIONES DE EXTENSIÓN
fun String.esPalindromo(): Boolean {
    val limpio = this.lowercase()
    return limpio == limpio.reversed()
}

fun Int.esParOImpar(): String = if (this % 2 == 0) "par" else "impar"

// 7. SCOPE FUNCTIONS
data class Usuario(var nombre: String, var edad: Int)

fun ejemplosScopeFunctions() {
    val usuario = Usuario("Juan", 25)
    
    // apply: configurar objeto
    usuario.apply {
        nombre = "Juan Carlos"
        edad = 26
    }
    
    // let: transformar resultado
    val mensaje = usuario.let { "Usuario: ${it.nombre}" }
    println(mensaje)
}

// 8. EJEMPLO PRÁCTICO
class Calculadora {
    fun sumar(a: Double, b: Double): Double = a + b
    fun dividir(a: Double, b: Double): Double = 
        if (b != 0.0) a / b else throw IllegalArgumentException("División por cero")
}

// FUNCIÓN MAIN - EJEMPLOS DE USO
fun main() {
    println("=== FUNCIONES EN KOTLIN ===")
    
    // Función básica
    saludar()
    println("Suma: ${sumar(5, 3)}")
    
    // Parámetros por defecto
    crearUsuario("Ana")
    crearUsuario("Bob", 30, false)
    
    // Expresión única
    println("Doble de 8: ${duplicar(8)}")
    println("¿Es positivo 5?: ${esPositivo(5)}")
    
    // Orden superior
    println("Suma: ${operacion(10, 5) { a, b -> a + b }}")
    println("Resta: ${operacion(10, 5) { a, b -> a - b }}")
    
    // Lambda
    ejemplosLambda()
    
    // Extensiones
    println("¿'ana' es palíndromo?: ${"ana".esPalindromo()}")
    println("7 es: ${7.esParOImpar()}")
    
    // Scope functions
    ejemplosScopeFunctions()
    
    // Calculadora
    val calc = Calculadora()
    println("15 ÷ 3 = ${calc.dividir(15.0, 3.0)}")
}
