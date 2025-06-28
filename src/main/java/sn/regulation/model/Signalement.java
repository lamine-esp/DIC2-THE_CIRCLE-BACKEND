package sn.regulation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "signalements")
public class Signalement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "prix_observe", nullable = false)
    private Double prixObserve;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Column(name = "date_signalement", nullable = false)
    private LocalDateTime dateSignalement = LocalDateTime.now();

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;

    public enum Statut {
        EN_ATTENTE, VALIDE, REJETE
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    public Double getPrixObserve() { return prixObserve; }
    public void setPrixObserve(Double prixObserve) { this.prixObserve = prixObserve; }
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public LocalDateTime getDateSignalement() { return dateSignalement; }
    public void setDateSignalement(LocalDateTime dateSignalement) { this.dateSignalement = dateSignalement; }
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
}
