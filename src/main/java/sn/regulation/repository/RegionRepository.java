package sn.regulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.regulation.model.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByNom(String nom);
}
