package ui;

import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Shows the currently logged-in user's profile information.
 * Since registration happens on the LoginPanel, this is a read-only view.
 */
public class UserPanel extends JPanel{

    public UserPanel(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 40, 20, 40));
        refresh();
    }

    public void refresh(){
        removeAll();

        JLabel title = new JLabel("My Profile", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        User user = Session.getCurrentUser();
        if (user == null){
            add(new JLabel("Not logged in.", SwingConstants.CENTER), BorderLayout.CENTER);
            return;
        }

        JPanel info = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Object[][] rows = {
            {"User ID:", user.getId()},
            {"Name:", user.getName()},
            {"Email:", user.getEmail()},
            {"Age:", user.getAge() + " years"},
            {"Height:", user.getHeight() + " cm"},
            {"Weight:", user.getWeight() + " kg"},
            {"Goal:", user.getGoal()},
        };

        for (int i = 0; i < rows.length; i++){
            JLabel key = new JLabel((String) rows[i][0]);
            key.setFont(new Font("SansSerif", Font.BOLD, 13));

            JLabel val = new JLabel(String.valueOf(rows[i][1]));
            val.setFont(new Font("SansSerif", Font.PLAIN, 13));

            gbc.gridx = 0; gbc.gridy = i;
            info.add(key, gbc);
            gbc.gridx = 1;
            info.add(val, gbc);
        }

        add(info, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}