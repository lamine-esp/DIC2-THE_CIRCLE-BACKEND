-- Schéma de la base de données regulation_prix_senegal
CREATE DATABASE IF NOT EXISTS regulation_prix_senegal DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE regulation_prix_senegal;

-- Table: regions
CREATE TABLE IF NOT EXISTS regions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL
);

-- Table: produits
CREATE TABLE IF NOT EXISTS produits (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    categorie VARCHAR(50) NOT NULL,
    description TEXT,
    unite VARCHAR(20) NOT NULL
);

-- Table: utilisateurs
CREATE TABLE IF NOT EXISTS utilisateurs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telephone VARCHAR(20) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    region_id INT,
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (region_id) REFERENCES regions(id)
);

-- Table: prix
CREATE TABLE IF NOT EXISTS prix (
    id INT PRIMARY KEY AUTO_INCREMENT,
    produit_id INT NOT NULL,
    region_id INT NOT NULL,
    valeur FLOAT NOT NULL,
    date_mise_a_jour DATETIME NOT NULL,
    source VARCHAR(50) NOT NULL,
    prix_officiel BOOLEAN,
    FOREIGN KEY (produit_id) REFERENCES produits(id),
    FOREIGN KEY (region_id) REFERENCES regions(id)
);

-- Table: signalements
CREATE TABLE IF NOT EXISTS signalements (
    id INT PRIMARY KEY AUTO_INCREMENT,
    utilisateur_id INT NOT NULL,
    produit_id INT NOT NULL,
    region_id INT NOT NULL,
    prix_observe DOUBLE NOT NULL,
    commentaire TEXT,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id),
    FOREIGN KEY (produit_id) REFERENCES produits(id),
    FOREIGN KEY (region_id) REFERENCES regions(id)
);
