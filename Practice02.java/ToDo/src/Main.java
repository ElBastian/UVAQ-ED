import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main {
    private static LinkedList tasks = new LinkedList();
    private static DefaultTableModel tablel;
    private static JTable table;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ToDo List");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] columns = {"Task", "Details", "Date"};
        tablel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tablel);
        JTextField taskInput = new JTextField(10);
        JTextField detailsInput = new JTextField(15);   
        JTextField dateInput = new JTextField(8);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            if (!taskInput.getText().isEmpty()) {
                tasks.addUrgentTask(taskInput.getText(), detailsInput.getText(), dateInput.getText());
                updateUI();
                taskInput.setText("");
                detailsInput.setText("");
                dateInput.setText("");
            }
        });
        JButton upButton = new JButton("Move");
        JButton deleteButton = new JButton("Delete");
        upButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String name = (String) tablel.getValueAt(row, 0);
                String det = (String) tablel.getValueAt(row, 1);
                String dat = (String) tablel.getValueAt(row, 2);
                tasks.removeTask(name);
                tasks.addUrgentTask(name, det, dat);
                updateUI();
            }
        });
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tasks.removeTask((String) tablel.getValueAt(row, 0));
                updateUI();
            }
        });
        JPanel Panel = new JPanel();
        Panel.add(new JLabel("Task:"));
        Panel.add(taskInput);
        Panel.add(new JLabel("Details:"));
        Panel.add(detailsInput);
        Panel.add(new JLabel("Date (Day/Month):"));
        Panel.add(dateInput);
        Panel.add(addButton);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(upButton);
        bottomPanel.add(deleteButton);
        frame.add(Panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    private static void updateUI() {
        tablel.setRowCount(0);
        Node tmp = tasks.getHead();
        while (tmp != null) {
            tablel.addRow(new Object[]{tmp.getTask(), tmp.getDetails(), tmp.getDate()});
            tmp = tmp.getNext();
        }
    }
}