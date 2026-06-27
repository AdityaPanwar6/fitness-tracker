package ui;

import model.BodyProgress;
import service.ProgressService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Two sections:
 *   Top    – form to log new body measurements (weight, body fat, chest, waist)
 *   Bottom – table showing history + Delete button
 */
public class ProgressPanel extends JPanel {

    private final JTextField weightField = new JTextField(8);
    private final JTextField bodyFatField = new JTextField(8);
    private final JTextField chestField = new JTextField(8);
    private final JTextField waistField = new JTextField(8);

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final ProgressService progressService = new ProgressService();

    public ProgressPanel(){
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(15, 20, 15, 20));

        add(buildFormPanel(), BorderLayout.NORTH);

        JPanel tablePanel = buildTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        this.tableModel = new DefaultTableModel();

        table = (JTable) ((JScrollPane) tablePanel.getComponent(0)).getViewport().getView();
    }

    //LOG PROGRESS FORM
    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Log Body Progress"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Object[][] fields = {
            {"Weight (kg):", weightField},
            {"Body Fat (%):", bodyFatField},
            {"Chest (cm):", chestField},
            {"Waist (cm):", waistField},
        };

        for (int i = 0; i < fields.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            panel.add(new JLabel((String) fields[i][0]), gbc);
            gbc.gridx = 1;
            panel.add((Component) fields[i][1], gbc);
        }

        JButton saveBtn = new JButton("Save Progress");
        saveBtn.setBackground(new Color(46, 204, 113));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFocusPainted(false);

        gbc.gridx = 0; gbc.gridy = fields.length; gbc.gridwidth = 2;
        gbc.fill   = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveBtn, gbc);

        saveBtn.addActionListener(e -> saveProgress());
        return panel;
    }

    // HISTORY TABLE
    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Progress History"));

        String[] cols = {"ID", "Date", "Weight (kg)", "Body Fat (%)", "Chest (cm)", "Waist (cm)"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable tbl = new JTable(model);
        tbl.setRowHeight(24);
        tbl.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        panel.putClientProperty("tableModel", model);

        JScrollPane scroll = new JScrollPane(tbl);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("Refresh");
        JButton deleteBtn  = new JButton("Delete Selected");
        deleteBtn.setBackground(new Color(231, 76, 60));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);

        refreshBtn.addActionListener(e -> loadProgress(model));
        deleteBtn.addActionListener(e -> deleteSelected(tbl, model));

        btnRow.add(refreshBtn);
        btnRow.add(deleteBtn);

        panel.add(scroll,  BorderLayout.CENTER);
        panel.add(btnRow,  BorderLayout.SOUTH);

        loadProgress(model);
        return panel;
    }

    //LOGIN TO SAVE PROG
    private void saveProgress(){
        if (!Session.isLoggedIn()){
            JOptionPane.showMessageDialog(this, "Please log in first.");
            return;
        }
        try {
            double weight = Double.parseDouble(weightField.getText().trim());
            double bodyFat = Double.parseDouble(bodyFatField.getText().trim());
            double chest = Double.parseDouble(chestField.getText().trim());
            double waist = Double.parseDouble(waistField.getText().trim());

            BodyProgress bp = new BodyProgress(Session.getCurrentUser().getId(),LocalDate.now(),weight, bodyFat, chest, waist);

            progressService.addProgress(bp);

            weightField.setText(""); bodyFatField.setText("");
            chestField.setText("");  waistField.setText("");

            JOptionPane.showMessageDialog(this, "Progress saved!", "Success", JOptionPane.INFORMATION_MESSAGE);

            JPanel parent = (JPanel) ((JScrollPane) ((JPanel) getComponent(1)).getComponent(0)).getParent().getParent();
            DefaultTableModel model = (DefaultTableModel) ((JPanel) getComponent(1)).getClientProperty("tableModel");
            if (model != null) loadProgress(model);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadProgress(DefaultTableModel model){
        model.setRowCount(0);
        if (!Session.isLoggedIn()) return;

        List<BodyProgress> list = progressService.getUserProgress(Session.getCurrentUser().getId());
        for (BodyProgress bp : list) {
            model.addRow(new Object[]{
                bp.getProgressId(),
                bp.getDate().toString(),
                bp.getWeight(),
                bp.getBodyFat(),
                bp.getChest(),
                bp.getWaist()
            });
        }
    }

    private void deleteSelected(JTable tbl, DefaultTableModel model){
        int row = tbl.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }
        int progressId = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this progress entry?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            progressService.deleteProgress(progressId);
            loadProgress(model);
        }
    }

    public void refresh(){
        Component center = getComponent(1);
        if (center instanceof JPanel) {
            DefaultTableModel model = (DefaultTableModel) ((JPanel) center).getClientProperty("tableModel");
            if (model != null) loadProgress(model);
        }
    }
}