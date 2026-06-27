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
 * Create Workout  – duration field + "Start Workout" button
 * Add Exercises   – exercise dropdown, sets/reps/weight fields, "Add" button
 * Workout History – scrollable table of all past workouts
 */
public class WorkoutPanel extends JPanel {

    private final WorkoutService workoutService = new WorkoutService();
    private final ExerciseService exerciseService = new ExerciseService();

    private int activeWorkoutId = -1;
    private final List<WorkoutExercise> stagedExercises = new ArrayList<>();

    private final JTextField durationField = new JTextField(8);
    private JButton startBtn;
    private JPanel section1;

    private JComboBox<Exercise> exerciseCombo;
    private final JTextField setsField = new JTextField(5);
    private final JTextField repsField = new JTextField(5);
    private final JTextField weightField = new JTextField(6);

    private DefaultTableModel stagingModel;
    private DefaultTableModel historyModel;
    private JPanel section2;

    public WorkoutPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(15, 20, 15, 20));

        buildSection1();
        buildSection2();
        buildSection3();

        loadExercisesIntoCombo();
        loadHistory();
    }

    private void buildSection1() {
        section1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        section1.setBorder(new TitledBorder("1. Create New Workout Session"));

        section1.add(new JLabel("Duration (mins):"));
        section1.add(durationField);

        startBtn = new JButton("🏋️ Start Workout");
        startBtn.setBackground(new Color(51, 122, 183));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.addActionListener(e -> startWorkout());
        section1.add(startBtn);

        add(section1);
    }

    private void buildSection2() {
        section2 = new JPanel(new BorderLayout(10, 10));
        section2.setBorder(new TitledBorder("2. Add Exercises Performed"));
        section2.setVisible(false);

        JPanel inputForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        exerciseCombo = new JComboBox<>();
        exerciseCombo.setPreferredSize(new Dimension(200, 25));

        inputForm.add(new JLabel("Exercise:"));
        inputForm.add(exerciseCombo);
        inputForm.add(new JLabel("Sets:"));
        inputForm.add(setsField);
        inputForm.add(new JLabel("Reps:"));
        inputForm.add(repsField);
        inputForm.add(new JLabel("Weight (kg):"));
        inputForm.add(weightField);

        JButton addExBtn = new JButton("➕ Add");
        addExBtn.addActionListener(e -> addExerciseToStaging());
        inputForm.add(addExBtn);

        // Crucial Change: Staging table columns explicitly define Exercise Name now
        String[] columns = {"Exercise Name", "Sets", "Reps", "Weight (kg)"};
        stagingModel = new DefaultTableModel(columns, 0);
        JTable stagingTable = new JTable(stagingModel);

        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton finishBtn = new JButton("✅ Finish and Save Workout");
        finishBtn.setBackground(new Color(92, 184, 92));
        finishBtn.setForeground(Color.WHITE);
        finishBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        finishBtn.setFocusPainted(false);
        finishBtn.addActionListener(e -> finishWorkout());
        bottomBar.add(finishBtn);

        section2.add(inputForm, BorderLayout.NORTH);
        section2.add(new JScrollPane(stagingTable), BorderLayout.CENTER);
        section2.add(bottomBar, BorderLayout.SOUTH);

        add(section2);
    }

    private void buildSection3() {
        JPanel section3 = new JPanel(new BorderLayout());
        section3.setBorder(new TitledBorder("3. Past Workouts Log History"));

        String[] columns = {"Workout ID", "Date Record", "Duration Captured (Minutes)"};
        historyModel = new DefaultTableModel(columns, 0);
        JTable historyTable = new JTable(historyModel);

        section3.add(new JScrollPane(historyTable), BorderLayout.CENTER);
        add(section3);
    }

    private void startWorkout() {
        if (!Session.isLoggedIn()) return;
        try {
            int duration = Integer.parseInt(durationField.getText().trim());
            if (duration <= 0) throw new NumberFormatException();

            Workout w = new Workout(Session.getCurrentUser().getId(), LocalDate.now(), duration);
            activeWorkoutId = workoutService.createWorkout(w);

            if (activeWorkoutId > 0) {
                durationField.setEnabled(false);
                startBtn.setEnabled(false);
                section2.setVisible(true);
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Could not initialize session in backend DB.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of minutes.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addExerciseToStaging() {
        Exercise selectedEx = (Exercise) exerciseCombo.getSelectedItem();
        if (selectedEx == null || selectedEx.getExerciseId() == 0) {
            JOptionPane.showMessageDialog(this, "Please select an exercise from the list.", "Selection Missing", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int sets = Integer.parseInt(setsField.getText().trim());
            int reps = Integer.parseInt(repsField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());

            WorkoutExercise we = new WorkoutExercise(activeWorkoutId, selectedEx.getExerciseId(), sets, reps, weight);
            stagedExercises.add(we);

            //We display the structural String name 'selectedEx.getName()' inside row index 0 instead of the ID
            stagingModel.addRow(new Object[]{
                selectedEx.getName(),
                sets,
                reps,
                weight
            });

            setsField.setText("");
            repsField.setText("");
            weightField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verify sets, reps, and weight format values are valid.", "Input Data Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void finishWorkout() {
        if (stagedExercises.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add at least one exercise row entry before checking out.", "Empty Log", JOptionPane.WARNING_MESSAGE);
            return;
        }

        workoutService.addExercisesToWorkout(activeWorkoutId, stagedExercises);
        JOptionPane.showMessageDialog(this, "Workout session logged successfully!", "Workout Saved", JOptionPane.INFORMATION_MESSAGE);

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
        loadExercisesIntoCombo();
    }
}