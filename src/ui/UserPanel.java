package ui;

import model.User;
import service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Displays and allows updates to the currently logged-in user's profile information.
 * Dynamically synchronizes updates back to the persistent DB and current memory Session wrapper.
 */
public class UserPanel extends JPanel{

    private final UserService userService = new UserService();

    private final JTextField nameField = new JTextField(15);
    private final JTextField ageField = new JTextField(5);
    private final JTextField heightField = new JTextField(7);
    private final JTextField weightField = new JTextField(7);
    private final JComboBox<String> goalCombo = new JComboBox<>(new String[]{
            "Lose Weight", "Gain Muscle", "Maintain Fitness", "Improve Endurance"
    });

    public UserPanel(){
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(25, 40, 25, 40));
        refresh();
    }

    public void refresh(){
        removeAll();

        JLabel title = new JLabel("👤 User Profile", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(0, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        User user = Session.getCurrentUser();
        if (user == null) {
            add(new JLabel("No active session tracked. Please log in.", SwingConstants.CENTER), BorderLayout.CENTER);
            return;
        }

        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBorder(new TitledBorder("Account Identity & Physical Metrics"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField.setText(user.getName());
        ageField.setText(String.valueOf(user.getAge()));
        heightField.setText(String.valueOf(user.getHeight()));
        weightField.setText(String.valueOf(user.getWeight()));
        goalCombo.setSelectedItem(user.getGoal());

        gbc.gridx = 0; gbc.gridy = 0;
        formContainer.add(new JLabel("Internal System ID:"), gbc);
        gbc.gridx = 1;
        JLabel idLabel = new JLabel(String.valueOf(user.getId()));
        idLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        formContainer.add(idLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formContainer.add(new JLabel("Full Display Name:"), gbc);
        gbc.gridx = 1;
        formContainer.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formContainer.add(new JLabel("Age (Years):"), gbc);
        gbc.gridx = 1;
        formContainer.add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formContainer.add(new JLabel("Height (cm):"), gbc);
        gbc.gridx = 1;
        formContainer.add(heightField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formContainer.add(new JLabel("Weight (kg):"), gbc);
        gbc.gridx = 1;
        formContainer.add(weightField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formContainer.add(new JLabel("Fitness Focus Goal:"), gbc);
        gbc.gridx = 1;
        formContainer.add(goalCombo, gbc);

        add(formContainer, BorderLayout.CENTER);

        JButton saveBtn = new JButton("💾 Commit Profile Changes");
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        saveBtn.setBackground(new Color(51, 122, 183));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> saveProfileChanges());
        
        add(saveBtn, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void saveProfileChanges(){
        User user = Session.getCurrentUser();
        if (user == null) return;

        try {
            String updatedName = nameField.getText().trim();
            int updatedAge = Integer.parseInt(ageField.getText().trim());
            double updatedHeight = Double.parseDouble(heightField.getText().trim());
            double updatedWeight = Double.parseDouble(weightField.getText().trim());
            String updatedGoal = (String) goalCombo.getSelectedItem();

            if (updatedName.isEmpty()){
                JOptionPane.showMessageDialog(this, "Profile name entry cannot be blank.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (updatedAge <= 0 || updatedHeight <= 0 || updatedWeight <= 0){
                JOptionPane.showMessageDialog(this, "Metrics must be positive quantities.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            user.setName(updatedName);
            user.setAge(updatedAge);
            user.setHeight(updatedHeight);
            user.setWeight(updatedWeight);
            user.setGoal(updatedGoal);

            userService.updateUserProfile(user);

            JOptionPane.showMessageDialog(this, "Profile baseline records updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
          
            Window rootFrame = SwingUtilities.getWindowAncestor(this);
            if (rootFrame instanceof MainFrame) {
                ((MainFrame) rootFrame).showMainApp();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please verify all numerical values for age, height, and weight are valid integers or decimals.", "Data Format Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}