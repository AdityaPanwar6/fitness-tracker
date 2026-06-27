package ui;

import db.DBConnection;
import service.WorkoutService;

import javax.swing.*;
import java.awt.*;

/**
 * The root window.
 *
 * Flow:
 * 1. App starts -> showLoginScreen()
 * 2. User logs in -> Session.setCurrentUser() -> showMainApp()
 * 3. User clicks Logout -> Session.logout() -> showLoginScreen()
 */
public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel root = new JPanel(cardLayout);

    private LoginPanel loginPanel;
    private UserPanel userPanel;
    private WorkoutPanel workoutPanel;
    private ProgressPanel progressPanel;
    private RecordPanel recordPanel;
    private BmiPanel bmiPanel; 

    private JLabel welcomeLabel;
    private final WorkoutService workoutService = new WorkoutService(); 

    public MainFrame(){
        setTitle("Fitness Tracker Dashboard");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 550));

        addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent e){
                DBConnection.closeConnection();
                dispose();
                System.exit(0);
            }
        });

        loginPanel = new LoginPanel(this);
        userPanel = new UserPanel();
        workoutPanel = new WorkoutPanel();
        progressPanel = new ProgressPanel();
        recordPanel = new RecordPanel();
        bmiPanel = new BmiPanel();

        root.add(loginPanel, "LOGIN");
        root.add(buildMainAppPanel(), "MAIN");
        
        add(root);
        showLoginScreen();
    }

    private JPanel buildMainAppPanel(){
        JPanel main = new JPanel(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(43, 62, 80));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        welcomeLabel = new JLabel("Welcome! 💪");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        welcomeLabel.setForeground(Color.WHITE);

        JButton logoutBtn = new JButton("🚪 Logout");
        logoutBtn.setBackground(new Color(217, 83, 79));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> doLogout());

        topBar.add(welcomeLabel, BorderLayout.WEST);
        topBar.add(logoutBtn, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 13));

        tabs.addTab("👤 Profile", userPanel);
        tabs.addTab("🏋️ Workouts", workoutPanel);
        tabs.addTab("📈 Progress", progressPanel);
        tabs.addTab("🏆 Records",  recordPanel);
        tabs.addTab("⚖️ BMI Gauge", bmiPanel);

        tabs.addChangeListener(e -> {
            int idx = tabs.getSelectedIndex();
            switch (idx) {
                case 0 -> userPanel.refresh();
                case 1 -> workoutPanel.refresh();
                case 2 -> progressPanel.refresh();
                case 3 -> recordPanel.refresh();
                case 4 -> bmiPanel.refresh();
            }
        });

        main.add(topBar, BorderLayout.NORTH);
        main.add(tabs, BorderLayout.CENTER);
        return main;
    }

    // NAVIGATION METHODS
    public void showMainApp() {
        if (Session.isLoggedIn()) {
            int streakValue = workoutService.getUserStreak(Session.getCurrentUser().getId());
            welcomeLabel.setText("Welcome, " + Session.getCurrentUser().getName() + "! 💪 | 🔥 Streak: " + streakValue + " Days");
        }
        
        userPanel.refresh();
        workoutPanel.refresh();
        progressPanel.refresh();
        recordPanel.refresh();
        bmiPanel.refresh();
        
        cardLayout.show(root, "MAIN");
    }

    private void showLoginScreen() {
        cardLayout.show(root, "LOGIN");
    }

    private void doLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to log out of your session?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.logout();
            showLoginScreen();
        }
    }
}