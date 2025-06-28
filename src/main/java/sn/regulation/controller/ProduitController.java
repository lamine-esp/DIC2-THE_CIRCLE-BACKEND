package sn.regulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.regulation.model.Produit;
import sn.regulation.repository.ProduitRepository;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {
    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public List<Produit> getAll() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getById(@PathVariable Integer id) {
        return produitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Produit create(@RequestBody Produit produit) {
        return produitRepository.save(produit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(@PathVariable Integer id, @RequestBody Produit produit) {
        return produitRepository.findById(id)
                .map(p -> {
                    p.setNom(produit.getNom());
                    p.setCategorie(produit.getCategorie());
                    p.setDescription(produit.getDescription());
                    p.setUnite(produit.getUnite());
                    return ResponseEntity.ok(produitRepository.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!produitRepository.existsById(id)) return ResponseEntity.notFound().build();
        produitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
