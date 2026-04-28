package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MusicStoreView extends JFrame {
    public JTextField searchField;
    public JButton searchButton;
    public JTable resultTable;
    public DefaultTableModel tableModel;
    // add CRUD
    public JButton deleteButton;
    public JButton updateButton;
    public JButton addButton;
    public JTextField titleInput, artistInput, genreInput, yearInput, qtyInput, formatInput;

    public MusicStoreView() {
        setTitle("Music Store Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       // TOP PANEL (Search)
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Search Album or Artist:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);
        searchButton = new JButton("Search");
        topPanel.add(searchButton);

        // CENTER PANEL (Table)
        String[] columns = {"Album ID", "Album Title", "Artist", "Genre", "Year", "Quantity", "Availability"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // BOTTOM PANEL (CRUD Controls)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1)); // Two rows for better spacing
        
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Title:")); titleInput = new JTextField(10); inputPanel.add(titleInput);
        inputPanel.add(new JLabel("Artist:")); artistInput = new JTextField(10); inputPanel.add(artistInput);
        inputPanel.add(new JLabel("Genre:")); genreInput = new JTextField(8); inputPanel.add(genreInput);
        inputPanel.add(new JLabel("Year:")); yearInput = new JTextField(4); inputPanel.add(yearInput);
        inputPanel.add(new JLabel("Qty:")); qtyInput = new JTextField(4); inputPanel.add(qtyInput);
        inputPanel.add(new JLabel("Format (CD/Vinyl):")); formatInput = new JTextField(5); inputPanel.add(formatInput);
        
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add New Album");
        updateButton = new JButton("Update Selected Quantity");
        deleteButton = new JButton("Delete Selected");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        bottomPanel.add(inputPanel);
        bottomPanel.add(buttonPanel);

        // Add to Frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}