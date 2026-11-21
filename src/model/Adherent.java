/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Isra Bouzidi
 */



import java.time.LocalDate;

public class Adherent {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateInscription;

    public Adherent() {}

    public Adherent(int id, String nom, String prenom, LocalDate dateInscription) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateInscription = dateInscription;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDate dateInscription) { this.dateInscription = dateInscription; }
}
