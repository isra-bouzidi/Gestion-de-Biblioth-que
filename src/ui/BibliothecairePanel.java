
package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BibliothecairePanel extends JPanel {

    private final Color BACKGROUND = new Color(245, 247, 251);
    private final Color DARK = new Color(45, 55, 72);
    private final Color GREY = new Color(130, 140, 155);

    public BibliothecairePanel() {

        setLayout(new BorderLayout(0, 20));
        setBackground(BACKGROUND);

        setBorder(
            new EmptyBorder(25, 30, 25, 30)
        );

        // Header
        JPanel header = new JPanel();
        header.setLayout(
            new BoxLayout(header, BoxLayout.Y_AXIS)
        );

        header.setBackground(BACKGROUND);

        JLabel title = new JLabel(
            "Espace Bibliothécaire"
        );

        title.setFont(
            new Font("SansSerif", Font.BOLD, 25)
        );

        title.setForeground(DARK);

        JLabel subtitle = new JLabel(
            "Gérez les opérations d'emprunt de votre bibliothèque."
        );

        subtitle.setFont(
            new Font("SansSerif", Font.PLAIN, 13)
        );

        subtitle.setForeground(GREY);

        header.add(title);
        header.add(Box.createVerticalStrut(7));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // Emprunts
        add(new EmpruntPanel(), BorderLayout.CENTER);
    }
}
