package sn.regulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.regulation.model.Utilisateur;
import sn.regulation.repository.RegionRepository;
import sn.regulation.service.AuthService;
import sn.regulation.service.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String motDePasse = loginRequest.get("motDePasse");
        
        try {
            var userOpt = authService.login(email, motDePasse);
            if (userOpt.isPresent()) {
                Utilisateur user = userOpt.get();
                String token = jwtUtil.generateToken(user.getEmail());
                
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", createUserResponse(user));
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Pour debug
            return ResponseEntity.status(500).body("Erreur interne du serveur: " + e.getMessage());
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
            String token = jwtUtil.generateToken(saved.getEmail());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", createUserResponse(saved));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // Pour debug
            return ResponseEntity.status(500).body("Erreur interne du serveur: " + e.getMessage());
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractUsername(token);
                
                if (email != null && jwtUtil.validateToken(token, email)) {
                    String newToken = jwtUtil.generateToken(email);
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("token", newToken);
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.status(401).body("Token invalide");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Pour une implémentation complète, on pourrait ajouter une blacklist des tokens
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Déconnexion réussie");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extractUsername(token);
                
                if (email != null && jwtUtil.validateToken(token, email)) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("valid", true);
                    response.put("email", email);
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.status(401).body("Token invalide");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }
    
    private Map<String, Object> createUserResponse(Utilisateur user) {
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("nom", user.getNom());
        userResponse.put("prenom", user.getPrenom());
        userResponse.put("email", user.getEmail());
        userResponse.put("role", user.getRole().toString());
        if (user.getRegion() != null) {
            Map<String, Object> regionResponse = new HashMap<>();
            regionResponse.put("id", user.getRegion().getId());
            regionResponse.put("nom", user.getRegion().getNom());
            userResponse.put("region", regionResponse);
        }
        return userResponse;
    }
}
