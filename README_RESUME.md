# Résumé des étapes réalisées sur le projet Regulation Prix Senegal

## 1. Mise en place du projet Spring Boot
- Création d'une API REST pour la gestion des régions, produits, prix, utilisateurs et signalements.
- Architecture en couches : models, repositories, services, controllers, configuration.

## 2. Sécurité JWT
- Implémentation de l'authentification JWT (login, register).
- Encodage des mots de passe avec BCrypt.
- Configuration Spring Security :
  - Accès ouvert aux endpoints d'authentification et à Swagger UI.
  - Accès temporairement ouvert aux endpoints CRUD pour faciliter les tests.

## 3. Documentation Swagger
- Activation et configuration de Swagger UI pour la documentation interactive de l'API.
- Correction des accès 403 sur les endpoints Swagger.

## 4. Base de données & données de test
- Utilisation de MariaDB/MySQL avec configuration dans `application.properties`.
- Création d'un script `schema_regulation_prix_senegal.sql` pour le schéma de la base (tables, clés étrangères).
- Création d'un script `data.sql` pour insérer des données factices (régions, produits, prix, utilisateurs, signalements).
- Correction des champs et des noms de colonnes pour correspondre aux entités Java.
- Encodage du mot de passe utilisateur en BCrypt dans les données de test.

## 5. Endpoints CRUD
- Contrôleurs REST pour chaque entité (Region, Produit, Prix, Signalement).
- Opérations : GET, POST, PUT, DELETE.

## 6. Endpoint personnalisé : prix par produit
- Ajout d'un endpoint `/api/prix/produit/{nomProduit}` pour récupérer tous les prix d'un produit donné (par nom) pour toutes les régions.
- Création de méthodes personnalisées dans les repositories pour supporter ce besoin.

## 7. Divers
- Correction des erreurs SQL et des incohérences entre le schéma, les entités et les données de test.
- Préparation de la base pour des tests automatisés et l'intégration continue.

---

**Projet prêt pour des tests fonctionnels, l'ajout de rôles/permissions, la validation métier et l'amélioration des réponses API.**

N'hésite pas à compléter ce résumé ou à demander un export plus détaillé !
