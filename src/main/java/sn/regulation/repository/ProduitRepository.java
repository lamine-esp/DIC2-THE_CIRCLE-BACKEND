package sn.regulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.regulation.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    Produit findByNom(String nom);
}
