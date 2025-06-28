package sn.regulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.regulation.model.Prix;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrixRepository extends JpaRepository<Prix, Integer> {
    @Query("SELECT p FROM Prix p WHERE p.produit.nom = :nomProduit")
    List<Prix> findByProduitNom(@Param("nomProduit") String nomProduit);
}
