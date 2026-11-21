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
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnexion {
    public static void main(String[] args) {
        try (Connection conn = ConnexionBD.getConnexion()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connexion à la base de données réussie !");
            } else {
                System.out.println("❌ Connexion non établie.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Échec de la connexion à la base de données !");
            e.printStackTrace();
        }
    }
}
