package sn.regulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.regulation.model.Region;
import sn.regulation.repository.RegionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {
    @Autowired
    private RegionRepository regionRepository;

    @GetMapping
    public List<Region> getAll() {
        return regionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getById(@PathVariable Integer id) {
        return regionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Region create(@RequestBody Region region) {
        return regionRepository.save(region);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> update(@PathVariable Integer id, @RequestBody Region region) {
        return regionRepository.findById(id)
                .map(r -> {
                    r.setNom(region.getNom());
                    return ResponseEntity.ok(regionRepository.save(r));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!regionRepository.existsById(id)) return ResponseEntity.notFound().build();
        regionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
