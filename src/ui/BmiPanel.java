package ui;

import model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Calculates and visualizes the user's Body Mass Index (BMI).
 * Automatically initializes using profile data, and allows real-time interactive tuning.
 */
public class BmiPanel extends JPanel {

    private final JTextField heightField = new JTextField(10);
    private final JTextField weightField = new JTextField(10);

    private final JLabel scoreLabel = new JLabel("0.0", SwingConstants.CENTER);
    private final JLabel categoryLabel = new JLabel("Status: Unknown", SwingConstants.CENTER);
    private final JProgressBar bmiGauge = new JProgressBar(15, 40);

    public BmiPanel() {
        setLayout(new BorderLayout(0, 15));
        setBorder(new EmptyBorder(20, 30, 20, 30));

        // Header Title
        JLabel titleLabel = new JLabel("⚖️ Real-Time BMI Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        // Center Panel splitting Inputs and Output Gauge
        JPanel mainContent = new JPanel(new GridLayout(2, 1, 0, 15));

        // 1. Interactive Inputs Form
        JPanel inputForm = new JPanel(new GridBagLayout());
        inputForm.setBorder(new TitledBorder("Tune Your Current Metrics"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        inputForm.add(new JLabel("Height (cm):"), gbc);
        gbc.gridx = 1;
        inputForm.add(heightField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputForm.add(new JLabel("Weight (kg):"), gbc);
        gbc.gridx = 1;
        inputForm.add(weightField, gbc);

        mainContent.add(inputForm);

        // 2. Output Visualization Gauge Display Card
        JPanel displayCard = new JPanel(new GridLayout(3, 1, 5, 5));
        displayCard.setBorder(new TitledBorder("Calculated Body Mass Index"));

        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 44));
        scoreLabel.setForeground(new Color(51, 122, 183));
        categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        bmiGauge.setStringPainted(true);
        bmiGauge.setPreferredSize(new Dimension(300, 22));

        displayCard.add(scoreLabel);
        displayCard.add(categoryLabel);
        displayCard.add(bmiGauge);

        mainContent.add(displayCard);
        add(mainContent, BorderLayout.CENTER);

        // 3. Reset Option Bottom Button
        JButton resetBtn = new JButton("🔄 Reset to Base Profile Metrics");
        resetBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        resetBtn.setBackground(new Color(92, 184, 92));
        resetBtn.setForeground(Color.WHITE);
        resetBtn.setFocusPainted(false);
        resetBtn.addActionListener(e -> loadProfileValues());
        add(resetBtn, BorderLayout.SOUTH);

        // Attach dynamic live updater document listening hooks to input boxes
        DocumentListener liveCalculationListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { calculateLiveBmi(); }
            @Override public void removeUpdate(DocumentEvent e) { calculateLiveBmi(); }
            @Override public void changedUpdate(DocumentEvent e) { calculateLiveBmi(); }
        };
        heightField.getDocument().addDocumentListener(liveCalculationListener);
        weightField.getDocument().addDocumentListener(liveCalculationListener);

        // Bootstraps default measurements
        loadProfileValues();
    }

    /**
     * Resets input fields back to the data values inside the database Session payload.
     */
    private void loadProfileValues() {
        if (!Session.isLoggedIn()) {
            heightField.setText("");
            weightField.setText("");
            return;
        }
        User user = Session.getCurrentUser();
        heightField.setText(String.valueOf(user.getHeight()));
        weightField.setText(String.valueOf(user.getWeight()));
        calculateLiveBmi();
    }

    /**
     * Grabs current string states out of input forms to run live mathematical formatting.
     */
    private void calculateLiveBmi() {
        try {
            String hStr = heightField.getText().trim();
            String wStr = weightField.getText().trim();

            if (hStr.isEmpty() || wStr.isEmpty()) {
                clearDisplay("Enter metrics...");
                return;
            }

            double heightCm = Double.parseDouble(hStr);
            double weight = Double.parseDouble(wStr);

            if (heightCm <= 0 || weight <= 0) {
                clearDisplay("Metrics must be > 0");
                return;
            }

            double heightMeters = heightCm / 100.0;
            double bmi = weight / (heightMeters * heightMeters);

            // Redraw visual panel components matching new input score limits
            scoreLabel.setText(String.format("%.1f", bmi));
            bmiGauge.setValue((int) bmi);

            if (bmi < 18.5) {
                categoryLabel.setText("Status: Underweight 🟡");
                categoryLabel.setForeground(new Color(240, 173, 78));
            } else if (bmi >= 18.5 && bmi < 25.0) {
                categoryLabel.setText("Status: Normal Weight (Healthy) 🟢");
                categoryLabel.setForeground(new Color(92, 184, 92));
            } else if (bmi >= 25.0 && bmi < 30.0) {
                categoryLabel.setText("Status: Overweight 🟡");
                categoryLabel.setForeground(new Color(240, 173, 78));
            } else {
                categoryLabel.setText("Status: Obese Class Range 🔴");
                categoryLabel.setForeground(new Color(217, 83, 79));
            }

        } catch (NumberFormatException ex) {
            clearDisplay("Invalid entry format");
        }
    }

    private void clearDisplay(String message) {
        scoreLabel.setText("0.0");
        categoryLabel.setText("Status: " + message);
        categoryLabel.setForeground(Color.GRAY);
        bmiGauge.setValue(15);
    }

 
    public void refresh() {
        loadProfileValues();
    }
}