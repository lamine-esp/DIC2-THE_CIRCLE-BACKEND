package sn.regulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.regulation.model.Prix;
import sn.regulation.repository.PrixRepository;

import java.util.List;

@RestController
@RequestMapping("/api/prix")
public class PrixController {
    @Autowired
    private PrixRepository prixRepository;

    @GetMapping("/produit/{nomProduit}")
    public List<Prix> getPrixByNomProduit(@PathVariable String nomProduit) {
        return prixRepository.findByProduitNom(nomProduit);
    }

    @GetMapping
    public List<Prix> getAll() {
        return prixRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prix> getById(@PathVariable Integer id) {
        return prixRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Prix create(@RequestBody Prix prix) {
        return prixRepository.save(prix);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prix> update(@PathVariable Integer id, @RequestBody Prix prix) {
        return prixRepository.findById(id)
                .map(p -> {
                    p.setProduit(prix.getProduit());
                    p.setRegion(prix.getRegion());
                    p.setValeur(prix.getValeur());
                    p.setDateMiseAJour(prix.getDateMiseAJour());
                    p.setSource(prix.getSource());
                    p.setPrixOfficiel(prix.getPrixOfficiel());
                    return ResponseEntity.ok(prixRepository.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!prixRepository.existsById(id)) return ResponseEntity.notFound().build();
        prixRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
