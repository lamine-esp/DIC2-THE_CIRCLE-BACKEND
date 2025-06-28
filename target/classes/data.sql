-- Données factices pour tests API Regulation Prix Senegal

-- Régions
INSERT INTO regions (id, nom) VALUES (1, 'Dakar'), (2, 'Thiès'), (3, 'Saint-Louis');

-- Produits
INSERT INTO produits (id, categorie, description, unite, nom) VALUES
  (1, 'Céréale', 'Riz brisé importé', 'kg', 'Riz'),
  (2, 'Condiment', 'Huile végétale raffinée', 'litre', 'Huile'),
  (3, 'Sucrerie', 'Sucre cristallisé', 'kg', 'Sucre');

-- Prix (prix courant pour chaque produit dans chaque région)
INSERT INTO prix (id, valeur, produit_id, region_id, source, date_mise_a_jour, prix_officiel) VALUES
  (1, 500, 1, 1, 'Marché Sandaga', NOW(), true), -- Riz à Dakar
  (2, 520, 1, 2, 'Marché central', NOW(), true), -- Riz à Thiès
  (3, 540, 1, 3, 'Marché central', NOW(), false), -- Riz à Saint-Louis
  (4, 1200, 2, 1, 'Marché Sandaga', NOW(), true), -- Huile à Dakar
  (5, 1250, 2, 2, 'Marché central', NOW(), false), -- Huile à Thiès
  (6, 1300, 2, 3, 'Marché central', NOW(), true), -- Huile à Saint-Louis
  (7, 800, 3, 1, 'Marché Sandaga', NOW(), false), -- Sucre à Dakar
  (8, 820, 3, 2, 'Marché central', NOW(), true), -- Sucre à Thiès
  (9, 850, 3, 3, 'Marché central', NOW(), true); -- Sucre à Saint-Louis

-- Utilisateurs (mot de passe à encoder manuellement si sécurité active)
INSERT INTO utilisateurs (id, nom, prenom, email, telephone, mot_de_passe, region_id, role, date_inscription) VALUES
  (1, 'Testeur', 'Alpha', 'testeur@mail.com', '770000001', '$2a$10$eJ2p/OAZL.0U8.y7o2mFZeI9O.4H.T0E0TfC.1AJ.Lz/iR9t.K0gS', 1, 'CONSOMMATEUR', NOW());
-- mot de passe = password (BCrypt encodé correctement)

-- Signalements
INSERT INTO signalements (id, commentaire, prix_observe, produit_id, region_id, utilisateur_id) VALUES
  (1, 'Prix trop élevé constaté', 600, 1, 1, 1),
  (2, 'Prix correct', 500, 1, 2, 1);
