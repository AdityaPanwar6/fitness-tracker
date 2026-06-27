package ui;

import model.User;
import service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * First screen the user sees.
 * Tabs: Login | Register
 * On successful login → calls MainFrame.showMainApp()
 */
public class LoginPanel extends JPanel {

    private final MainFrame mainFrame;
    private final UserService userService = new UserService();

    //LOGIN FIELDS
    private final JTextField loginEmail = new JTextField(20);
    private final JPasswordField loginPassword = new JPasswordField(20);

    //REGISTER FIELDS
    private final JTextField regName = new JTextField(20);
    private final JTextField regEmail = new JTextField(20);
    private final JPasswordField regPassword = new JPasswordField(20);
    private final JTextField regAge = new JTextField(5);
    private final JTextField regHeight = new JTextField(7);
    private final JTextField regWeight = new JTextField(7);
    private final JComboBox<String> regGoal  = new JComboBox<>(new String[]{"Lose Weight", "Gain Muscle", "Maintain Fitness", "Improve Endurance"});

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 60, 30, 60));

        //TITLE
        JLabel title = new JLabel("💪 Fitness Tracker", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        //TABS
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Login",    buildLoginTab());
        tabs.addTab("Register", buildRegisterTab());
        add(tabs, BorderLayout.CENTER);
    }

    //TABS

    //LOGIN TAB
    private JPanel buildLoginTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(loginEmail, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(loginPassword, gbc);

        row++;
        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(52, 152, 219));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.fill  = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> doLogin());
        loginPassword.addActionListener(e -> doLogin());

        return panel;
    }

    //REGISTER TAB
    private JPanel buildRegisterTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        Object[][] fields = {
            {"Name:",regName},
            {"Email:",regEmail},
            {"Password:",regPassword},
            {"Age:",regAge},
            {"Height (cm):", regHeight},
            {"Weight (kg):", regWeight},
            {"Goal:",regGoal}
        };

        for (int i = 0; i < fields.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.gridwidth = 1;
            panel.add(new JLabel((String) fields[i][0]), gbc);
            gbc.gridx = 1;
            panel.add((Component) fields[i][1], gbc);
        }

        JButton registerBtn = new JButton("Create Account");
        styleButton(registerBtn, new Color(46, 204, 113));
        int row = fields.length;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.fill   = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerBtn, gbc);

        registerBtn.addActionListener(e -> doRegister());

        return panel;
    }

    //LOGIN LOGIC
    private void doLogin() {
        String email = loginEmail.getText().trim();
        String password = new String(loginPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter email and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userService.login(email, password);

        if (user != null) {
            Session.setCurrentUser(user);
            mainFrame.showMainApp();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            loginPassword.setText("");
        }
    }

    private void doRegister() {
        try {
            String name = regName.getText().trim();
            String email = regEmail.getText().trim();
            String password = new String(regPassword.getPassword());
            int    age  = Integer.parseInt(regAge.getText().trim());
            double height = Double.parseDouble(regHeight.getText().trim());
            double weight = Double.parseDouble(regWeight.getText().trim());
            String goal = (String) regGoal.getSelectedItem();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name, email, and password are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = new User(name, email, password, age, height, weight, goal);
            userService.registerUser(user);

            JOptionPane.showMessageDialog(this,
                "Account created for " + name + "!\nYou can now log in.",
                "Success", JOptionPane.INFORMATION_MESSAGE);

            regName.setText(""); regEmail.setText(""); regPassword.setText("");
            regAge.setText(""); regHeight.setText(""); regWeight.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid numbers for age, height, and weight.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(150, 35));
    }
}
