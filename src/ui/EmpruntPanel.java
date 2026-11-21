
package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import dao.EmpruntDAO;
import model.Emprunt;

public class EmpruntPanel extends JPanel {

    private EmpruntDAO empruntDAO;
    private JTable table;
    private DefaultTableModel tableModel;
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

    public EmpruntPanel() {

        empruntDAO = new EmpruntDAO();

        setLayout(new BorderLayout(0, 20));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);

        JLabel lblTitre = new JLabel("Gestion des emprunts");
        lblTitre.setFont(new Font("SansSerif", Font.BOLD, 25));
        lblTitre.setForeground(NAVY);

        JLabel lblSubtitle = new JLabel(
                "Consultez et gérez les emprunts de votre bibliothèque."
        );
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblSubtitle.setForeground(MUTED);

        titlePanel.add(lblTitre);
        titlePanel.add(Box.createVerticalStrut(7));
        titlePanel.add(lblSubtitle);

        header.add(titlePanel, BorderLayout.WEST);

        lblCount = new JLabel("0 emprunt(s)");
        lblCount.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblCount.setForeground(BLUE);

        JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        countPanel.setBackground(BACKGROUND);
        countPanel.add(lblCount);

        header.add(countPanel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // TABLE
        tableModel = new DefaultTableModel(
                new Object[]{
                    "ID", "ID Livre", "ID Adhérent",
                    "Date Emprunt", "Date Retour"
                }, 0
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

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 13));
        tableHeader.setBackground(new Color(239, 243, 249));
        tableHeader.setForeground(NAVY);
        tableHeader.setPreferredSize(new Dimension(0, 45));
        tableHeader.setReorderingAllowed(false);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(
                new Color(230, 234, 240)
        ));
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
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );
        buttonPanel.setBackground(BACKGROUND);

        JButton btnCharger = createButton("Actualiser", BLUE);
        JButton btnAjouter = createButton("+ Ajouter emprunt", GREEN);
        JButton btnModifier = createButton("Modifier", ORANGE);
        JButton btnSupprimer = createButton("Supprimer", RED);

        buttonPanel.add(btnCharger);
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);

        add(buttonPanel, BorderLayout.SOUTH);

        // ACTIONS
        btnCharger.addActionListener(e -> chargerEmprunts());
        btnAjouter.addActionListener(e -> ajouterEmprunt());
        btnModifier.addActionListener(e -> modifierEmprunt());
        btnSupprimer.addActionListener(e -> supprimerEmprunt());

        chargerEmprunts();
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

    // CHARGER LES EMPRUNTS
    private void chargerEmprunts() {

        List<Emprunt> emprunts = empruntDAO.listerEmprunts();

        tableModel.setRowCount(0);

        for (Emprunt e : emprunts) {
            tableModel.addRow(new Object[]{
                e.getId(),
                e.getLivreId(),
                e.getAdherentId(),
                e.getDateEmprunt(),
                e.getDateRetour() != null ? e.getDateRetour() : ""
            });
        }

        updateCount();
    }

    // METTRE A JOUR LE NOMBRE D'EMPRUNTS
    private void updateCount() {
        int count = tableModel.getRowCount();
        lblCount.setText(count + " emprunt(s)");
    }

    // AJOUTER UN EMPRUNT
    private void ajouterEmprunt() {

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 12));

        JTextField livreIdField = new JTextField();
        JTextField adherentIdField = new JTextField();
        JTextField dateEmpruntField = new JTextField();
        JTextField dateRetourField = new JTextField();

        panel.add(new JLabel("ID Livre :"));
        panel.add(livreIdField);

        panel.add(new JLabel("ID Adhérent :"));
        panel.add(adherentIdField);

        panel.add(new JLabel("Date Emprunt (YYYY-MM-DD) :"));
        panel.add(dateEmpruntField);

        panel.add(new JLabel("Date Retour (optionnelle) :"));
        panel.add(dateRetourField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Ajouter un emprunt",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {

                int livreId = Integer.parseInt(livreIdField.getText().trim());
                int adherentId = Integer.parseInt(adherentIdField.getText().trim());

                LocalDate dateEmprunt = LocalDate.parse(
                        dateEmpruntField.getText().trim()
                );

                String dateRetourText = dateRetourField.getText().trim();

                LocalDate dateRetour = dateRetourText.isEmpty()
                        ? null
                        : LocalDate.parse(dateRetourText);

                Emprunt emprunt = new Emprunt(
                        0, livreId, adherentId, dateEmprunt, dateRetour
                );

                empruntDAO.ajouterEmprunt(emprunt);

                chargerEmprunts();

                JOptionPane.showMessageDialog(
                        this,
                        "Emprunt ajouté avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Les IDs doivent être des nombres entiers.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (DateTimeParseException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Les dates doivent être au format YYYY-MM-DD.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Erreur lors de l'ajout : " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // MODIFIER UN EMPRUNT
    private void modifierEmprunt() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez sélectionner un emprunt à modifier."
            );
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int currentLivreId = (int) tableModel.getValueAt(selectedRow, 1);
        int currentAdherentId = (int) tableModel.getValueAt(selectedRow, 2);

        String currentDateEmprunt =
                tableModel.getValueAt(selectedRow, 3).toString();

        String currentDateRetour =
                tableModel.getValueAt(selectedRow, 4).toString();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 12));

        JTextField livreIdField =
                new JTextField(String.valueOf(currentLivreId));

        JTextField adherentIdField =
                new JTextField(String.valueOf(currentAdherentId));

        JTextField dateEmpruntField =
                new JTextField(currentDateEmprunt);

        JTextField dateRetourField =
                new JTextField(currentDateRetour);

        panel.add(new JLabel("ID Livre :"));
        panel.add(livreIdField);

        panel.add(new JLabel("ID Adhérent :"));
        panel.add(adherentIdField);

        panel.add(new JLabel("Date Emprunt (YYYY-MM-DD) :"));
        panel.add(dateEmpruntField);

        panel.add(new JLabel("Date Retour (optionnelle) :"));
        panel.add(dateRetourField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Modifier un emprunt",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {

                int newLivreId =
                        Integer.parseInt(livreIdField.getText().trim());

                int newAdherentId =
                        Integer.parseInt(adherentIdField.getText().trim());

                LocalDate newDateEmprunt = LocalDate.parse(
                        dateEmpruntField.getText().trim()
                );

                String dateRetourText = dateRetourField.getText().trim();

                LocalDate newDateRetour = dateRetourText.isEmpty()
                        ? null
                        : LocalDate.parse(dateRetourText);

                Emprunt emprunt = new Emprunt(
                        id,
                        newLivreId,
                        newAdherentId,
                        newDateEmprunt,
                        newDateRetour
                );

                empruntDAO.modifierEmprunt(emprunt);

                chargerEmprunts();

                JOptionPane.showMessageDialog(
                        this,
                        "Emprunt modifié avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Les IDs doivent être des nombres entiers.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (DateTimeParseException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Les dates doivent être au format YYYY-MM-DD.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Erreur lors de la modification : " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // SUPPRIMER UN EMPRUNT
    private void supprimerEmprunt() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Veuillez sélectionner un emprunt à supprimer."
            );
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Confirmez-vous la suppression de l'emprunt sélectionné ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {

                empruntDAO.supprimerEmprunt(id);

                chargerEmprunts();

                JOptionPane.showMessageDialog(
                        this,
                        "Emprunt supprimé avec succès !",
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
}
