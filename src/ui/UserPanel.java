package ui;

import model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Shows the currently logged-in user's profile information.
 * Since registration happens on the LoginPanel, this is a read-only view.
 * Fix: Added revalidate() and repaint() to ensure data populates correctly on tab switch.
 */
public class UserPanel extends JPanel {

    public UserPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));
        refresh();
    }

    public void refresh() {
        removeAll();

        JLabel title = new JLabel("My Profile", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        User user = Session.getCurrentUser();
        if (user == null) {
            add(new JLabel("Not logged in. Please restart and authenticate.", SwingConstants.CENTER), BorderLayout.CENTER);
            revalidate();
            repaint();
            return;
        }

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        Object[][] rows = {
            {"User ID:", String.valueOf(user.getId())},
            {"Name:", user.getName()},
            {"Email:", user.getEmail()},
            {"Age:", user.getAge() + " years"},
            {"Height:", user.getHeight() + " cm"},
            {"Weight:", user.getWeight() + " kg"},
            {"Goal:", user.getGoal()}
        };

        for (int i = 0; i < rows.length; i++) {
            JLabel keyLabel = new JLabel((String) rows[i][0]);
            keyLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

            JLabel valLabel = new JLabel(String.valueOf(rows[i][1]));
            valLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

            gbc.gridx = 0; gbc.gridy = i;
            infoPanel.add(keyLabel, gbc);

            gbc.gridx = 1;
            infoPanel.add(valLabel, gbc);
        }

        add(new JScrollPane(infoPanel), BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}