package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MusicStoreView extends JFrame {
    public JTextField searchField;
    public JButton searchButton;
    public JTable resultTable;
    public DefaultTableModel tableModel;

    public MusicStoreView() {
        setTitle("Music Store Search");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Search Album or Artist:"));

        searchField = new JTextField(20);
        topPanel.add(searchField);

        searchButton = new JButton("Search");
        topPanel.add(searchButton);

        String[] columns = {"Album ID", "Album Title", "Artist", "Genre", "Year", "Quantity", "Availability"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(resultTable);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}