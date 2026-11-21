package model;

import java.time.LocalDate;

/**
 *
 * @author Isra Bouzidi
 */
public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private int annee;
    private boolean disponible; // You can still keep this if you need it
    private LocalDate dateRetour; // NEW FIELD

    public Livre() {}

    public Livre(int id, String titre, String auteur, int annee, boolean disponible) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.disponible = disponible;
    }

    public Livre(String titre, String auteur, int annee, boolean disponible) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.disponible = disponible;
    }

    // New constructor with dateRetour
    public Livre(String titre, String auteur, int annee, boolean disponible, LocalDate dateRetour) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.disponible = disponible;
        this.dateRetour = dateRetour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public boolean isDisponible() {
        // Logic based on the return date
        return dateRetour == null || dateRetour.isBefore(LocalDate.now());
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", annee=" + annee +
                ", disponible=" + isDisponible() + // updated to reflect true availability
                ", dateRetour=" + dateRetour +
                '}';
    }
}
