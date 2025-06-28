package sn.regulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.regulation.model.Utilisateur;
import sn.regulation.repository.RegionRepository;
import sn.regulation.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RegionRepository regionRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String motDePasse = loginRequest.get("motDePasse");
        
        try {
            var userOpt = authService.login(email, motDePasse);
            if (userOpt.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Connexion réussie");
                response.put("user", userOpt.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
        try {
            if (authService.existsByEmail(registerRequest.get("email"))) {
                return ResponseEntity.badRequest().body("Email déjà utilisé");
            }
            if (authService.existsByTelephone(registerRequest.get("telephone"))) {
                return ResponseEntity.badRequest().body("Téléphone déjà utilisé");
            }
            
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(registerRequest.get("nom"));
            utilisateur.setPrenom(registerRequest.get("prenom"));
            utilisateur.setEmail(registerRequest.get("email"));
            utilisateur.setTelephone(registerRequest.get("telephone"));
            utilisateur.setMotDePasse(registerRequest.get("motDePasse"));
            utilisateur.setRole(Utilisateur.Role.valueOf(registerRequest.getOrDefault("role", "CONSOMMATEUR").toUpperCase()));
            utilisateur.setRegion(regionRepository.findById(Integer.parseInt(registerRequest.get("regionId"))).orElse(null));
            
            Utilisateur saved = authService.register(utilisateur);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inscription réussie");
            response.put("user", saved);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }
}
