/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Isra Bouzidi
 */

import model.Emprunt;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {

    public void ajouterEmprunt(Emprunt emprunt) {
        String sql = "INSERT INTO emprunt(livre_id, adherent_id, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = ConnexionBD.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, emprunt.getLivreId());
            stmt.setInt(2, emprunt.getAdherentId());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            if (emprunt.getDateRetour() != null) {
                stmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierEmprunt(Emprunt emprunt) {
        String sql = "UPDATE emprunt SET livre_id = ?, adherent_id = ?, date_emprunt = ?, date_retour = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnexionBD.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, emprunt.getLivreId());
            stmt.setInt(2, emprunt.getAdherentId());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            if (emprunt.getDateRetour() != null) {
                stmt.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            stmt.setInt(5, emprunt.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerEmprunt(int id) {
    String sql = "DELETE FROM emprunt WHERE id = ?";
    try (PreparedStatement stmt = ConnexionBD.getConnexion().prepareStatement(sql)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public void modifierRetourEmprunt(int id, LocalDate dateRetour) {
        String sql = "UPDATE emprunt SET date_retour = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnexionBD.getConnexion().prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dateRetour));
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Emprunt> listerEmprunts() {
        List<Emprunt> emprunts = new ArrayList<>();
        String sql = "SELECT * FROM emprunt";
        try (Statement stmt = ConnexionBD.getConnexion().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Emprunt e = new Emprunt();
                e.setId(rs.getInt("id"));
                e.setLivreId(rs.getInt("livre_id"));
                e.setAdherentId(rs.getInt("adherent_id"));
                e.setDateEmprunt(rs.getDate("date_emprunt").toLocalDate());
                Date dRetour = rs.getDate("date_retour");
                if (dRetour != null) {
                    e.setDateRetour(dRetour.toLocalDate());
                }
                emprunts.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprunts;
    }
}
