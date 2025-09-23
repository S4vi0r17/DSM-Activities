/*
 * APUNTES: VARIABLES EN KOTLIN - UNIDAD 1, PATHWAY 1
 * 
 * Conceptos clave:
 * - val: Variables inmutables (read-only) - no se pueden reasignar después de la inicialización
 * - var: Variables mutables - se pueden reasignar
 * - Tipos de datos: Int, Double, String, Boolean
 * - String templates: interpolación de variables en strings
 * - Inferencia de tipos: Kotlin puede deducir el tipo automáticamente
 */

fun main() {
    // ═══════════════════════════════════════════════════════════════
    // 1. VARIABLES INMUTABLES (val) - TIPO EXPLÍCITO
    // ═══════════════════════════════════════════════════════════════
    
    // val + tipo explícito: declaración más verbosa pero clara
    val count: Int = 2
    println("You have $count unread messages.")
    // Nota: $variable es la forma básica de string interpolation
    
    // ═══════════════════════════════════════════════════════════════
    // 2. VARIABLES INMUTABLES (val) - INFERENCIA DE TIPO
    // ═══════════════════════════════════════════════════════════════
    
    // Kotlin infiere automáticamente que estas son Int
    val unreadCount = 5        // Int inferido
    val readCount = 100        // Int inferido
    
    // String templates con expresiones: ${expresión}
    println("You have ${unreadCount + readCount} total messages in your inbox.")
    // Nota: Usa ${} cuando necesites evaluar expresiones o operaciones
    
    // ═══════════════════════════════════════════════════════════════
    // 3. MÁS EJEMPLOS DE STRING INTERPOLATION
    // ═══════════════════════════════════════════════════════════════
    
    val numberOfPhotos = 100   // Int inferido
    val photosDeleted = 10     // Int inferido
    
    // Múltiples formas de usar string templates
    println("$numberOfPhotos photos")                              // Variable simple
    println("$photosDeleted photos deleted")                       // Variable simple
    println("${numberOfPhotos - photosDeleted} photos left")       // Expresión matemática
    
    println() // Línea en blanco para separar secciones
    
    // ═══════════════════════════════════════════════════════════════
    // 4. VARIABLES MUTABLES (var) - PUEDEN CAMBIAR
    // ═══════════════════════════════════════════════════════════════
    
    // var permite reasignar el valor de la variable
    var cartTotal = 0          // Int inferido, inicializado en 0
    println("Total: $cartTotal")
    
    // Reasignación: esto es posible porque usamos 'var'
    cartTotal = 20             // Cambiamos el valor de 0 a 20
    println("Total: $cartTotal")
    
    println() // Separador visual
    
    // ═══════════════════════════════════════════════════════════════
    // 5. NÚMEROS DECIMALES (Double)
    // ═══════════════════════════════════════════════════════════════
    
    // Tipo Double para números con decimales
    val trip1: Double = 3.20   // Tipo explícito
    val trip2: Double = 4.10   // Tipo explícito  
    val trip3: Double = 1.72   // Tipo explícito
    
    // Operaciones matemáticas con Double
    val totalTripLength: Double = trip1 + trip2 + trip3
    println("$totalTripLength miles left to destination")
    
    println() // Separador visual
    
    // ═══════════════════════════════════════════════════════════════
    // 6. STRINGS Y CONCATENACIÓN
    // ═══════════════════════════════════════════════════════════════
    
    // Strings con inferencia de tipo
    val nextMeeting = "Next meeting: "  // String inferido
    val date = "January 1"              // String inferido
    
    // Concatenación tradicional con operador +
    val reminder = nextMeeting + date + " at work"
    println(reminder)
    
    // ═══════════════════════════════════════════════════════════════
    // 7. CARACTERES ESPECIALES EN STRINGS
    // ═══════════════════════════════════════════════════════════════
    
    // Escape de comillas dobles con \"
    println("Say \"hello\"")
    // Nota: El backslash (\) se usa para "escapar" caracteres especiales
    
    println() // Separador visual
    
    // ═══════════════════════════════════════════════════════════════
    // 8. TIPO BOOLEAN
    // ═══════════════════════════════════════════════════════════════
    
    // Boolean: solo puede ser true o false
    val notificationsEnabled: Boolean = false  // Tipo explícito
    
    // Concatenación tradicional vs string templates
    println("Are notifications enabled? " + notificationsEnabled)
    // Alternativa con string template: println("Are notifications enabled? $notificationsEnabled")
}

/*
 * RESUMEN DE CONCEPTOS IMPORTANTES:
 * 
 * 1. INMUTABILIDAD:
 *    - Prefiere 'val' sobre 'var' cuando sea posible
 *    - 'val' hace el código más seguro y predecible
 * 
 * 2. INFERENCIA DE TIPOS:
 *    - Kotlin es muy bueno detectando tipos automáticamente
 *    - Solo especifica el tipo cuando sea necesario para claridad
 * 
 * 3. STRING TEMPLATES:
 *    - $variable para variables simples
 *    - ${expresión} para operaciones o expresiones complejas
 *    - Más legible que la concatenación con +
 * 
 * 4. TIPOS BÁSICOS:
 *    - Int: números enteros
 *    - Double: números decimales
 *    - String: texto
 *    - Boolean: verdadero/falso
 */
