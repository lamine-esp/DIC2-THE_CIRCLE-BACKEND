# Script de build optimisé pour Docker (PowerShell)
Write-Host "🐳 Build Docker optimisé pour Regulation Prix Senegal" -ForegroundColor Green
Write-Host "=================================================="

# Variables
$IMAGE_NAME = "regulation-prix-senegal"
$IMAGE_TAG = "latest"
$CONTAINER_NAME = "regulation-prix-app"

# Nettoyage des containers existants
Write-Host "🧹 Nettoyage des containers existants..." -ForegroundColor Yellow
try {
    docker stop $CONTAINER_NAME 2>$null
    docker rm $CONTAINER_NAME 2>$null
} catch {
    # Ignorer les erreurs si les containers n'existent pas
}

# Build de l'image avec cache
Write-Host "🔨 Construction de l'image Docker..." -ForegroundColor Yellow
$buildResult = docker build --build-arg BUILDKIT_INLINE_CACHE=1 --cache-from "${IMAGE_NAME}:${IMAGE_TAG}" -t "${IMAGE_NAME}:${IMAGE_TAG}" .

# Vérification du build
if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Build réussi !" -ForegroundColor Green
    
    # Affichage des informations sur l'image
    Write-Host "📊 Informations sur l'image :" -ForegroundColor Cyan
    docker images "${IMAGE_NAME}:${IMAGE_TAG}"
    
    Write-Host ""
    Write-Host "🚀 Pour démarrer l'application :" -ForegroundColor Green
    Write-Host "docker run -d --name $CONTAINER_NAME -p 8080:8080 --network regulation_network ${IMAGE_NAME}:${IMAGE_TAG}" -ForegroundColor White
    Write-Host ""
    Write-Host "🔍 Pour voir les logs :" -ForegroundColor Green
    Write-Host "docker logs -f $CONTAINER_NAME" -ForegroundColor White
    Write-Host ""
    Write-Host "❤️ Pour vérifier la santé :" -ForegroundColor Green
    Write-Host "curl http://localhost:8080/actuator/health" -ForegroundColor White
} else {
    Write-Host "❌ Échec du build !" -ForegroundColor Red
    exit 1
}
