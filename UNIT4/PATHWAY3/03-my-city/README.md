# Mi Ciudad - Lima

Una aplicación Android moderna que muestra recomendaciones de actividades y lugares para visitar en Lima, Perú.

## 📱 Características

### Funcionalidades Principales
- **Navegación por Categorías**: Explora lugares organizados en 5 categorías:
  - ☕ Cafeterías
  - 🍽️ Restaurantes
  - 🌳 Parques
  - 🏛️ Museos
  - 🛍️ Centros Comerciales

- **Diseño Adaptable**: La aplicación se adapta automáticamente a diferentes tamaños de pantalla:
  - **Teléfonos** (Compact/Medium): Navegación secuencial con una pantalla a la vez
  - **Tabletas** (Expanded): Vista de lista y detalle simultánea

- **Detalles Completos**: Cada lugar incluye:
  - Nombre y descripción
  - Calificación con estrellas
  - Dirección completa
  - Imágenes representativas

### Arquitectura
La aplicación sigue las mejores prácticas de desarrollo Android:

- **Patrón MVVM**: Separación clara entre la UI y la lógica de negocio
- **StateFlow**: Manejo reactivo del estado con flujo unidireccional de datos
- **Jetpack Compose**: UI declarativa moderna
- **Navigation**: Navegación consistente entre pantallas
- **Material Design 3**: Diseño moderno y accesible

## 🏗️ Estructura del Proyecto

```
app/src/main/java/tech/s4vi0r/mycity/
├── data/
│   └── LocalPlacesDataProvider.kt    # Fuente de datos local
├── model/
│   ├── Place.kt                      # Modelo de datos de lugares
│   └── PlaceCategory.kt              # Enum de categorías
├── ui/
│   ├── MyCityViewModel.kt            # ViewModel con lógica de negocio
│   ├── MyCityScreens.kt              # Composables de las pantallas
│   └── theme/                        # Tema de la aplicación
├── utils/
│   └── WindowStateUtils.kt           # Utilidades para tamaños de pantalla
└── MainActivity.kt                   # Activity principal
```

## 🎨 Diseño

### Paleta de Colores
Inspirada en Lima: el azul del Océano Pacífico, el naranja de los atardeceres y el verde de sus parques:

- **Primary**: Azul Lima (#023047)
- **Secondary**: Naranja Lima (#FB8500)
- **Tertiary**: Verde Lima (#43AA8B)

### Componentes de UI
- **CategoryCard**: Tarjetas grandes con iconos para seleccionar categorías
- **PlaceListItem**: Items de lista con imagen, título, descripción y rating
- **PlaceDetailScreen**: Vista detallada con banner, información completa y dirección

## 🚀 Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación principal
- **Jetpack Compose**: Framework de UI moderno
- **Material 3**: Sistema de diseño
- **ViewModel**: Manejo del estado de la UI
- **StateFlow**: Flujo de datos reactivo
- **Window Size Class**: Diseño adaptable

## 📝 Datos de Ejemplo

La aplicación incluye 15 lugares reales de Lima organizados en 5 categorías:

### Cafeterías (3)
- Tostado Café Bar
- Café Bisetti
- Juan Valdez Café

### Restaurantes (3)
- Central Restaurante
- Maido
- Astrid y Gastón

### Parques (3)
- Parque Kennedy
- Parque del Amor
- Circuito Mágico del Agua

### Museos (3)
- Museo Larco
- Museo de Arte de Lima (MALI)
- Museo Nacional del Perú

### Centros Comerciales (3)
- Jockey Plaza
- Larcomar
- Real Plaza Salaverry

## 🔧 Compilación

### Requisitos
- Android Studio Hedgehog o superior
- Kotlin 1.9+
- Gradle 8.0+
- SDK mínimo: API 24 (Android 7.0)
- SDK objetivo: API 36

### Pasos para ejecutar
1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Sincroniza las dependencias de Gradle
4. Ejecuta la aplicación en un emulador o dispositivo físico

## 📖 Aprendizajes Aplicados

Este proyecto demuestra el dominio de:

1. **Múltiples Pantallas**: Navegación entre pantalla de categorías, lista de lugares y detalles
2. **Diseño Adaptable**: Layouts diferentes para teléfonos y tabletas
3. **Arquitectura MVVM**: Separación de capas UI y datos
4. **Ciclo de Vida**: Manejo correcto del estado durante cambios de configuración
5. **Material Design**: Implementación de componentes siguiendo los lineamientos de diseño

## 🎯 Próximas Mejoras

Posibles mejoras futuras:
- Integración con API de Google Maps para ubicaciones
- Favoritos persistentes con Room Database
- Búsqueda y filtros avanzados
- Compartir lugares en redes sociales
- Modo oscuro personalizado
- Internacionalización (inglés/español)

## 👨‍💻 Autor

Desarrollado como parte del proyecto final de la Unidad 4 del curso de Android Development.

## 📄 Licencia

Este proyecto es con fines educativos.
