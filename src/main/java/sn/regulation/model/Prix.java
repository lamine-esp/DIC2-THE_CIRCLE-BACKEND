package sn.regulation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prix")
public class Prix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(nullable = false)
    private Float valeur;

    @Column(name = "date_mise_a_jour", nullable = false)
    private LocalDateTime dateMiseAJour = LocalDateTime.now();

    @Column(nullable = false, length = 50)
    private String source;

    @Column(name = "prix_officiel", nullable = false)
    private Boolean prixOfficiel = false;

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    public Float getValeur() { return valeur; }
    public void setValeur(Float valeur) { this.valeur = valeur; }
    public LocalDateTime getDateMiseAJour() { return dateMiseAJour; }
    public void setDateMiseAJour(LocalDateTime dateMiseAJour) { this.dateMiseAJour = dateMiseAJour; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Boolean getPrixOfficiel() { return prixOfficiel; }
    public void setPrixOfficiel(Boolean prixOfficiel) { this.prixOfficiel = prixOfficiel; }
}
