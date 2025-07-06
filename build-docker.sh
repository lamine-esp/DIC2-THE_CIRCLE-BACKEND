#!/bin/bash

# Script de build optimisé pour Docker
echo "🐳 Build Docker optimisé pour Regulation Prix Senegal"
echo "=================================================="

# Variables
IMAGE_NAME="regulation-prix-senegal"
IMAGE_TAG="latest"
CONTAINER_NAME="regulation-prix-app"

# Nettoyage des containers existants
echo "🧹 Nettoyage des containers existants..."
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

# Build de l'image avec cache
echo "🔨 Construction de l'image Docker..."
docker build \
  --build-arg BUILDKIT_INLINE_CACHE=1 \
  --cache-from $IMAGE_NAME:$IMAGE_TAG \
  -t $IMAGE_NAME:$IMAGE_TAG \
  .

# Vérification du build
if [ $? -eq 0 ]; then
    echo "✅ Build réussi !"
    
    # Affichage des informations sur l'image
    echo "📊 Informations sur l'image :"
    docker images $IMAGE_NAME:$IMAGE_TAG
    
    echo ""
    echo "🚀 Pour démarrer l'application :"
    echo "docker run -d --name $CONTAINER_NAME -p 8080:8080 --network regulation_network $IMAGE_NAME:$IMAGE_TAG"
    echo ""
    echo "🔍 Pour voir les logs :"
    echo "docker logs -f $CONTAINER_NAME"
    echo ""
    echo "❤️ Pour vérifier la santé :"
    echo "curl http://localhost:8080/actuator/health"
else
    echo "❌ Échec du build !"
    exit 1
fi
