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

public class Emprunt {
    private int id;
    private int livreId;
    private int adherentId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt() {}

    public Emprunt(int id, int livreId, int adherentId, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.livreId = livreId;
        this.adherentId = adherentId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getLivreId() { return livreId; }
    public void setLivreId(int livreId) { this.livreId = livreId; }

    public int getAdherentId() { return adherentId; }
    public void setAdherentId(int adherentId) { this.adherentId = adherentId; }

    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }

    public LocalDate getDateRetour() { return dateRetour; }
    public void setDateRetour(LocalDate dateRetour) { this.dateRetour = dateRetour; }
}
