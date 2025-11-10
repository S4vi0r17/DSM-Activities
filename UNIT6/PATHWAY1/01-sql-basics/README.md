# SQL Basics - Codelab de Bases de Datos

> **Codelab oficial:** [Cómo usar SQL para leer y escribir en una base de datos](https://developer.android.com/codelabs/basic-android-kotlin-compose-sql-basics)

## 🎯 Objetivos

Aprender los fundamentos de SQL y SQLite para Android:
- ✅ Leer datos con `SELECT`
- ✅ Filtrar con `WHERE` y `LIKE`
- ✅ Agrupar y ordenar con `GROUP BY` / `ORDER BY`
- ✅ Modificar datos con `INSERT`, `UPDATE`, `DELETE`
- ✅ Usar funciones de agregación (`COUNT`, `SUM`, `AVG`, `MIN`, `MAX`)

---

## 🚀 Inicio Rápido

### 1. Ejecutar la App

```bash
1. Abre el proyecto en Android Studio
2. Ejecuta la app (▶️)
3. Espera a que se instale
```

### 2. Abrir Database Inspector

```
View → Tool Windows → App Inspection → Database Inspector
```

### 3. Tu Primera Consulta

```sql
SELECT * FROM email LIMIT 10;
```

---

## 📊 Esquema de la Base de Datos

### Tabla: `email`

| Columna | Tipo | Descripción |
|---------|------|-------------|
| `id` | INTEGER | Clave primaria (autoincremental) |
| `subject` | TEXT | Asunto del correo |
| `sender` | VARCHAR | Dirección del remitente |
| `folder` | TEXT | Carpeta (inbox, spam, important, trash, sent) |
| `starred` | BOOLEAN | ¿Está destacado? |
| `read` | BOOLEAN | ¿Ha sido leído? |
| `received` | INTEGER | Timestamp Unix (milisegundos) |

**Datos:** 43 correos con citas de obras de Shakespeare.

---

## 📚 Guía de SQL

### 1. SELECT - Leer Datos

```sql
-- Todas las columnas
SELECT * FROM email;

-- Columnas específicas
SELECT subject, sender FROM email;

-- Valores únicos
SELECT DISTINCT sender FROM email;
```

### 2. Funciones de Agregación

```sql
-- Contar correos
SELECT COUNT(*) FROM email;

-- Correo más reciente
SELECT MAX(received) FROM email;

-- Remitentes únicos
SELECT COUNT(DISTINCT sender) FROM email;
```

### 3. WHERE - Filtrar

```sql
-- Correos de inbox
SELECT * FROM email WHERE folder = 'inbox';

-- Correos no leídos
SELECT * FROM email WHERE read = false;

-- Múltiples condiciones
SELECT * FROM email 
WHERE folder = 'inbox' AND read = false;

-- Buscar texto
SELECT * FROM email WHERE subject LIKE '%love%';
```

### 4. ORDER BY - Ordenar

```sql
-- Más recientes primero
SELECT * FROM email ORDER BY received DESC;

-- Más antiguos primero
SELECT * FROM email ORDER BY received ASC;
```

### 5. GROUP BY - Agrupar

```sql
-- Contar por carpeta
SELECT folder, COUNT(*) FROM email
GROUP BY folder;

-- Solo carpetas con más de 5 correos
SELECT folder, COUNT(*) as total FROM email
GROUP BY folder
HAVING total > 5;
```

### 6. LIMIT y OFFSET - Paginar

```sql
-- Primeros 10
SELECT * FROM email LIMIT 10;

-- Siguientes 10 (página 2)
SELECT * FROM email LIMIT 10 OFFSET 10;
```

### 7. INSERT - Insertar

```sql
INSERT INTO email VALUES (
    NULL,                          -- id autogenerado
    'Nuevo correo',               -- subject
    'test@example.com',           -- sender
    'inbox',                      -- folder
    false,                        -- starred
    false,                        -- read
    CURRENT_TIMESTAMP             -- received
);
```

### 8. UPDATE - Actualizar

```sql
-- Marcar como leído
UPDATE email SET read = true WHERE id = 1;

-- Múltiples columnas
UPDATE email 
SET read = true, starred = true 
WHERE sender = 'importante@example.com';
```

### 9. DELETE - Eliminar

```sql
-- Eliminar un correo
DELETE FROM email WHERE id = 44;

-- Vaciar papelera
DELETE FROM email WHERE folder = 'trash';
```

⚠️ **IMPORTANTE:** Siempre usa `WHERE` con `UPDATE` y `DELETE` o afectarás todas las filas.

---

## 🎓 Ejercicios Prácticos

### Nivel Básico (1-5)

1. Selecciona todos los asuntos
2. Cuenta cuántos correos hay en total
3. Obtén carpetas únicas
4. Selecciona correos destacados
5. Cuenta correos no leídos

### Nivel Intermedio (6-10)

6. Encuentra correos de 'hamlet@example.com'
7. Busca asuntos con "love"
8. Cuenta correos por carpeta
9. Últimos 5 correos de inbox
10. Correos que NO están en trash

### Nivel Avanzado (11-15)

11. Remitentes con más de 3 correos
12. Correos destacados no leídos, ordenados
13. Porcentaje de correos leídos en inbox
14. Remitentes que terminan en 'o@'
15. Reporte completo por carpeta

Ver soluciones en `solutions.sql`.

---

## 📖 Referencia Rápida

### Operadores de Comparación

| SQL | Kotlin | Descripción |
|-----|--------|-------------|
| `=` | `==` | Igual |
| `!=` o `<>` | `!=` | Diferente |
| `<`, `>`, `<=`, `>=` | Igual | Comparaciones |

### Operadores Lógicos

```sql
AND  -- Ambas condiciones verdaderas
OR   -- Al menos una verdadera
NOT  -- Niega la condición
```

### Wildcards en LIKE

```sql
'%text%'   -- Contiene "text"
'text%'    -- Empieza con "text"
'%text'    -- Termina con "text"
'_ext'     -- Un carácter + "ext"
```

### Funciones Comunes

```sql
COUNT(*)              -- Cuenta filas
COUNT(DISTINCT col)   -- Cuenta valores únicos
SUM(columna)          -- Suma valores
AVG(columna)          -- Promedio
MIN(columna)          -- Mínimo
MAX(columna)          -- Máximo
```

### Orden de Cláusulas

```sql
SELECT columnas
FROM tabla
WHERE condición
GROUP BY columna
HAVING condición_grupos
ORDER BY columna [ASC|DESC]
LIMIT número OFFSET número;
```

---

## 💡 Casos de Uso Reales

### Badge de correos no leídos

```sql
SELECT COUNT(*) FROM email 
WHERE folder = 'inbox' AND read = false;
```

```kotlin
fun getUnreadCount(): Int {
    val query = "SELECT COUNT(*) FROM email WHERE folder = 'inbox' AND read = 0"
    return database.rawQuery(query).getInt(0)
}
```

### Buscar correos

```kotlin
fun searchEmails(query: String): List<Email> {
    val pattern = "%$query%"
    val sql = """
        SELECT * FROM email
        WHERE (subject LIKE ? OR sender LIKE ?)
        AND folder != 'trash'
        ORDER BY received DESC
        LIMIT 50
    """
    return database.rawQuery(sql, arrayOf(pattern, pattern))
}
```

### Marcar como leído

```kotlin
fun markAsRead(emailId: Int) {
    database.execSQL("UPDATE email SET read = 1 WHERE id = ?", arrayOf(emailId))
}
```

### Paginación

```kotlin
fun loadEmailPage(page: Int, pageSize: Int = 20): List<Email> {
    val offset = (page - 1) * pageSize
    val sql = """
        SELECT * FROM email
        WHERE folder = 'inbox'
        ORDER BY received DESC
        LIMIT ? OFFSET ?
    """
    return database.rawQuery(sql, arrayOf(pageSize, offset))
}
```

---

## ⚠️ Mejores Prácticas

### ✅ Hacer

```sql
-- Especificar columnas
SELECT id, subject FROM email;

-- Siempre usar WHERE con UPDATE/DELETE
UPDATE email SET read = true WHERE id = 5;

-- Usar LIMIT para pruebas
SELECT * FROM email LIMIT 10;
```

```kotlin
// Usar prepared statements (seguro)
val query = "SELECT * FROM email WHERE sender = ?"
database.rawQuery(query, arrayOf(sender))
```

### ❌ Evitar

```sql
-- SELECT * en producción
SELECT * FROM email;  -- Ineficiente

-- UPDATE/DELETE sin WHERE
UPDATE email SET read = true;  -- ¡Actualiza TODOS!

-- Comparar NULL con =
WHERE columna = NULL  -- ❌ Siempre false
WHERE columna IS NULL -- ✅ Correcto
```

```kotlin
// SQL injection vulnerable
val query = "SELECT * FROM email WHERE sender = '$sender'"  // ❌ PELIGROSO
```

---

## 🎯 Checklist de Completitud

Marca tu progreso:

**Fundamentos**
- [ ] Ejecuté el proyecto exitosamente
- [ ] Abrí Database Inspector
- [ ] Ejecuté mi primera consulta SELECT
- [ ] Entiendo qué es una base de datos relacional

**Lectura de Datos**
- [ ] Puedo usar SELECT con columnas específicas
- [ ] Sé usar WHERE para filtrar
- [ ] Domino LIKE para búsquedas de texto
- [ ] Puedo ordenar con ORDER BY

**Agregación**
- [ ] Uso funciones COUNT, MAX, MIN, AVG, SUM
- [ ] Sé agrupar con GROUP BY
- [ ] Entiendo la diferencia entre WHERE y HAVING

**Modificación**
- [ ] Puedo insertar datos con INSERT
- [ ] Sé actualizar con UPDATE
- [ ] Puedo eliminar con DELETE
- [ ] Siempre uso WHERE con UPDATE/DELETE

**Aplicación**
- [ ] Completé al menos 10 ejercicios
- [ ] Puedo implementar consultas en Kotlin
- [ ] Entiendo cuándo usar SQL vs código

---

## 📁 Archivos del Proyecto

```
01-sql-basics/
├── app/                    # App Android con SQLite
├── queries.sql             # Consultas del codelab
├── exercises.sql           # 25 ejercicios de práctica
├── solutions.sql           # Soluciones a ejercicios
└── README.md              # Esta guía
```

---

## 🔗 Recursos

- [Documentación SQLite](https://www.sqlite.org/docs.html)
- [Android SQLite Guide](https://developer.android.com/training/data-storage/sqlite)
- [SQLBolt - Tutorial interactivo](https://sqlbolt.com/)
- [W3Schools SQL](https://www.w3schools.com/sql/)

---

## 📈 Próximos Pasos

Después de completar este codelab:

1. ✅ **Room Persistence Library** - Abstracción sobre SQLite
2. ✅ **DataStore** - Para preferencias simples
3. ✅ Trabajar con APIs y caché local

---

## 💪 ¡Empieza Ahora!

1. Ejecuta la app
2. Abre Database Inspector
3. Practica con `queries.sql`
4. Intenta los `exercises.sql`
5. Compara con `solutions.sql`

**¡Buena suerte! 🚀**
