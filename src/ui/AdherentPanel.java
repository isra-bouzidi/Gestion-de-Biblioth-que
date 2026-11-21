
package ui;

import dao.AdherentDAO;
import model.Adherent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

public class AdherentPanel extends JPanel {

    private final AdherentDAO adherentDAO = new AdherentDAO();

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField searchField;
    private JLabel countLabel;

    // ================= COLORS BOOKSPACE =================

    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color WHITE = Color.WHITE;
    private final Color NAVY = new Color(25, 39, 65);
    private final Color BLUE = new Color(55, 115, 220);
    private final Color GREEN = new Color(25, 155, 125);
    private final Color ORANGE = new Color(225, 145, 55);
    private final Color RED = new Color(220, 75, 75);
    private final Color TEXT = new Color(45, 55, 72);
    private final Color MUTED = new Color(130, 140, 155);

    // ================= CONSTRUCTOR =================

    public AdherentPanel() {

        setLayout(new BorderLayout(0, 20));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        createHeader();
        createTable();
        createBottomPanel();

        chargerAdherents();
    }

    // ================= HEADER =================

    private void createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BACKGROUND);

        JLabel title = new JLabel("Gestion des adhérents");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Consultez, ajoutez et gérez les membres de votre bibliothèque."
        );

        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(7));
        titlePanel.add(subtitle);

        countLabel = new JLabel("0 adhérent(s)");
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        countLabel.setForeground(BLUE);

        header.add(titlePanel, BorderLayout.WEST);
        header.add(countLabel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
    }

    // ================= TABLE =================

    private void createTable() {

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(BACKGROUND);

        // SEARCH PANEL

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(BACKGROUND);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 42));

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 235)
                        ),
                        new EmptyBorder(5, 12, 5, 12)
                )
        );

        searchField.putClientProperty(
                "JTextField.placeholderText",
                "Rechercher un adhérent..."
        );

        searchPanel.add(searchField, BorderLayout.CENTER);

        // TABLE MODEL

        tableModel = new DefaultTableModel(
                new Object[]{
                    "ID",
                    "NOM",
                    "PRÉNOM",
                    "DATE D'INSCRIPTION"
                },
                0
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
        table.setRowHeight(45);

        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(235, 238, 243));

        table.setSelectionBackground(new Color(225, 237, 255));
        table.setSelectionForeground(NAVY);

        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(0, 1));

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        // TABLE HEADER

        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        table.getTableHeader().setBackground(
                new Color(239, 243, 249)
        );

        table.getTableHeader().setForeground(NAVY);

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 45)
        );

        table.getTableHeader().setReorderingAllowed(false);

        // COLUMN WIDTHS

        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(180);

        // SEARCH FILTER

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        searchField.getDocument().addDocumentListener(
                new DocumentListener() {

            private void filter() {

                String text = searchField.getText().trim();

                if (text.isEmpty()) {

                    sorter.setRowFilter(null);

                } else {

                    sorter.setRowFilter(
                            RowFilter.regexFilter(
                                    "(?i)" + Pattern.quote(text),
                                    1, 2
                            )
                    );
                }

                updateCount();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });

        // TABLE CONTAINER

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 238)
                )
        );

        scrollPane.getViewport().setBackground(WHITE);

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(WHITE);

        tableContainer.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        tableContainer.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(tableContainer, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    // ================= BOTTOM BUTTONS =================

    private void createBottomPanel() {

        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        bottomPanel.setBackground(BACKGROUND);

        JButton btnCharger = createButton(
                "Actualiser",
                BLUE
        );

        JButton btnAjouter = createButton(
                "+ Ajouter un adhérent",
                GREEN
        );

        JButton btnModifier = createButton(
                "Modifier",
                ORANGE
        );

        JButton btnSupprimer = createButton(
                "Supprimer",
                RED
        );

        // BUTTON ACTIONS

        btnCharger.addActionListener(e -> {
            searchField.setText("");
            chargerAdherents();
        });

        btnAjouter.addActionListener(
                e -> afficherFormulaireAjout()
        );

        btnModifier.addActionListener(
                e -> afficherFormulaireModification()
        );

        btnSupprimer.addActionListener(
                e -> supprimerAdherent()
        );

        // ADD BUTTONS

        bottomPanel.add(btnCharger);
        bottomPanel.add(btnAjouter);
        bottomPanel.add(btnModifier);
        bottomPanel.add(btnSupprimer);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ================= BUTTON DESIGN =================

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

        button.setBorder(
                new EmptyBorder(11, 18, 11, 18)
        );

        // Force button color display

        button.setUI(
                new javax.swing.plaf.basic.BasicButtonUI()
        );

        // HOVER EFFECT

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

    // ================= LOAD ADHERENTS =================

    private void chargerAdherents() {

        try {

            List<Adherent> adherents =
                    adherentDAO.listerAdherents();

            tableModel.setRowCount(0);

            if (adherents != null) {

                for (Adherent a : adherents) {

                    tableModel.addRow(new Object[]{
                        a.getId(),
                        a.getNom(),
                        a.getPrenom(),
                        a.getDateInscription()
                    });
                }
            }

            updateCount();

        } catch (Exception e) {

            showError(
                    "Erreur lors du chargement des adhérents : "
                    + e.getMessage()
            );
        }
    }

    // ================= UPDATE COUNT =================

    private void updateCount() {

        int total = tableModel.getRowCount();
        int visible = table.getRowCount();

        countLabel.setText(
                visible + " / " + total + " adhérent(s)"
        );
    }

    // ================= ADD ADHERENT =================

    private void afficherFormulaireAjout() {

        JTextField nomField = new JTextField(20);
        JTextField prenomField = new JTextField(20);

        JTextField dateField = new JTextField(
                LocalDate.now().toString(), 20
        );

        JPanel panel = createFormPanel(
                nomField,
                prenomField,
                dateField
        );

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Ajouter un nouvel adhérent",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();

                if (nom.isEmpty() || prenom.isEmpty()) {

                    throw new IllegalArgumentException(
                            "Veuillez remplir tous les champs."
                    );
                }

                LocalDate date = LocalDate.parse(
                        dateField.getText().trim()
                );

                Adherent adherent = new Adherent(
                        0,
                        nom,
                        prenom,
                        date
                );

                adherentDAO.ajouterAdherent(adherent);

                chargerAdherents();

                JOptionPane.showMessageDialog(
                        this,
                        "L'adhérent a été ajouté avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (DateTimeParseException e) {

                showError(
                        "Format de date invalide. Utilisez YYYY-MM-DD."
                );

            } catch (IllegalArgumentException e) {

                showError(e.getMessage());

            } catch (Exception e) {

                showError(
                        "Erreur lors de l'ajout : " + e.getMessage()
                );
            }
        }
    }

    // ================= EDIT ADHERENT =================

    private void afficherFormulaireModification() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {

            showError("Veuillez sélectionner un adhérent.");
            return;
        }

        // Convert selected view row into model row

        int modelRow = table.convertRowIndexToModel(selectedRow);

        int id = (int) tableModel.getValueAt(modelRow, 0);

        String nom = String.valueOf(
                tableModel.getValueAt(modelRow, 1)
        );

        String prenom = String.valueOf(
                tableModel.getValueAt(modelRow, 2)
        );

        String date = String.valueOf(
                tableModel.getValueAt(modelRow, 3)
        );

        JTextField nomField = new JTextField(nom, 20);
        JTextField prenomField = new JTextField(prenom, 20);
        JTextField dateField = new JTextField(date, 20);

        JPanel panel = createFormPanel(
                nomField,
                prenomField,
                dateField
        );

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Modifier un adhérent",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

                String newNom = nomField.getText().trim();
                String newPrenom = prenomField.getText().trim();

                if (newNom.isEmpty() || newPrenom.isEmpty()) {

                    throw new IllegalArgumentException(
                            "Veuillez remplir tous les champs."
                    );
                }

                LocalDate newDate = LocalDate.parse(
                        dateField.getText().trim()
                );

                Adherent adherent = new Adherent(
                        id,
                        newNom,
                        newPrenom,
                        newDate
                );

                adherentDAO.modifierAdherent(adherent);

                chargerAdherents();

                JOptionPane.showMessageDialog(
                        this,
                        "L'adhérent a été modifié avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (DateTimeParseException e) {

                showError(
                        "Format de date invalide. Utilisez YYYY-MM-DD."
                );

            } catch (IllegalArgumentException e) {

                showError(e.getMessage());

            } catch (Exception e) {

                showError(
                        "Erreur lors de la modification : "
                        + e.getMessage()
                );
            }
        }
    }

    // ================= FORM DESIGN =================

    private JPanel createFormPanel(
            JTextField nomField,
            JTextField prenomField,
            JTextField dateField
    ) {

        JPanel panel = new JPanel(
                new GridLayout(0, 1, 5, 7)
        );

        panel.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        panel.setPreferredSize(
                new Dimension(330, 230)
        );

        panel.add(new JLabel("Nom :"));
        panel.add(nomField);

        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);

        panel.add(
                new JLabel("Date d'inscription (YYYY-MM-DD) :")
        );

        panel.add(dateField);

        return panel;
    }

    // ================= DELETE ADHERENT =================

    private void supprimerAdherent() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {

            showError(
                    "Veuillez sélectionner un adhérent à supprimer."
            );

            return;
        }

        // Convert selected view row into model row

        int modelRow = table.convertRowIndexToModel(selectedRow);

        int id = (int) tableModel.getValueAt(modelRow, 0);

        String nom = String.valueOf(
                tableModel.getValueAt(modelRow, 1)
        );

        String prenom = String.valueOf(
                tableModel.getValueAt(modelRow, 2)
        );

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment supprimer "
                + prenom + " " + nom + " ?",
                "Confirmer la suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {

            try {

                adherentDAO.supprimerAdherent(id);

                chargerAdherents();

                JOptionPane.showMessageDialog(
                        this,
                        "L'adhérent a été supprimé avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception e) {

                showError(
                        "Erreur lors de la suppression : "
                        + e.getMessage()
                );
            }
        }
    }

    // ================= ERROR MESSAGE =================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Attention",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
