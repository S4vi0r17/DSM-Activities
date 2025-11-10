# Script de Verificación y Ayuda para Flight Search App
# Ejecutar con: .\VerificarSetup.ps1

Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "  Flight Search App - Verificación" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""

# Ruta de la base de datos
$dbPath = "C:\Users\GBP17\Desktop\DSM-Activities\UNIT6\PATHWAY2\02-flight-search\app\src\main\assets\database\flight_search.db"

# Verificar si existe la carpeta assets/database
$assetsPath = "C:\Users\GBP17\Desktop\DSM-Activities\UNIT6\PATHWAY2\02-flight-search\app\src\main\assets\database"

Write-Host "1. Verificando estructura de carpetas..." -ForegroundColor Yellow

if (Test-Path $assetsPath) {
    Write-Host "   ✅ Carpeta assets/database existe" -ForegroundColor Green
} else {
    Write-Host "   ❌ Carpeta assets/database NO existe" -ForegroundColor Red
    Write-Host "   Creando carpeta..." -ForegroundColor Yellow
    New-Item -ItemType Directory -Path $assetsPath -Force | Out-Null
    Write-Host "   ✅ Carpeta creada" -ForegroundColor Green
}

Write-Host ""
Write-Host "2. Verificando base de datos..." -ForegroundColor Yellow

if (Test-Path $dbPath) {
    Write-Host "   ✅ Base de datos encontrada!" -ForegroundColor Green
    $fileSize = (Get-Item $dbPath).Length
    $fileSizeKB = [math]::Round($fileSize / 1KB, 2)
    Write-Host "   📊 Tamaño: $fileSizeKB KB" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "   ✨ ¡La app está lista para ejecutarse!" -ForegroundColor Green
    Write-Host ""
    Write-Host "   Próximos pasos:" -ForegroundColor Cyan
    Write-Host "   1. Abre Android Studio" -ForegroundColor White
    Write-Host "   2. Sincroniza el proyecto (File → Sync Project with Gradle Files)" -ForegroundColor White
    Write-Host "   3. Ejecuta la app (Run → Run 'app')" -ForegroundColor White
} else {
    Write-Host "   ❌ Base de datos NO encontrada" -ForegroundColor Red
    Write-Host ""
    Write-Host "   📥 NECESITAS DESCARGAR LA BASE DE DATOS" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "   Opciones para obtenerla:" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "   Opción 1: Descargar desde GitHub (Manual)" -ForegroundColor White
    Write-Host "   -----------------------------------------------" -ForegroundColor Gray
    Write-Host "   1. Ve a: https://github.com/google-developer-training/" -ForegroundColor White
    Write-Host "      basic-android-kotlin-compose-training-sql-basics" -ForegroundColor White
    Write-Host "   2. Cambia a la rama 'project'" -ForegroundColor White
    Write-Host "   3. Descarga el archivo 'flight_search.db'" -ForegroundColor White
    Write-Host "   4. Cópialo a:" -ForegroundColor White
    Write-Host "      $assetsPath" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "   Opción 2: Clonar con Git (Automático)" -ForegroundColor White
    Write-Host "   -----------------------------------------------" -ForegroundColor Gray
    
    # Verificar si Git está instalado
    try {
        $gitVersion = git --version 2>&1
        if ($LASTEXITCODE -eq 0) {
            Write-Host "   ✅ Git está instalado: $gitVersion" -ForegroundColor Green
            Write-Host ""
            Write-Host "   ¿Deseas clonar el repositorio y copiar la base de datos automáticamente?" -ForegroundColor Yellow
            Write-Host "   (S/N): " -NoNewline -ForegroundColor Cyan
            $response = Read-Host
            
            if ($response -eq "S" -or $response -eq "s" -or $response -eq "Y" -or $response -eq "y") {
                Write-Host ""
                Write-Host "   Clonando repositorio..." -ForegroundColor Yellow
                
                $tempDir = "$env:TEMP\flight-search-db-temp"
                
                # Eliminar directorio temporal si existe
                if (Test-Path $tempDir) {
                    Remove-Item -Path $tempDir -Recurse -Force
                }
                
                # Clonar solo la rama project
                git clone --branch project --depth 1 `
                    "https://github.com/google-developer-training/basic-android-kotlin-compose-training-sql-basics.git" `
                    $tempDir
                
                if ($LASTEXITCODE -eq 0) {
                    # Buscar el archivo de base de datos
                    $sourceDb = Get-ChildItem -Path $tempDir -Filter "flight_search.db" -Recurse | Select-Object -First 1
                    
                    if ($sourceDb) {
                        Write-Host "   ✅ Base de datos encontrada en el repositorio" -ForegroundColor Green
                        Copy-Item -Path $sourceDb.FullName -Destination $dbPath -Force
                        Write-Host "   ✅ Base de datos copiada exitosamente!" -ForegroundColor Green
                        
                        # Limpiar
                        Remove-Item -Path $tempDir -Recurse -Force
                        
                        Write-Host ""
                        Write-Host "   ✨ ¡Setup completado! La app está lista para ejecutarse." -ForegroundColor Green
                    } else {
                        Write-Host "   ❌ No se encontró la base de datos en el repositorio" -ForegroundColor Red
                        Write-Host "   Por favor, descárgala manualmente (Opción 1)" -ForegroundColor Yellow
                    }
                } else {
                    Write-Host "   ❌ Error al clonar el repositorio" -ForegroundColor Red
                    Write-Host "   Por favor, descárgala manualmente (Opción 1)" -ForegroundColor Yellow
                }
            } else {
                Write-Host "   OK, descárgala manualmente cuando estés listo." -ForegroundColor Cyan
            }
        }
    } catch {
        Write-Host "   ❌ Git no está instalado" -ForegroundColor Red
        Write-Host "   Usa la Opción 1 para descargar manualmente" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "  Verificación Completada" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "📚 Para más información, lee:" -ForegroundColor Cyan
Write-Host "   - INSTRUCCIONES.md" -ForegroundColor White
Write-Host "   - RESUMEN_IMPLEMENTACION.md" -ForegroundColor White
Write-Host "   - README_SETUP.md" -ForegroundColor White
Write-Host ""
Write-Host "Presiona cualquier tecla para salir..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
