package sn.regulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.regulation.model.Utilisateur;
import sn.regulation.repository.UtilisateurRepository;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Utilisateur> login(String email, String motDePasse) {
        Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(motDePasse, userOpt.get().getMotDePasse())) {
            return userOpt;
        }
        return Optional.empty();
    }

    public Utilisateur register(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public boolean existsByEmail(String email) {
        return utilisateurRepository.findByEmail(email).isPresent();
    }

    public boolean existsByTelephone(String telephone) {
        return utilisateurRepository.findByTelephone(telephone).isPresent();
    }
}
