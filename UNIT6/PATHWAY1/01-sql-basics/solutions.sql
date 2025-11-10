-- ========================================
-- SOLUCIONES A LOS EJERCICIOS DE PRÁCTICA SQL
-- ========================================

-- ========================================
-- SOLUCIONES NIVEL BÁSICO
-- ========================================

-- 1. Selecciona todos los asuntos de correos electrónicos
SELECT subject FROM email;

-- 2. Cuenta cuántos correos hay en total en la base de datos
SELECT COUNT(*) FROM email;

-- 3. Obtén una lista de todas las carpetas únicas (sin duplicados)
SELECT DISTINCT folder FROM email;

-- 4. Selecciona todos los correos que están destacados (starred = true)
SELECT * FROM email
WHERE starred = true;

-- 5. Cuenta cuántos correos no han sido leídos
SELECT COUNT(*) FROM email
WHERE read = false;


-- ========================================
-- SOLUCIONES NIVEL INTERMEDIO
-- ========================================

-- 6. Encuentra todos los correos de 'hamlet@example.com'
SELECT * FROM email
WHERE sender = 'hamlet@example.com';

-- 7. Selecciona los correos cuyo asunto contiene la palabra "love"
SELECT * FROM email
WHERE subject LIKE '%love%';

-- 8. Cuenta cuántos correos hay en cada carpeta
SELECT folder, COUNT(*) as total FROM email
GROUP BY folder;

-- 9. Encuentra los 5 correos más recientes de la bandeja de entrada
SELECT * FROM email
WHERE folder = 'inbox'
ORDER BY received DESC
LIMIT 5;

-- 10. Obtén todos los correos que NO están en la papelera (trash)
SELECT * FROM email
WHERE folder != 'trash';
-- O también:
-- WHERE NOT folder = 'trash';


-- ========================================
-- SOLUCIONES NIVEL AVANZADO
-- ========================================

-- 11. Encuentra los remitentes que han enviado más de 3 correos
SELECT sender, COUNT(*) as total FROM email
GROUP BY sender
HAVING total > 3
ORDER BY total DESC;

-- 12. Selecciona correos destacados que aún no han sido leídos, 
--     ordenados por fecha de recepción (más recientes primero)
SELECT * FROM email
WHERE starred = true AND read = false
ORDER BY received DESC;

-- 13. Calcula el porcentaje de correos leídos en la bandeja de entrada
SELECT 
    ROUND(
        CAST(SUM(CASE WHEN read = true THEN 1 ELSE 0 END) AS REAL) / COUNT(*) * 100, 
        2
    ) as porcentaje_leidos
FROM email
WHERE folder = 'inbox';

-- 14. Encuentra todos los correos de remitentes cuyo nombre termina con 'o'
--     antes del símbolo @
SELECT * FROM email
WHERE sender LIKE '%o@%';

-- 15. Crea un reporte que muestre por cada carpeta:
SELECT 
    folder as carpeta,
    COUNT(*) as total,
    SUM(CASE WHEN read = true THEN 1 ELSE 0 END) as leidos,
    SUM(CASE WHEN read = false THEN 1 ELSE 0 END) as no_leidos,
    SUM(CASE WHEN starred = true THEN 1 ELSE 0 END) as destacados
FROM email
GROUP BY folder
ORDER BY total DESC;


-- ========================================
-- SOLUCIONES DE MODIFICACIÓN DE DATOS
-- ========================================

-- 16. Inserta un nuevo correo de 'test@example.com'
INSERT INTO email
VALUES (
    NULL,
    'Testing SQL queries',
    'test@example.com',
    'inbox',
    false,
    false,
    CURRENT_TIMESTAMP
);

-- 17. Marca como leídos todos los correos de la carpeta 'spam'
UPDATE email
SET read = true
WHERE folder = 'spam';

-- 18. Mueve todos los correos destacados a la carpeta 'important'
UPDATE email
SET folder = 'important'
WHERE starred = true;

-- 19. Elimina todos los correos de la papelera (trash)
DELETE FROM email
WHERE folder = 'trash';

-- 20. Actualiza el asunto de un correo específico (ejemplo con id = 1)
UPDATE email
SET subject = 'Nuevo asunto actualizado'
WHERE id = 1;


-- ========================================
-- SOLUCIONES DESAFÍOS EXTRA
-- ========================================

-- 21. Encuentra pares de correos del mismo remitente donde uno está
--     leído y otro no leído
SELECT DISTINCT e1.sender
FROM email e1
JOIN email e2 ON e1.sender = e2.sender
WHERE e1.read = true AND e2.read = false;

-- 22. Crea una consulta que simule una función de "archivos"
SELECT * FROM email
WHERE read = true 
  AND starred = false 
  AND folder != 'trash'
ORDER BY received DESC;

-- 23. Encuentra el remitente más "activo" y muestra todos sus correos
SELECT * FROM email
WHERE sender = (
    SELECT sender 
    FROM email 
    GROUP BY sender 
    ORDER BY COUNT(*) DESC 
    LIMIT 1
)
ORDER BY received DESC;

-- 24. Calcula cuántos días han pasado desde que se recibió cada correo
SELECT 
    subject,
    sender,
    CAST((CURRENT_TIMESTAMP - received) / 86400000 AS INTEGER) as dias_transcurridos
FROM email
ORDER BY received DESC;

-- 25. Crea un resumen ejecutivo
SELECT 
    (SELECT COUNT(*) FROM email) as total_correos,
    (SELECT COUNT(DISTINCT sender) FROM email) as remitentes_unicos,
    ROUND(
        CAST((SELECT COUNT(*) FROM email WHERE read = true) AS REAL) / 
        (SELECT COUNT(*) FROM email) * 100, 
        2
    ) as porcentaje_leidos,
    ROUND(
        CAST((SELECT COUNT(*) FROM email WHERE starred = true) AS REAL) / 
        (SELECT COUNT(*) FROM email) * 100, 
        2
    ) as porcentaje_destacados,
    (
        SELECT folder 
        FROM email 
        GROUP BY folder 
        ORDER BY COUNT(*) DESC 
        LIMIT 1
    ) as carpeta_mas_correos;
