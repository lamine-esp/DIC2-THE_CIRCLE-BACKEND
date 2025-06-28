package sn.regulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.regulation.model.Utilisateur;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findByTelephone(String telephone);
}
