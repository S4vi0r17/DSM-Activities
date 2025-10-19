# Guía detallada — Reply (Puntos 3–7)

Esta guía describe en detalle los puntos 3–7 del codelab: explicación del código de partida, cómo cambiar de pantalla sin NavHostController, cómo manejar el botón Atrás, ejecutar la app en pantallas grandes, implementar Window Size Class y crear navegación adaptable. Incluye rutas de archivos, fragmentos clave y pasos concretos para aplicar los cambios en el proyecto.

---

## Índice

- 1. Resumen rápido
- 2. Estructura de directorios relevante
- 3. Explicación del código de partida
  - 3.1. Inicialización de datos
  - 3.2. ReplyApp como punto de entrada
  - 3.3. Otros composables importantes
- 4. Navegación sin NavHostController
  - 4.1. Cambiar pantallas con condicionales
  - 4.2. BackHandler para el botón Atrás
- 5. Test en pantallas grandes (AVD resizable)
- 6. Window Size Class (API) — cómo usarla
  - 6.1. Dependencia y setup
  - 6.2. Cambios en `MainActivity.kt`
  - 6.3. Cambios en `ReplyApp.kt`
- 7. Implementar navegación adaptable
  - 7.1. Crear `ReplyNavigationType`
  - 7.2. Determinar `navigationType` en `ReplyApp`
  - 7.3. Ajustes en `ReplyHomeScreen.kt`
  - 7.4. Ajustes en `ReplyAppContent` / `ReplyHomeContent.kt`
- 8. Archivos a editar/crear (resumen con rutas)
- 9. Verificación y pruebas
- 10. Notas y recomendaciones

---

## 1. Resumen rápido

La app Reply separa UI y datos en paquetes `ui` y `data`. El flujo de inicio de la UI se construye en `ReplyViewModel` que genera un `ReplyUiState`. `ReplyApp` es el composable de nivel app que obtiene `viewModel` y `replyUiState`, y pasa lambdas a `ReplyHomeScreen`.

Se puede implementar "navegación" entre lista y detalle sin NavHost utilizando campos de estado en `ReplyUiState` (por ejemplo `isShowingHomepage`) y condicionales en los composables. Al no usar NavHost es necesario manejar manualmente el botón Atrás con `BackHandler`.

Para adaptar la UI a distintos tamaños de pantalla se usa `material3-window-size-class`. Según `WindowWidthSizeClass` se decide mostrar Bottom Navigation (Compact), Navigation Rail (Medium) o Permanent Navigation Drawer (Expanded).

---

## 2. Estructura de directorios relevante

Busca el módulo `01-reply` dentro de `UNIT4/01-reply/app/src/main/java/<paquete>` (o la ruta análoga). Los archivos relevantes suelen encontrarse en:

- `.../ui/ReplyApp.kt`
- `.../ui/ReplyHomeScreen.kt`
- `.../ui/ReplyHomeContent.kt` (o `ReplyAppContent.kt`)
- `.../ui/ReplyDetailsScreen.kt`
- `.../ui/ReplyViewModel.kt`
- `.../ui/utils/` (crear `WindowStateUtils.kt` aquí)
- `.../data/LocalEmailsDataProvider.kt`
- `.../data/models/Email.kt`, `MailboxType.kt`
- `.../MainActivity.kt` (en el módulo app)

> Nota: Ajusta las rutas exactas según el paquete Java/Kotlin del proyecto; aquí se usan rutas relativas al módulo `app`.

---

## 3. Explicación del código de partida

### 3.1 Inicialización de datos (ReplyViewModel)

Archivo: `ReplyViewModel.kt` (paquete `ui`)

Descripción:
- En el bloque `init { initializeUIState() }` se carga el estado inicial.
- `initializeUIState()` agrupa los correos (emails) por `MailboxType` usando `LocalEmailsDataProvider.allEmails.groupBy { it.mailbox }`.
- Posteriormente se asigna `_uiState.value = ReplyUiState(mailboxes = mailboxes, currentSelectedEmail = ...)`.

Consecuencias:
- Si quieres cambiar los datos iniciales modifica `LocalEmailsDataProvider` o la lógica de `initializeUIState()`.
- `ReplyUiState` es la fuente de verdad para la UI.

### 3.2 ReplyApp como punto de entrada

Archivo: `ReplyApp.kt` (paquete `ui`)

Descripción:
- `ReplyApp()` obtiene el `ReplyViewModel` con `viewModel()` y observa `uiState` con `collectAsState()`.
- Llama a `ReplyHomeScreen(replyUiState = replyUiState, onTabPressed = { ... }, onEmailCardPressed = { ... }, onDetailScreenBackPressed = { ... })`.

Implicación:
- `ReplyApp` coordina estado <-> viewModel y delega eventos a éste.

### 3.3 Otros composables

