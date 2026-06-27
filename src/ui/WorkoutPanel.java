package ui;

import model.Exercise;
import model.Workout;
import model.WorkoutExercise;
import service.ExerciseService;
import service.WorkoutService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Three sections stacked vertically:
 *
 * Create Workout  – duration field + "Start Workout" button
 *                      On success: section 2 appears and section 1 is locked
 *
 * Add Exercises   – exercise dropdown, sets/reps/weight fields, "Add" button
 *                      Each added exercise shows in a small staging table
 *                      "Finish Workout" saves all exercises and resets the panel
 *
 * Workout History – scrollable table of all past workouts
 */
public class WorkoutPanel extends JPanel{

    private final WorkoutService workoutService = new WorkoutService();
    private final ExerciseService exerciseService = new ExerciseService();

    private int activeWorkoutId  = -1;
    private final List<WorkoutExercise> stagedExercises = new ArrayList<>();

    private final JTextField durationField  = new JTextField(8);
    private JButton startBtn;
    private JPanel section1;

    private JComboBox<Exercise> exerciseCombo;
    private final JTextField setsField = new JTextField(5);
    private final JTextField repsField = new JTextField(5);
    private final JTextField weightField = new JTextField(5);
    private DefaultTableModel stagingModel;
    private JPanel section2;
    private JLabel workoutIdLabel;

    private DefaultTableModel historyModel;

    public WorkoutPanel(){
        setLayout(new BorderLayout(0, 8));
        setBorder(new EmptyBorder(12, 18, 12, 18));

        JPanel topArea = new JPanel();
        topArea.setLayout(new BoxLayout(topArea, BoxLayout.Y_AXIS));

        section1 = buildSection1();
        section2 = buildSection2();
        section2.setVisible(false);

        topArea.add(section1);
        topArea.add(Box.createVerticalStrut(8));
        topArea.add(section2);

        add(topArea, BorderLayout.NORTH);
        add(buildSection3(), BorderLayout.CENTER);

        loadHistory();
    }

   //SECTION 1 CREATE WORKOUT
    private JPanel buildSection1() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panel.setBorder(new TitledBorder("1.Create Workout Session"));

        panel.add(new JLabel("Duration (minutes):"));
        panel.add(durationField);

        startBtn = new JButton("Start Workout");
        startBtn.setBackground(new Color(52, 152, 219));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setBorderPainted(false);
        panel.add(startBtn);

