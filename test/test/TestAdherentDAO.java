/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author Isra Bouzidi
 */

import dao.ConnexionBD;
import model.Adherent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestAdherentDAO {
    public static void main(String[] args) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            System.out.println("✅ Connexion réussie à la base de données !");

            String sql = "SELECT * FROM adherent";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n📋 Liste des adhérents :");

            boolean found = false;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String dateInscription = rs.getDate("date_inscription").toString();

                System.out.println("👤 ID: " + id + ", Nom: " + nom + ", Prénom: " + prenom + ", Date d'inscription: " + dateInscription);
                found = true;
            }

            if (!found) {
                System.out.println("⚠️ Aucun adhérent trouvé dans la base de données.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Erreur : " + e.getMessage());
        }
    }
}
