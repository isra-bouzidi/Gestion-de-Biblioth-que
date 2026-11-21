
package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFrame extends JFrame {

    // ================= COLORS =================

    private final Color NAVY = new Color(25, 39, 65);
    private final Color BLUE = new Color(55, 115, 220);
    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(45, 55, 72);
    private final Color MUTED = new Color(150, 160, 175);

    // ================= COMPONENTS =================

    private UtilisateurPanel utilisateurPanel;

    private JPanel contentPanel;

    private CardLayout cardLayout;

    private JLabel pageTitle;

    private JLabel userRoleLabel;

    private List<JButton> navigationButtons = new ArrayList<>();

    // ================= CONSTRUCTOR =================

    public MainFrame() {

        setTitle("BOOKSPACE | Gestion de Bibliothèque");

        setSize(1250, 780);

        setMinimumSize(new Dimension(1000, 650));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        afficherLogin();
    }

    // ================= LOGIN =================

    private void afficherLogin() {

        JPanel loginContainer = new JPanel(new BorderLayout());

        loginContainer.setBackground(BACKGROUND);

        JLabel header = new JLabel(
            "BOOKSPACE | Gestion de Bibliothèque",
            SwingConstants.CENTER
        );

        header.setFont(new Font("SansSerif", Font.BOLD, 21));
        header.setForeground(NAVY);
        header.setBorder(new EmptyBorder(25, 10, 25, 10));

        utilisateurPanel = new UtilisateurPanel();

        loginContainer.add(header, BorderLayout.NORTH);
        loginContainer.add(utilisateurPanel, BorderLayout.CENTER);

        setContentPane(loginContainer);

        utilisateurPanel.setLoginListener(utilisateur -> {

            String role = utilisateur.getRole();

            afficherInterfaceSelonRole(role);
        });
    }

    // ================= ROLE MANAGEMENT =================

    private void afficherInterfaceSelonRole(String role) {

        if (role == null) {

            JOptionPane.showMessageDialog(
                this,
                "Rôle utilisateur invalide.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String normalizedRole = role.trim().toLowerCase(Locale.ROOT);

        if (!normalizedRole.equals("administrateur")
                && !normalizedRole.equals("bibliothecaire")) {

            JOptionPane.showMessageDialog(
                this,
                "Rôle non reconnu : " + role,
                "Erreur",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Reset navigation
        navigationButtons.clear();

        // Main application
        JPanel application = new JPanel(new BorderLayout());
        application.setBackground(BACKGROUND);

        // Sidebar
        JPanel sidebar = createSidebar(normalizedRole);

        // Workspace
        JPanel workspace = new JPanel(new BorderLayout());
        workspace.setBackground(BACKGROUND);

        // Header
        JPanel header = createHeader(role);

        // Content with CardLayout
        cardLayout = new CardLayout();

        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BACKGROUND);

        // Administrator dashboard
        if (normalizedRole.equals("administrateur")) {

            contentPanel.add(
                new AdminPanel(this::ouvrirPage),
                "DASHBOARD"
            );

        } else {

            // Librarian dashboard
            contentPanel.add(
                createDashboard(role),
                "DASHBOARD"
            );
        }

        // Administrator pages
        if (normalizedRole.equals("administrateur")) {

            contentPanel.add(new LivrePanel(), "LIVRES");

            contentPanel.add(new AdherentPanel(), "ADHERENTS");

            contentPanel.add(new EmpruntPanel(), "EMPRUNTS");

        } else {

            // Librarian can access loans only
            contentPanel.add(new EmpruntPanel(), "EMPRUNTS");
        }

        // Workspace
        workspace.add(header, BorderLayout.NORTH);
        workspace.add(contentPanel, BorderLayout.CENTER);

        // Application layout
        application.add(sidebar, BorderLayout.WEST);
        application.add(workspace, BorderLayout.CENTER);

        setContentPane(application);

        afficherPage("DASHBOARD", "Dashboard", 0);

        revalidate();
        repaint();
    }

    // ================= SIDEBAR =================

    private JPanel createSidebar(String role) {

        JPanel sidebar = new JPanel();

        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(245, 0));

        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));

        // Logo
        JLabel logo = new JLabel("BOOKSPACE");

        logo.setFont(new Font("SansSerif", Font.BOLD, 23));
        logo.setForeground(WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("LIBRARY MANAGEMENT");

        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 10));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(7));
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(40));

        // Menu title
        JLabel menuTitle = new JLabel("MENU PRINCIPAL");

        menuTitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        menuTitle.setForeground(MUTED);
        menuTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(menuTitle);
        sidebar.add(Box.createVerticalStrut(15));

        // Dashboard
        ajouterBoutonNavigation(
            sidebar,
            "⌂    Dashboard",
            "DASHBOARD",
            "Dashboard"
        );

        // Administrator menu
        if (role.equals("administrateur")) {

            ajouterBoutonNavigation(
                sidebar,
                "▤    Livres",
                "LIVRES",
                "Gestion des livres"
            );

            ajouterBoutonNavigation(
                sidebar,
                "♙    Adhérents",
                "ADHERENTS",
                "Gestion des adhérents"
            );
        }

        // Both roles can access loans
        ajouterBoutonNavigation(
            sidebar,
            "⇄    Emprunts",
            "EMPRUNTS",
            "Gestion des emprunts"
        );

        sidebar.add(Box.createVerticalGlue());

        // Footer
        JLabel footer = new JLabel("BOOKSPACE • Version 1.0");

        footer.setFont(new Font("SansSerif", Font.PLAIN, 10));
        footer.setForeground(MUTED);
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(footer);

        return sidebar;
    }

    // ================= NAVIGATION BUTTON =================

    private void ajouterBoutonNavigation(
        JPanel sidebar,
        String text,
        String page,
        String title
    ) {

        JButton button = new JButton(text);

        // Use BasicButtonUI to prevent the system look and feel
        // from overriding the button background.
        button.setUI(new BasicButtonUI());

        button.setFont(new Font("SansSerif", Font.PLAIN, 14));

        button.setForeground(new Color(205, 215, 230));
        button.setBackground(NAVY);

        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.setPreferredSize(new Dimension(215, 48));

        button.setBorder(new EmptyBorder(10, 15, 10, 10));

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Store page identifier
        button.setActionCommand(page);

        button.putClientProperty("active", false);

        int index = navigationButtons.size();

        navigationButtons.add(button);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                boolean active = Boolean.TRUE.equals(
                    button.getClientProperty("active")
                );

                if (!active) {
                    button.setBackground(new Color(38, 57, 88));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                boolean active = Boolean.TRUE.equals(
                    button.getClientProperty("active")
                );

                if (!active) {
                    button.setBackground(NAVY);
                }
            }
        });

        button.addActionListener(e -> {

            afficherPage(page, title, index);

        });

        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(button);

        sidebar.add(Box.createVerticalStrut(8));
    }

    // ================= HEADER =================

    private JPanel createHeader(String role) {

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(WHITE);
        header.setPreferredSize(new Dimension(0, 85));

        header.setBorder(new EmptyBorder(20, 30, 20, 30));

        pageTitle = new JLabel("Dashboard");

        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        pageTitle.setForeground(TEXT);

        userRoleLabel = new JLabel(
            "Connecté en tant que : " + role
        );

        userRoleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        userRoleLabel.setForeground(MUTED);

        header.add(pageTitle, BorderLayout.WEST);
        header.add(userRoleLabel, BorderLayout.EAST);

        return header;
    }

    // ================= LIBRARIAN DASHBOARD =================

    private JPanel createDashboard(String role) {

        JPanel dashboard = new JPanel(new BorderLayout());

        dashboard.setBackground(BACKGROUND);
        dashboard.setBorder(new EmptyBorder(35, 35, 35, 35));

        JPanel welcomePanel = new JPanel();

        welcomePanel.setLayout(
            new BoxLayout(welcomePanel, BoxLayout.Y_AXIS)
        );

        welcomePanel.setBackground(BACKGROUND);

        JLabel welcome = new JLabel(
            "Bienvenue dans BOOKSPACE !"
        );

        welcome.setFont(new Font("SansSerif", Font.BOLD, 25));
        welcome.setForeground(TEXT);

        JLabel description = new JLabel(
            "Votre espace de gestion de bibliothèque."
        );

        description.setFont(new Font("SansSerif", Font.PLAIN, 14));
        description.setForeground(MUTED);

        welcomePanel.add(welcome);
        welcomePanel.add(Box.createVerticalStrut(10));
        welcomePanel.add(description);

        dashboard.add(welcomePanel, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 1, 20, 20));

        cards.setBackground(BACKGROUND);

        cards.add(
            createDashboardCard(
                "⇄",
                "Emprunts",
                "Consultez et gérez les opérations d'emprunt.",
                "Accéder aux emprunts",
                "EMPRUNTS",
                new Color(225, 145, 55)
            )
        );

        dashboard.add(cards, BorderLayout.CENTER);

        return dashboard;
    }

    // ================= DASHBOARD CARD =================

    private JPanel createDashboardCard(
        String iconText,
        String title,
        String description,
        String buttonText,
        String page,
        Color accent
    ) {

        JPanel card = new JPanel();

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);

        card.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(230, 234, 240)
                ),
                new EmptyBorder(25, 22, 25, 22)
            )
        );

        JLabel icon = new JLabel(iconText);

        icon.setFont(new Font("SansSerif", Font.BOLD, 34));
        icon.setForeground(accent);
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(
            "<html><div style='width:200px;'>"
            + description
            + "</div></html>"
        );

        descriptionLabel.setFont(
            new Font("SansSerif", Font.PLAIN, 13)
        );

        descriptionLabel.setForeground(MUTED);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton button = new JButton(buttonText);

        styleActionButton(button, accent);

        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        button.addActionListener(e -> ouvrirPage(page));

        card.add(icon);
        card.add(Box.createVerticalStrut(20));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(12));

        card.add(descriptionLabel);
        card.add(Box.createVerticalGlue());

        card.add(button);

        return card;
    }

    // ================= ACTION BUTTON STYLE =================

    private void styleActionButton(JButton button, Color accent) {

        button.setUI(new BasicButtonUI());

        button.setFont(new Font("SansSerif", Font.BOLD, 12));

        button.setForeground(WHITE);
        button.setBackground(accent);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setBorder(new EmptyBorder(12, 18, 12, 18));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
    }

    // ================= OPEN PAGE =================

    // Called by AdminPanel and librarian dashboard
    private void ouvrirPage(String page) {

        String title;

        switch (page) {

            case "LIVRES":
                title = "Gestion des livres";
                break;

            case "ADHERENTS":
                title = "Gestion des adhérents";
                break;

            case "EMPRUNTS":
                title = "Gestion des emprunts";
                break;

            case "DASHBOARD":
                title = "Dashboard";
                break;

            default:
                return;
        }

        // Find the corresponding sidebar button
        for (int i = 0; i < navigationButtons.size(); i++) {

            JButton button = navigationButtons.get(i);

            if (button.getActionCommand().equals(page)) {

                afficherPage(page, title, i);

                return;
            }
        }
    }

    // ================= DISPLAY PAGE =================

    private void afficherPage(
        String page,
        String title,
        int selectedIndex
    ) {

        cardLayout.show(contentPanel, page);

        pageTitle.setText(title);

        // Update sidebar active button
        for (int i = 0; i < navigationButtons.size(); i++) {

            JButton button = navigationButtons.get(i);

            boolean active = (i == selectedIndex);

            button.putClientProperty("active", active);

            if (active) {

                button.setBackground(BLUE);
                button.setForeground(WHITE);

            } else {

                button.setBackground(NAVY);
                button.setForeground(new Color(205, 215, 230));
            }
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            MainFrame frame = new MainFrame();

            frame.setVisible(true);

        });
    }
}
