/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Isra Bouzidi
 */



import model.Livre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotheque_db", "root", "");
    }

    public void ajouterLivre(Livre livre) {
        String sql = "INSERT INTO livre(titre, auteur, annee, disponible) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setInt(3, livre.getAnnee());
            stmt.setBoolean(4, livre.isDisponible());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livre> listerLivres() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livre";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livre livre = new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getInt("annee"),
                        rs.getBoolean("disponible")
                );
                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    public void supprimerLivre(int id) {
        String sql = "DELETE FROM livre WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifierLivre(Livre livre) {
        String sql = "UPDATE livre SET titre = ?, auteur = ?, annee = ?, disponible = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setInt(3, livre.getAnnee());
            stmt.setBoolean(4, livre.isDisponible());
            stmt.setInt(5, livre.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}