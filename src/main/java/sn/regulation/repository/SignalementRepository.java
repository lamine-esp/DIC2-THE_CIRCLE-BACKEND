package sn.regulation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.regulation.model.Signalement;

public interface SignalementRepository extends JpaRepository<Signalement, Integer> {
}
