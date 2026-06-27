package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;

/**
 * The root window.
 *
 * Flow:
 *   1. App starts → showLoginScreen()
 *   2. User logs in → Session.setCurrentUser() → showMainApp()
 *   3. User clicks Logout → Session.logout() → showLoginScreen()
 */
public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel root = new JPanel(cardLayout);

    private LoginPanel loginPanel;
    private UserPanel userPanel;
    private WorkoutPanel workoutPanel;
    private ProgressPanel progressPanel;
    private RecordPanel recordPanel;

    private JLabel welcomeLabel;

    public MainFrame(){
        setTitle("Fitness Tracker");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(720, 480));

        addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent e){
                DBConnection.closeConnection();
                dispose();
                System.exit(0);
            }
        });

        buildPanels();
        add(root);

        showLoginScreen();
    }

    private void buildPanels(){
        loginPanel = new LoginPanel(this);
        userPanel = new UserPanel();
        workoutPanel = new WorkoutPanel();
        progressPanel = new ProgressPanel();
        recordPanel = new RecordPanel();

        root.add(loginPanel, "LOGIN");
        root.add(buildMainApp(), "MAIN");
    }

    private JPanel buildMainApp() {
        JPanel main = new JPanel(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(44, 62, 80));
        topBar.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));

        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        topBar.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.addActionListener(e -> doLogout());
        topBar.add(logoutBtn, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabs.addTab("👤 Profile",  userPanel);
        tabs.addTab("🏋 Workouts", workoutPanel);
        tabs.addTab("📈 Progress", progressPanel);
        tabs.addTab("🏆 Records",  recordPanel);

        tabs.addChangeListener(e -> {
            int idx = tabs.getSelectedIndex();
            switch (idx) {
                case 0 -> userPanel.refresh();
                case 1 -> workoutPanel.refresh();
                case 2 -> progressPanel.refresh();
                case 3 -> recordPanel.refresh();
            }
        });

        main.add(topBar, BorderLayout.NORTH);
        main.add(tabs, BorderLayout.CENTER);
        return main;
    }

    //NAVIGATION
    public void showMainApp() {
        welcomeLabel.setText("Welcome, " + Session.getCurrentUser().getName() + "! 💪");
        userPanel.refresh();
        workoutPanel.refresh();
        progressPanel.refresh();
        recordPanel.refresh();
        cardLayout.show(root, "MAIN");
    }

    private void showLoginScreen() {
        cardLayout.show(root, "LOGIN");
    }

    private void doLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Log out?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.logout();
            showLoginScreen();
        }
    }

    //MAIN METHOD
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}