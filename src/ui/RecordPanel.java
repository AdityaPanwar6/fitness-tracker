package ui;

import model.Exercise;
import model.PersonalRecord;
import service.ExerciseService;
import service.RecordService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Two sections:
 *   Top    – form to log a personal record (exercise from dropdown)
 *   Bottom – table showing records with EXERCISE NAME (not ID) + Delete button
 */
public class RecordPanel extends JPanel{
    private JComboBox<Exercise> exerciseCombo = new JComboBox<>();
    private final JTextField maxWeightField = new JTextField(8);
    private final JTextField maxRepsField = new JTextField(8);

    private DefaultTableModel tableModel;
    private JTable table;

    private final RecordService recordService = new RecordService();
    private final ExerciseService exerciseService = new ExerciseService();

    public RecordPanel(){
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(15, 20, 15, 20));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);

        loadExercises();
        loadRecords();
    }

    //FORM
    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Log Personal Record"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        exerciseCombo.setPreferredSize(new Dimension(240, 26));

        String[] labels = {"Exercise:", "Max Weight (kg):", "Max Reps:"};
        Component[] comps = {exerciseCombo, maxWeightField, maxRepsField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            panel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            panel.add(comps[i], gbc);
        }

        JButton saveBtn = new JButton("Save Record");
        saveBtn.setBackground(new Color(155, 89, 182));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);

        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        gbc.fill   = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveBtn, gbc);

        saveBtn.addActionListener(e -> saveRecord());
        return panel;
    }

    //TABLE
    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("My Personal Records"));

        //"Exercise Name" instead of "Exercise ID"
        String[] cols = {"Record ID", "Exercise", "Max Weight (kg)", "Max Reps", "Date"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("Refresh");
        JButton deleteBtn  = new JButton("Delete Selected");
        deleteBtn.setBackground(new Color(231, 76, 60));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorderPainted(false);

        refreshBtn.addActionListener(e -> loadRecords());
        deleteBtn.addActionListener(e -> deleteSelected());

        btnRow.add(refreshBtn);
        btnRow.add(deleteBtn);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnRow,                 BorderLayout.SOUTH);
        return panel;
    }

    //LOGIC
    private void loadExercises() {
        exerciseCombo.removeAllItems();
        List<Exercise> exercises = exerciseService.getAllExercises();
        for (Exercise ex : exercises) {
            exerciseCombo.addItem(ex);
        }
        if (exercises.isEmpty()) {
            exerciseCombo.addItem(new Exercise(0, "No exercises in DB — run seed SQL", "", "", ""));
        }
    }

    private void saveRecord(){
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(this, "Please log in first.");
            return;
        }
        try {
            Exercise selected = (Exercise) exerciseCombo.getSelectedItem();
            if (selected == null || selected.getExerciseId() <= 0) {
                JOptionPane.showMessageDialog(this,
                    "No valid exercise selected.\nRun the seed SQL to populate exercises.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double maxWeight = Double.parseDouble(maxWeightField.getText().trim());
            int maxReps = Integer.parseInt(maxRepsField.getText().trim());

            PersonalRecord pr = new PersonalRecord(
                Session.getCurrentUser().getId(),
                selected.getExerciseId(),
                maxWeight, maxReps, LocalDate.now()
            );

            recordService.addRecord(pr);
            maxWeightField.setText("");
            maxRepsField.setText("");

            JOptionPane.showMessageDialog(this, "Personal record saved!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
            loadRecords();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Enter valid numbers for weight and reps.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRecords(){
        tableModel.setRowCount(0);
        if (!Session.isLoggedIn()) return;

        List<PersonalRecord> list = recordService.getUserRecords(Session.getCurrentUser().getId());
        for (PersonalRecord pr : list) {
            tableModel.addRow(new Object[]{
                pr.getRecordId(),
                pr.getExerciseName(),
                pr.getMaxWeight(),
                pr.getMaxReps(),
                pr.getDate().toString()
            });
        }
    }

    private void deleteSelected(){
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a record to delete.");
            return;
        }>
        int recordId = (int) tableModel.getValueAt(row, 0);
        int confirm  = JOptionPane.showConfirmDialog(this,
            "Delete this personal record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            recordService.deleteRecord(recordId);
            loadRecords();
        }
    }

    public void refresh() {
        loadExercises();
        loadRecords();
    }
}