        startBtn.addActionListener(e -> startWorkout());
        return panel;
    }

    //SECTION 2 ADD EXS TO WORKOUT
    private JPanel buildSection2() {
        JPanel outer = new JPanel(new BorderLayout(0, 6));
        outer.setBorder(new TitledBorder("2.Add Exercises to Workout"));

        JPanel inputRow = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 8, 4, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        workoutIdLabel = new JLabel("Workout ID: —");
        workoutIdLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        workoutIdLabel.setForeground(new Color(52, 152, 219));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        inputRow.add(workoutIdLabel, gbc);

        exerciseCombo = new JComboBox<>();
        exerciseCombo.setPreferredSize(new Dimension(220, 26));

        gbc.gridwidth = 1; gbc.gridy = 1;
        gbc.gridx = 0; inputRow.add(new JLabel("Exercise:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        inputRow.add(exerciseCombo, gbc);

        gbc.gridwidth = 1; gbc.gridy = 2;
        gbc.gridx = 0; inputRow.add(new JLabel("Sets:"), gbc);
        gbc.gridx = 1; inputRow.add(setsField, gbc);
        gbc.gridx = 2; inputRow.add(new JLabel("Reps:"), gbc);
        gbc.gridx = 3; inputRow.add(repsField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0; inputRow.add(new JLabel("Weight (kg):"), gbc);
        gbc.gridx = 1; inputRow.add(weightField, gbc);

        JButton addBtn = new JButton("Add Exercise");
        addBtn.setBackground(new Color(39, 174, 96));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);
        gbc.gridx = 2; gbc.gridwidth = 2;
        inputRow.add(addBtn, gbc);

        addBtn.addActionListener(e -> addExerciseToStaging());

        String[] cols = {"Exercise", "Sets", "Reps", "Weight (kg)"};
        stagingModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        JTable stagingTable = new JTable(stagingModel);
        stagingTable.setRowHeight(22);
        stagingTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        JScrollPane stagingScroll = new JScrollPane(stagingTable);
        stagingScroll.setPreferredSize(new Dimension(0, 110));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 4));

        JButton removeLastBtn = new JButton("Remove Last");
        removeLastBtn.addActionListener(e -> removeLastStaged());

        JButton finishBtn = new JButton("✔ Finish Workout");
        finishBtn.setBackground(new Color(231, 76, 60));
        finishBtn.setForeground(Color.WHITE);
        finishBtn.setFocusPainted(false);
        finishBtn.setBorderPainted(false);
        finishBtn.addActionListener(e -> finishWorkout());

        btnRow.add(removeLastBtn);
        btnRow.add(finishBtn);

        outer.add(inputRow, BorderLayout.NORTH);
        outer.add(stagingScroll, BorderLayout.CENTER);
        outer.add(btnRow, BorderLayout.SOUTH);
        return outer;
    }

    //SECTION 3
    private JPanel buildSection3(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("3.Workout History"));

        String[] cols = {"Workout ID", "Date", "Duration (min)"};
        historyModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable historyTable = new JTable(historyModel);
        historyTable.setRowHeight(24);
        historyTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadHistory());

        panel.add(new JScrollPane(historyTable), BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);
        return panel;
    }

    //LOGIC

    /** Step 1 — create the workout row in DB, unlock section 2. */
    private void startWorkout(){
        if (!Session.isLoggedIn()){
            JOptionPane.showMessageDialog(this, "Please log in first.");
            return;
        }
        try {
            int duration = Integer.parseInt(durationField.getText().trim());
            if (duration <= 0) throw new NumberFormatException();

            Workout w = new Workout();
            w.setUserId(Session.getCurrentUser().getId());
            w.setDuration(duration);
            w.setDate(LocalDate.now());

            activeWorkoutId = workoutService.createWorkout(w);
            if (activeWorkoutId == -1) {
                JOptionPane.showMessageDialog(this, "Failed to create workout. Check DB connection.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            workoutIdLabel.setText("Workout ID: " + activeWorkoutId + "  |  Date: " + LocalDate.now());
            durationField.setEnabled(false);
            startBtn.setEnabled(false);

            loadExercisesIntoCombo();

            section2.setVisible(true);
            revalidate();
            repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid positive number for duration.", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Step 2 — stage one exercise (shown in the table, not yet saved). */
    private void addExerciseToStaging(){
        try {
            Exercise selected = (Exercise) exerciseCombo.getSelectedItem();
            if (selected == null || selected.getExerciseId() <= 0) {
                JOptionPane.showMessageDialog(this,
                    "No valid exercise selected. Populate the exercises table first.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int    sets   = Integer.parseInt(setsField.getText().trim());
            int    reps   = Integer.parseInt(repsField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());

            if (sets <= 0 || reps <= 0 || weight < 0) throw new NumberFormatException();

            WorkoutExercise we = new WorkoutExercise(activeWorkoutId,
                selected.getExerciseId(), sets, reps, weight);
            stagedExercises.add(we);

            stagingModel.addRow(new Object[]{
                selected.getName(), sets, reps, weight
            });

            setsField.setText("");
            repsField.setText("");
            weightField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Enter valid positive numbers for sets, reps, and weight.", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeLastStaged(){
        int rows = stagingModel.getRowCount();
        if (rows == 0) {
            JOptionPane.showMessageDialog(this, "Nothing to remove.");
            return;
        }
        stagingModel.removeRow(rows - 1);
        stagedExercises.remove(stagedExercises.size() - 1);
    }

    /** Step 3 — persist all staged exercises, reset UI to ready state. */
    private void finishWorkout(){
        if (stagedExercises.isEmpty()) {
            int choice = JOptionPane.showConfirmDialog(this,
                "No exercises added. Finish workout with no exercises?",
                "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) return;
        }

        if (!stagedExercises.isEmpty()) {
            workoutService.addExercisesToWorkout(activeWorkoutId, stagedExercises);
        }

        JOptionPane.showMessageDialog(this,
            "Workout #" + activeWorkoutId + " saved with " + stagedExercises.size() + " exercise(s)!",
            "Workout Complete", JOptionPane.INFORMATION_MESSAGE);

        resetToReady();
        loadHistory();
    }

    private void resetToReady() {
        activeWorkoutId = -1;
        stagedExercises.clear();
        stagingModel.setRowCount(0);

        durationField.setText("");
        durationField.setEnabled(true);
        startBtn.setEnabled(true);

        setsField.setText("");
        repsField.setText("");
        weightField.setText("");

        section2.setVisible(false);
        revalidate();
        repaint();
    }

    private void loadExercisesIntoCombo() {
        exerciseCombo.removeAllItems();
        List<Exercise> list = exerciseService.getAllExercises();
        for (Exercise ex : list) {
            exerciseCombo.addItem(ex);
        }
        if (list.isEmpty()) {
            exerciseCombo.addItem(new Exercise(0, "No exercises — run seed SQL", "", "", ""));
        }
    }

    private void loadHistory() {
        historyModel.setRowCount(0);
        if (!Session.isLoggedIn()) return;

        List<Workout> workouts = workoutService.getUserWorkouts(Session.getCurrentUser().getId());
        for (Workout w : workouts) {
            historyModel.addRow(new Object[]{
                w.getWorkoutId(),
                w.getDate().toString(),
                w.getDuration()
            });
        }
    }

    public void refresh() {
        loadHistory();
    }
}