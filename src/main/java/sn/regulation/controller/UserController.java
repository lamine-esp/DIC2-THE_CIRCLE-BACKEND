package sn.regulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sn.regulation.model.Utilisateur;
import sn.regulation.service.AuthService;
import sn.regulation.service.UtilisateurService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UtilisateurService utilisateurService;
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            Utilisateur user = utilisateurService.findByEmail(email);
            if (user != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("nom", user.getNom());
                response.put("prenom", user.getPrenom());
                response.put("email", user.getEmail());
                response.put("telephone", user.getTelephone());
                response.put("role", user.getRole());
                response.put("region", user.getRegion());
                response.put("dateInscription", user.getDateInscription());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body("Utilisateur non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> updateRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            Utilisateur user = utilisateurService.findByEmail(email);
            if (user != null) {
                // Mettre à jour les champs fournis
                if (updateRequest.containsKey("nom")) {
                    user.setNom(updateRequest.get("nom"));
                }
                if (updateRequest.containsKey("prenom")) {
                    user.setPrenom(updateRequest.get("prenom"));
                }
                if (updateRequest.containsKey("telephone")) {
                    user.setTelephone(updateRequest.get("telephone"));
                }
                
                Utilisateur updatedUser = utilisateurService.save(user);
                
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Profil mis à jour avec succès");
                response.put("user", updatedUser);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404).body("Utilisateur non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }
}
