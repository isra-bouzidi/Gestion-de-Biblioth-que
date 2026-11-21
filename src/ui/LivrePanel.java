
package ui;

import dao.LivreDAO;
import model.Livre;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class LivrePanel extends JPanel {

    private final LivreDAO livreDAO = new LivreDAO();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private JLabel lblCount;

    // Couleurs BOOKSPACE
    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color WHITE = Color.WHITE;
    private final Color NAVY = new Color(25, 39, 65);
    private final Color BLUE = new Color(55, 115, 220);
    private final Color GREEN = new Color(25, 155, 125);
    private final Color ORANGE = new Color(225, 145, 55);
    private final Color RED = new Color(220, 75, 75);
    private final Color TEXT = new Color(45, 55, 72);
    private final Color MUTED = new Color(130, 140, 155);

    public LivrePanel() {

        setLayout(new BorderLayout(0, 20));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);

        JLabel lblTitre = new JLabel("Gestion des livres");
        lblTitre.setFont(new Font("SansSerif", Font.BOLD, 25));
        lblTitre.setForeground(NAVY);

        JLabel lblSubtitle = new JLabel(
                "Consultez et gérez les livres de votre bibliothèque."
        );
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtitle.setForeground(MUTED);

        titlePanel.add(lblTitre);
        titlePanel.add(Box.createVerticalStrut(7));
        titlePanel.add(lblSubtitle);

        header.add(titlePanel, BorderLayout.WEST);

        lblCount = new JLabel("0 livre(s)");
        lblCount.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCount.setForeground(BLUE);

        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        countPanel.setBackground(BACKGROUND);
        countPanel.add(lblCount);

        header.add(countPanel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // TABLE
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Titre", "Auteur", "Année", "Disponibilité"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setBackground(WHITE);
        table.setRowHeight(42);
        table.setSelectionBackground(new Color(225, 237, 255));
        table.setSelectionForeground(NAVY);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(235, 238, 243));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 13));
        tableHeader.setBackground(new Color(239, 243, 249));
        tableHeader.setForeground(NAVY);
        tableHeader.setPreferredSize(new Dimension(0, 45));
        tableHeader.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(WHITE);

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(WHITE);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 234, 240)),
                new EmptyBorder(5, 5, 5, 5)
        ));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        add(tableContainer, BorderLayout.CENTER);

        // BUTTONS
        JPanel btnPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );
        btnPanel.setBackground(BACKGROUND);

        JButton btnCharger = createButton("Actualiser", BLUE);
        JButton btnAjouter = createButton("+ Ajouter livre", GREEN);
        JButton btnModifier = createButton("Modifier", ORANGE);
        JButton btnSupprimer = createButton("Supprimer", RED);

        btnPanel.add(btnCharger);
        btnPanel.add(btnAjouter);
        btnPanel.add(btnModifier);
        btnPanel.add(btnSupprimer);

        add(btnPanel, BorderLayout.SOUTH);

        // ACTIONS
        btnCharger.addActionListener(e -> chargerLivres());
        btnAjouter.addActionListener(e -> afficherFormulaireAjout());
        btnModifier.addActionListener(e -> afficherFormulaireModification());
        btnSupprimer.addActionListener(e -> supprimerLivre());

        chargerLivres();
    }

    // CREATION DES BOUTONS
    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(11, 18, 11, 18));

        // Force l'affichage de la couleur du bouton
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        Color hoverColor = color.darker();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    // CHARGER LES LIVRES
    private void chargerLivres() {

        List<Livre> livres = livreDAO.listerLivres();

        tableModel.setRowCount(0);

        for (Livre l : livres) {
            tableModel.addRow(new Object[]{
                l.getId(),
                l.getTitre(),
                l.getAuteur(),
                l.getAnnee(),
                l.isDisponible() ? "Oui" : "Non"
            });
        }

        updateCount();
    }

    // METTRE A JOUR LE COMPTEUR
    private void updateCount() {
        int count = tableModel.getRowCount();
        lblCount.setText(count + " livre(s)");
    }

    // FORMULAIRE D'AJOUT
    private void afficherFormulaireAjout() {

        JTextField titreField = new JTextField();
        JTextField auteurField = new JTextField();
        JTextField anneeField = new JTextField();

        JCheckBox disponibleCheck = new JCheckBox("Disponible", true);
        disponibleCheck.setBackground(WHITE);
        disponibleCheck.setForeground(TEXT);

        JPanel panel = createFormPanel();

        addFormField(panel, "Titre :", titreField);
        addFormField(panel, "Auteur :", auteurField);
        addFormField(panel, "Année :", anneeField);

        panel.add(new JLabel(""));
        panel.add(disponibleCheck);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Ajouter un livre",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {

                String titre = titreField.getText().trim();
                String auteur = auteurField.getText().trim();

                if (titre.isEmpty() || auteur.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Veuillez remplir le titre et l'auteur.",
                            "Champs obligatoires",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                int annee = Integer.parseInt(anneeField.getText().trim());

                if (annee <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Veuillez saisir une année valide.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                boolean disponible = disponibleCheck.isSelected();

                Livre livre = new Livre(
                        titre, auteur, annee, disponible
                );

                livreDAO.ajouterLivre(livre);
                chargerLivres();

                JOptionPane.showMessageDialog(
                        this,
                        "Livre ajouté avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "L'année doit être un nombre entier.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // FORMULAIRE DE MODIFICATION
    private void afficherFormulaireModification() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez sélectionner un livre à modifier."
            );
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String titre = (String) tableModel.getValueAt(selectedRow, 1);
        String auteur = (String) tableModel.getValueAt(selectedRow, 2);
        int annee = (int) tableModel.getValueAt(selectedRow, 3);
        boolean disponible =
                tableModel.getValueAt(selectedRow, 4).equals("Oui");

        JTextField titreField = new JTextField(titre);
        JTextField auteurField = new JTextField(auteur);
        JTextField anneeField = new JTextField(String.valueOf(annee));

        JCheckBox disponibleCheck = new JCheckBox(
                "Disponible", disponible
        );
        disponibleCheck.setBackground(WHITE);
        disponibleCheck.setForeground(TEXT);

        JPanel panel = createFormPanel();

        addFormField(panel, "Titre :", titreField);
        addFormField(panel, "Auteur :", auteurField);
        addFormField(panel, "Année :", anneeField);

        panel.add(new JLabel(""));
        panel.add(disponibleCheck);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Modifier un livre",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {

                String newTitre = titreField.getText().trim();
                String newAuteur = auteurField.getText().trim();

                if (newTitre.isEmpty() || newAuteur.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Veuillez remplir le titre et l'auteur.",
                            "Champs obligatoires",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                int newAnnee = Integer.parseInt(
                        anneeField.getText().trim()
                );

                if (newAnnee <= 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Veuillez saisir une année valide.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                boolean newDisponible = disponibleCheck.isSelected();

                Livre livre = new Livre(
                        id,
                        newTitre,
                        newAuteur,
                        newAnnee,
                        newDisponible
                );

                livreDAO.modifierLivre(livre);
                chargerLivres();

                JOptionPane.showMessageDialog(
                        this,
                        "Livre modifié avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "L'année doit être un nombre entier.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // SUPPRIMER UN LIVRE
    private void supprimerLivre() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez sélectionner un livre à supprimer."
            );
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Confirmer la suppression du livre sélectionné ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {

                livreDAO.supprimerLivre(id);
                chargerLivres();

                JOptionPane.showMessageDialog(
                        this,
                        "Livre supprimé avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Erreur lors de la suppression : " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // CREER UN PANEL DE FORMULAIRE
    private JPanel createFormPanel() {

        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12));
        panel.setBackground(WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        return panel;
    }

    // AJOUTER UN CHAMP AU FORMULAIRE
    private void addFormField(
            JPanel panel,
            String labelText,
            JTextField field
    ) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(TEXT);

        field.setFont(new Font("SansSerif", Font.PLAIN, 13));

        panel.add(label);
        panel.add(field);
    }
}