- `ReplyHomeScreen.kt`: organiza la pantalla (si muestra lista o detalle), maneja navegación local.
- `ReplyHomeContent.kt` (o `ReplyAppContent.kt`): contenido principal de la app (lista, barra inferior, etc.).
- `ReplyDetailsScreen.kt`: pantalla de detalle.

---

## 4. Navegación sin `NavHostController`

### 4.1 Cambiar pantallas con condicionales

En `ReplyHomeScreen.kt`:
- Usa `if (replyUiState.isShowingHomepage) { ReplyAppContent(...) } else { ReplyDetailsScreen(...) }`.
- `replyUiState.isShowingHomepage` es parte del estado observado; cuando cambia, Compose recompone y evalúa la condición.

Tipo de firma (antes):
```kotlin
@Composable
fun ReplyHomeScreen(
    replyUiState: ReplyUiState,
    onTabPressed: (MailboxType) -> Unit,
    onEmailCardPressed: (Int) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) { ... }
```

Asegúrate de que `onEmailCardPressed` reciba el tipo correcto (Email o Int) según el código del proyecto.

### 4.2 Manejar el botón Atrás con BackHandler

En `ReplyDetailsScreen.kt`:
- Importa: `import androidx.activity.compose.BackHandler`
- Dentro del composable, añade al inicio:
```kotlin
BackHandler {
    onBackPressed()
}
```

Esto ejecuta la lambda `onBackPressed` (proporcionada por `ReplyApp`) cuando el usuario presiona Atrás.

---

## 5. Ejecutar la app en pantallas grandes (AVD resizable)

Pasos (Android Studio):
1. Tools > Device Manager.
2. Click + > Create Virtual Device.
3. Elige categoría Phone y el dispositivo "Resizable (Experimental)".
4. Selecciona API level 34 o superior.
5. Finaliza y lanza el AVD.
6. Ejecuta la app, desde el AVD cambia a Tablet o plegable y prueba orientación horizontal/vertical.

Consejo: Observa no sólo el aspecto, sino la ergonomía (botones importantes no deben quedar en zonas difíciles de alcanzar).

---

## 6. Window Size Class — usar la API para adaptabilidad

### 6.1 Dependencia y setup

En el `build.gradle.kts` del módulo app añade:
```kotlin
implementation("androidx.compose.material3:material3-window-size-class")
```
Después sincroniza Gradle.

### 6.2 Cambios en `MainActivity.kt`

1. Importa:
```kotlin
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
```
2. Dentro de `setContent {}` calcula la clase de ventana:
```kotlin
val windowSize = calculateWindowSizeClass(this)
ReplyApp(windowSize = windowSize.widthSizeClass)
```
3. Añade Opt-in si la API lo pide, por ejemplo:
```kotlin
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
override fun onCreate(...) { ... }
```
4. Actualiza la preview para pasar `WindowWidthSizeClass.Compact` a `ReplyApp`.

### 6.3 Cambios en `ReplyApp.kt`

Cambia la firma:
```kotlin
@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) { ... }
```
Usa un `when(windowSize) { ... }` para ajustar comportamientos o elegir `navigationType`.

---

## 7. Implementar navegación adaptable

### 7.1 Crear `ReplyNavigationType`

Archivo nuevo: `ui/utils/WindowStateUtils.kt`
```kotlin
package <tu.paquete>.ui.utils

enum class ReplyNavigationType {
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}
```

### 7.2 Determinar `navigationType` en `ReplyApp`

En `ReplyApp.kt`:
```kotlin
import <tu.paquete>.ui.utils.ReplyNavigationType

val navigationType = when (windowSize) {
    WindowWidthSizeClass.Compact -> ReplyNavigationType.BOTTOM_NAVIGATION
    WindowWidthSizeClass.Medium -> ReplyNavigationType.NAVIGATION_RAIL
    WindowWidthSizeClass.Expanded -> ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
    else -> ReplyNavigationType.BOTTOM_NAVIGATION
}

ReplyHomeScreen(
    navigationType = navigationType,
    replyUiState = replyUiState,
    ...
)
```

### 7.3 Ajustes en `ReplyHomeScreen.kt`

- Añadir parámetro `navigationType: ReplyNavigationType` a la firma.
- Implementar la rama que muestra `PermanentNavigationDrawer` cuando `navigationType == PERMANENT_NAVIGATION_DRAWER && replyUiState.isShowingHomepage`.
- Dentro del drawer usar `PermanentDrawerSheet` con `NavigationDrawerContent(...)` y, en el content lambda del drawer, renderizar `ReplyAppContent(...)`.
- Mantener la rama `else` que muestra `ReplyAppContent` o `ReplyDetailsScreen` según `isShowingHomepage`.

