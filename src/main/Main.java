
package main;

import ui.AdherentPanel;
import ui.LivrePanel;
import ui.EmpruntPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class Main {

    // Application colors
    private static final Color NAVY = new Color(25, 39, 65);
    private static final Color NAVY_LIGHT = new Color(37, 55, 85);
    private static final Color BLUE = new Color(55, 115, 220);
    private static final Color BACKGROUND = new Color(245, 247, 251);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(45, 55, 72);
    private static final Color MUTED = new Color(135, 145, 160);

    private static CardLayout cardLayout;
    private static JPanel contentPanel;
    private static JLabel pageTitle;
    private static JButton[] navigationButtons;
    private static int selectedIndex = 0;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            createWindow();
        });
    }

    private static void createWindow() {

        JFrame frame = new JFrame("Bibliothèque | Gestion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 780);
        frame.setMinimumSize(new Dimension(1000, 650));
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(BACKGROUND);
        frame.setLayout(new BorderLayout());

        // Main content
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BACKGROUND);

        // Existing panels
        AdherentPanel adherentPanel = new AdherentPanel();
        LivrePanel livrePanel = new LivrePanel();
        EmpruntPanel empruntPanel = new EmpruntPanel();

        // Dashboard
        contentPanel.add(createDashboard(), "DASHBOARD");
        contentPanel.add(adherentPanel, "ADHERENTS");
        contentPanel.add(livrePanel, "LIVRES");
        contentPanel.add(empruntPanel, "EMPRUNTS");

        // Sidebar
        JPanel sidebar = createSidebar();

        // Header
        JPanel header = createHeader();

        // Main workspace
        JPanel workspace = new JPanel(new BorderLayout());
        workspace.setBackground(BACKGROUND);
        workspace.add(header, BorderLayout.NORTH);
        workspace.add(contentPanel, BorderLayout.CENTER);

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(workspace, BorderLayout.CENTER);

        showPage("DASHBOARD", "Dashboard", 0);

        frame.setVisible(true);
    }

    // ================= SIDEBAR =================

    private static JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));

        // Logo / Application name
        JLabel logo = new JLabel("  BOOKSPACE");
        logo.setFont(new Font("SansSerif", Font.BOLD, 23));
        logo.setForeground(WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("  LIBRARY MANAGEMENT");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 10));
        subtitle.setForeground(new Color(160, 175, 200));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logo);
        sidebar.add(Box.createVerticalStrut(5));
        sidebar.add(subtitle);

        sidebar.add(Box.createVerticalStrut(45));

        JLabel menuLabel = new JLabel("  MENU PRINCIPAL");
        menuLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        menuLabel.setForeground(MUTED);
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(menuLabel);
        sidebar.add(Box.createVerticalStrut(15));

        navigationButtons = new JButton[4];

        navigationButtons[0] = createNavButton("⌂    Dashboard");
        navigationButtons[1] = createNavButton("♙    Adhérents");
        navigationButtons[2] = createNavButton("▤    Livres");
        navigationButtons[3] = createNavButton("⇄    Emprunts");

        String[] pages = {
            "DASHBOARD", "ADHERENTS", "LIVRES", "EMPRUNTS"
        };

        String[] titles = {
            "Dashboard", "Gestion des adhérents",
            "Gestion des livres", "Gestion des emprunts"
        };

        for (int i = 0; i < navigationButtons.length; i++) {

            final int index = i;

            navigationButtons[i].addActionListener(e ->
                showPage(pages[index], titles[index], index)
            );

            sidebar.add(navigationButtons[i]);
            sidebar.add(Box.createVerticalStrut(8));
        }

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("  BOOKSPACE  •  Version 1.0");
        footer.setFont(new Font("SansSerif", Font.PLAIN, 10));
        footer.setForeground(MUTED);
        footer.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(footer);

        return sidebar;
    }

    private static JButton createNavButton(String text) {

        JButton button = new JButton(text);

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
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // ================= HEADER =================

    private static JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(new EmptyBorder(22, 30, 22, 30));
        header.setPreferredSize(new Dimension(0, 85));

        pageTitle = new JLabel("Dashboard");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        pageTitle.setForeground(TEXT);

        JLabel rightLabel = new JLabel("Système de gestion de bibliothèque");
        rightLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        rightLabel.setForeground(MUTED);

        header.add(pageTitle, BorderLayout.WEST);
        header.add(rightLabel, BorderLayout.EAST);

        return header;
    }

    // ================= DASHBOARD =================

    private static JPanel createDashboard() {

        JPanel dashboard = new JPanel(new BorderLayout(0, 25));
        dashboard.setBackground(BACKGROUND);
        dashboard.setBorder(new EmptyBorder(35, 35, 35, 35));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(BACKGROUND);

        JLabel welcome = new JLabel("Bienvenue dans BOOKSPACE !");
        welcome.setFont(new Font("SansSerif", Font.BOLD, 25));
        welcome.setForeground(TEXT);

        JLabel description = new JLabel(
            "Gérez facilement votre bibliothèque depuis un seul espace."
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 14));
        description.setForeground(MUTED);

        top.add(welcome);
        top.add(Box.createVerticalStrut(10));
        top.add(description);

        dashboard.add(top, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 20));
        cards.setBackground(BACKGROUND);

        cards.add(createDashboardCard(
            "♙", "Adhérents",
            "Gérer les informations des adhérents",
            "Voir les adhérents", 1
        ));

        cards.add(createDashboardCard(
            "▤", "Livres",
            "Consulter et gérer le catalogue",
            "Voir les livres", 2
        ));

        cards.add(createDashboardCard(
            "⇄", "Emprunts",
            "Suivre les opérations d'emprunt",
            "Voir les emprunts", 3
        ));

        dashboard.add(cards, BorderLayout.CENTER);

        return dashboard;
    }

    private static JPanel createDashboardCard(
        String icon,
        String title,
        String description,
        String actionText,
        int pageIndex
    ) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(new EmptyBorder(25, 22, 25, 22));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        iconLabel.setForeground(BLUE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 19));
        titleLabel.setForeground(TEXT);

        JLabel descLabel = new JLabel(
            "<html><div style='width:180px'>" +
            description + "</div></html>"
        );
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLabel.setForeground(MUTED);

        JButton action = new JButton(actionText);
        action.setFont(new Font("SansSerif", Font.BOLD, 12));
        action.setForeground(BLUE);
        action.setBackground(new Color(235, 242, 255));
        action.setBorder(new EmptyBorder(10, 15, 10, 15));
        action.setFocusPainted(false);
        action.setCursor(new Cursor(Cursor.HAND_CURSOR));
        action.setAlignmentX(Component.LEFT_ALIGNMENT);

        action.addActionListener(e -> {
            String[] pages = {
                "DASHBOARD", "ADHERENTS", "LIVRES", "EMPRUNTS"
            };

            String[] titles = {
                "Dashboard", "Gestion des adhérents",
                "Gestion des livres", "Gestion des emprunts"
            };

            showPage(pages[pageIndex], titles[pageIndex], pageIndex);
        });

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(25));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(descLabel);
        card.add(Box.createVerticalGlue());
        card.add(action);

        return card;
    }

    // ================= NAVIGATION =================

    private static void showPage(
        String page,
        String title,
        int index
    ) {

        cardLayout.show(contentPanel, page);
        pageTitle.setText(title);

        selectedIndex = index;

        for (int i = 0; i < navigationButtons.length; i++) {

            JButton button = navigationButtons[i];

            if (i == selectedIndex) {
                button.setBackground(BLUE);
                button.setForeground(WHITE);
            } else {
                button.setBackground(NAVY);
                button.setForeground(new Color(205, 215, 230));
            }
        }
    }
}
