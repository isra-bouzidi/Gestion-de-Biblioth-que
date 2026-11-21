
package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class AdminPanel extends JPanel {

    // Colors
    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color WHITE = Color.WHITE;
    private final Color DARK = new Color(45, 55, 72);
    private final Color GREY = new Color(135, 145, 160);

    // Navigation callback
    private final Consumer<String> navigation;

    public AdminPanel(Consumer<String> navigation) {

        this.navigation = navigation;

        setLayout(new BorderLayout(0, 25));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(35, 35, 35, 35));

        createDashboard();
    }

    // ================= DASHBOARD =================

    private void createDashboard() {

        // Welcome section
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(
            new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)
        );
        welcomePanel.setBackground(BACKGROUND);

        JLabel title = new JLabel("Bienvenue dans BOOKSPACE !");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setForeground(DARK);

        JLabel subtitle = new JLabel(
            "Gérez votre bibliothèque depuis votre espace administrateur."
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(GREY);

        welcomePanel.add(title);
        welcomePanel.add(Box.createVerticalStrut(10));
        welcomePanel.add(subtitle);

        add(welcomePanel, BorderLayout.NORTH);

        // Management cards
        JPanel cardsPanel = new JPanel(
            new GridLayout(1, 3, 20, 20)
        );
        cardsPanel.setBackground(BACKGROUND);

        cardsPanel.add(createCard(
            "01",
            "▤",
            "Livres",
            "Consultez et gérez le catalogue de votre bibliothèque.",
            "Accéder aux livres",
            "LIVRES",
            new Color(55, 115, 220)
        ));

        cardsPanel.add(createCard(
            "02",
            "♙",
            "Adhérents",
            "Gérez les informations et les inscriptions des membres.",
            "Accéder aux adhérents",
            "ADHERENTS",
            new Color(25, 155, 125)
        ));

        cardsPanel.add(createCard(
            "03",
            "⇄",
            "Emprunts",
            "Consultez et gérez les opérations d'emprunt.",
            "Accéder aux emprunts",
            "EMPRUNTS",
            new Color(225, 145, 55)
        ));

        add(cardsPanel, BorderLayout.CENTER);

        // Footer
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BACKGROUND);

        JLabel information = new JLabel(
            "BOOKSPACE | Espace Administrateur"
        );
        information.setFont(
            new Font("SansSerif", Font.PLAIN, 12)
        );
        information.setForeground(GREY);

        bottomPanel.add(information, BorderLayout.WEST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ================= DASHBOARD CARD =================

    private JPanel createCard(
        String number,
        String iconText,
        String title,
        String description,
        String buttonText,
        String page,
        Color accent
    ) {

        JPanel card = new JPanel();
        card.setLayout(
            new BoxLayout(card, BoxLayout.Y_AXIS)
        );
        card.setBackground(WHITE);

        card.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(230, 234, 240)
                ),
                new EmptyBorder(25, 22, 25, 22)
            )
        );

        // Number
        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(
            new Font("SansSerif", Font.BOLD, 13)
        );
        numberLabel.setForeground(accent);
        numberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Icon
        JLabel icon = new JLabel(iconText);
        icon.setFont(
            new Font("SansSerif", Font.BOLD, 34)
        );
        icon.setForeground(accent);
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
            new Font("SansSerif", Font.BOLD, 20)
        );
        titleLabel.setForeground(DARK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Description
        JLabel descriptionLabel = new JLabel(
            "<html><div style='width:190px;'>"
            + description
            + "</div></html>"
        );
        descriptionLabel.setFont(
            new Font("SansSerif", Font.PLAIN, 13)
        );
        descriptionLabel.setForeground(GREY);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Action button
        JButton button = new JButton(buttonText);

        button.setFont(
            new Font("SansSerif", Font.BOLD, 12)
        );

        button.setForeground(Color.WHITE);
        button.setBackground(accent);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setBorder(
            new EmptyBorder(12, 18, 12, 18)
        );

        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Hover effect
        Color darkerAccent = accent.darker();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(darkerAccent);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(accent);
            }
        });

        // Navigation
        button.addActionListener(e -> {
            if (navigation != null) {
                navigation.accept(page);
            }
        });

        // Add components
        card.add(numberLabel);
        card.add(Box.createVerticalStrut(20));

        card.add(icon);
        card.add(Box.createVerticalStrut(20));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(12));

        card.add(descriptionLabel);
        card.add(Box.createVerticalGlue());

        card.add(button);

        return card;
    }
}
