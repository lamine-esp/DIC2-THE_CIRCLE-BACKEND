package sn.regulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.regulation.model.Signalement;
import sn.regulation.repository.SignalementRepository;

import java.util.List;

@RestController
@RequestMapping("/api/signalements")
public class SignalementController {
    @Autowired
    private SignalementRepository signalementRepository;

    @GetMapping
    public List<Signalement> getAll() {
        return signalementRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Signalement> getById(@PathVariable Integer id) {
        return signalementRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Signalement create(@RequestBody Signalement signalement) {
        return signalementRepository.save(signalement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Signalement> update(@PathVariable Integer id, @RequestBody Signalement signalement) {
        return signalementRepository.findById(id)
                .map(s -> {
                    s.setUtilisateur(signalement.getUtilisateur());
                    s.setProduit(signalement.getProduit());
                    s.setRegion(signalement.getRegion());
                    s.setPrixObserve(signalement.getPrixObserve());
                    s.setCommentaire(signalement.getCommentaire());
                    s.setDateSignalement(signalement.getDateSignalement());
                    s.setStatut(signalement.getStatut());
                    return ResponseEntity.ok(signalementRepository.save(s));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!signalementRepository.existsById(id)) return ResponseEntity.notFound().build();
        signalementRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