Ejemplo de la rama del drawer:
```kotlin
if (navigationType == ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
    && replyUiState.isShowingHomepage) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {
                NavigationDrawerContent(
                    selectedDestination = replyUiState.currentMailbox,
                    onTabPressed = onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.wrapContentWidth()...
                )
            }
        }
    ) {
        ReplyAppContent(...) // como antes
    }
} else {
    // rama anterior: ReplyAppContent o ReplyDetailsScreen
}
```

### 7.4 Ajustes en `ReplyAppContent` / `ReplyHomeContent.kt`

- Cambia la firma para aceptar `navigationType`.
- Usa un `Row` contenedor principal.
- En la izquierda, `AnimatedVisibility(visible = navigationType == NAVIGATION_RAIL) { ReplyNavigationRail(...) }`.
- En la derecha, `Column` con `ReplyListOnlyContent` y `AnimatedVisibility(visible = navigationType == BOTTOM_NAVIGATION) { ReplyBottomNavigationBar(...) }`.

Ejemplo de estructura:
```kotlin
Row(modifier = modifier) {
    AnimatedVisibility(visible = navigationType == ReplyNavigationType.NAVIGATION_RAIL) {
        ReplyNavigationRail(...)
    }

    Column(modifier = Modifier.fillMaxSize().background(...)) {
        ReplyListOnlyContent(...)
        AnimatedVisibility(visible = navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
            ReplyBottomNavigationBar(...)
        }
    }
}
```

---

## 8. Archivos a editar/crear (resumen con rutas sugeridas)

- Crear:
  - `UNIT4/01-reply/app/src/main/java/<paquete>/ui/utils/WindowStateUtils.kt` — enum `ReplyNavigationType`.
  - `UNIT4/01-reply/REPLY_DETAILED_GUIDE.md` — (este archivo).

- Editar:
  - `UNIT4/01-reply/app/src/main/java/<paquete>/MainActivity.kt` — calcular `WindowSizeClass`, pasar `windowSize.widthSizeClass` a `ReplyApp`, Opt-in si aplica, actualizar previews.
  - `UNIT4/01-reply/app/src/main/java/<paquete>/ui/ReplyApp.kt` — cambiar firma para recibir `WindowWidthSizeClass`, calcular `navigationType` y pasar a `ReplyHomeScreen`.
  - `UNIT4/01-reply/app/src/main/java/<paquete>/ui/ReplyHomeScreen.kt` — aceptar `navigationType`, agregar PermanentNavigationDrawer branch, else mantener ReplyAppContent/ReplyDetailsScreen.
  - `UNIT4/01-reply/app/src/main/java/<paquete>/ui/ReplyHomeContent.kt` o `ReplyAppContent.kt` — aceptar `navigationType`, envolver contenido en `Row`, agregar `AnimatedVisibility` para rail/bottom nav.
  - `UNIT4/01-reply/app/src/main/java/<paquete>/ui/ReplyDetailsScreen.kt` — añadir `BackHandler { onBackPressed() }`.
  - `UNIT4/01-reply/app/build.gradle.kts` — añadir `implementation("androidx.compose.material3:material3-window-size-class")`.

> Ajusta `<paquete>` por el package root real del proyecto (por ejemplo `com.example.reply`).

---

## 9. Verificación y pruebas

Checks básicos a realizar tras cambios:
1. Compilar el proyecto en Android Studio (Sync + Rebuild). Verificar errores de imports/firmas.
2. Ejecutar en AVD resizable API 34:
   - Compact → Bottom Navigation visible.
   - Medium → Navigation Rail visible (en la izquierda) y Bottom Nav oculto.
   - Expanded → Permanent Navigation Drawer visible y contenido principal a la derecha.
3. Ir a pantalla detalles y pulsar botón Atrás: comprobar que `BackHandler` llama correctamente a la lambda que restablece el estado.
4. Revisar Previews que acepten `WindowWidthSizeClass.Compact` para `ReplyApp`.

Comandos útiles (PowerShell):
```powershell
# Abrir Android Studio en el proyecto (si tienes el path configurado)
Start-Process -FilePath "C:\Program Files\Android\Android Studio\bin\studio64.exe" -ArgumentList '"C:\Users\GBP17\Desktop\DSM-Activities"'
```

---

## 10. Notas y recomendaciones

- Mantén las lambdas y firmas consistentes: `onEmailCardPressed` debe coincidir (si el proyecto usa `Email` como parámetro o `Int` como index, respeta eso).
- Usa `AnimatedVisibility` para transiciones suaves; sin animaciones el cambio de navegación será brusco.
- Si la app crece en complejidad (más pantallas, back stack, deep links), considera migrar a `NavHostController`.
- Documenta en `README.md` del módulo los pasos para probar en AVD resizable y los requisitos (API 34+).

---

Si quieres, aplico los cambios en el árbol de archivos (crear `WindowStateUtils.kt` y editar las firmas) y hago un build rápido para comprobar errores. ¿Quieres que lo haga ahora? 
