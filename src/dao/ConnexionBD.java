/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Isra Bouzidi
 */



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            return null;
        }
    }
}
