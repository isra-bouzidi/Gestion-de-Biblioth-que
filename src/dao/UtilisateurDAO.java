/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Isra Bouzidi
 */


import model.Utilisateur;

import java.sql.*;

public class UtilisateurDAO {

    public Utilisateur authentifier(String username, String password) {
        String sql = "SELECT * FROM utilisateur WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = ConnexionBD.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password); 

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Utilisateur user = new Utilisateur();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
