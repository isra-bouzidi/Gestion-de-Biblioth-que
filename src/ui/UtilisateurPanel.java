
package ui;

import dao.UtilisateurDAO;
import model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UtilisateurPanel extends JPanel {

    // Couleurs de l'application
    private static final Color BLUE = new Color(55, 115, 220);
    private static final Color DARK_BLUE = new Color(40, 85, 170);
    private static final Color BACKGROUND = new Color(245, 247, 250);
    private static final Color TEXT_COLOR = new Color(45, 55, 70);
    private static final Color BORDER_COLOR = new Color(220, 225, 232);
    private static final Color GREEN = new Color(25, 155, 125);

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    private LoginListener loginListener;

    public UtilisateurPanel() {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Carte principale
        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(35, 45, 35, 45)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 10, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        // Icône
        JLabel iconLabel = new JLabel("●");
        iconLabel.setFont(new Font("Arial", Font.BOLD, 34));
        iconLabel.setForeground(BLUE);
        loginCard.add(iconLabel, gbc);

        // Titre
        JLabel titleLabel = new JLabel("Connexion");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
        titleLabel.setForeground(TEXT_COLOR);
        gbc.gridy++;
        gbc.insets = new Insets(0, 5, 8, 5);
        loginCard.add(titleLabel, gbc);

        // Sous-titre
        JLabel subtitleLabel = new JLabel("Connectez-vous à votre espace");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.GRAY);
        gbc.gridy++;
        gbc.insets = new Insets(0, 5, 25, 5);
        loginCard.add(subtitleLabel, gbc);

        // Nom d'utilisateur
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 7, 0);

        JLabel usernameLabel = new JLabel("Nom d'utilisateur");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        usernameLabel.setForeground(TEXT_COLOR);
        loginCard.add(usernameLabel, gbc);

        usernameField = createTextField();
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        loginCard.add(usernameField, gbc);

        // Mot de passe
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 7, 0);

        JLabel passwordLabel = new JLabel("Mot de passe");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        passwordLabel.setForeground(TEXT_COLOR);
        loginCard.add(passwordLabel, gbc);

        passwordField = createPasswordField();
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        loginCard.add(passwordField, gbc);

        // Bouton connexion
        loginButton = createButton("Se connecter", BLUE);
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 12, 0);
        loginCard.add(loginButton, gbc);

        // Message d'erreur / succès
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        messageLabel.setForeground(Color.RED);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        loginCard.add(messageLabel, gbc);

        // Centrer la carte
        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.anchor = GridBagConstraints.CENTER;
        cardGbc.fill = GridBagConstraints.NONE;
        add(loginCard, cardGbc);

        loginButton.addActionListener(e -> authentifier());

        passwordField.addActionListener(e -> authentifier());
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(TEXT_COLOR);
        field.setBackground(Color.WHITE);
        field.setCaretColor(BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(TEXT_COLOR);
        field.setBackground(Color.WHITE);
        field.setCaretColor(BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        return field;
    }

    // Même style de bouton que AdherentPanel et EmpruntPanel
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(11, 18, 11, 18));

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void authentifier() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setForeground(new Color(220, 75, 75));
            messageLabel.setText("Veuillez saisir votre nom d'utilisateur et mot de passe.");
            return;
        }

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        Utilisateur utilisateur = utilisateurDAO.authentifier(username, password);

        if (utilisateur != null) {
            messageLabel.setForeground(GREEN);
            messageLabel.setText("Connexion réussie !");

            if (loginListener != null) {
                loginListener.onLoginSuccess(utilisateur);
            }
        } else {
            messageLabel.setForeground(new Color(220, 75, 75));
            messageLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    public void setLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public interface LoginListener {
        void onLoginSuccess(Utilisateur utilisateur);
    }
}
