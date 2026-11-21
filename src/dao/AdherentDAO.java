/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Isra Bouzidi
 */
import model.Adherent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdherentDAO {


    public void ajouterAdherent(Adherent adherent) {
        String sql = "INSERT INTO adherent(nom, prenom, date_inscription) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = ConnexionBD.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, adherent.getNom());
            stmt.setString(2, adherent.getPrenom());
            stmt.setDate(3, Date.valueOf(adherent.getDateInscription()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerAdherent(int id) {
        String sql = "DELETE FROM adherent WHERE id = ?";
        try (PreparedStatement stmt = ConnexionBD.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierAdherent(Adherent adherent) {
        String sql = "UPDATE adherent SET nom = ?, prenom = ?, date_inscription = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnexionBD.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, adherent.getNom());
            stmt.setString(2, adherent.getPrenom());
            stmt.setDate(3, Date.valueOf(adherent.getDateInscription()));
            stmt.setInt(4, adherent.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Adherent> listerAdherents() {
    List<Adherent> adherents = new ArrayList<>();
    String sql = "SELECT * FROM adherent";

    try (Connection conn = ConnexionBD.getConnexion()) {
        if (conn == null) {
            System.err.println("Connexion échouée !");
            return adherents;
        }
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Adherent a = new Adherent();
                a.setId(rs.getInt("id"));
                a.setNom(rs.getString("nom"));
                a.setPrenom(rs.getString("prenom"));
                a.setDateInscription(rs.getDate("date_inscription").toLocalDate());
                adherents.add(a);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return adherents;
}

}
