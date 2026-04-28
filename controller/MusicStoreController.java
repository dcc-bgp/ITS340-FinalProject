package controller;

import dao.AlbumDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Album;
import view.MusicStoreView;

public class MusicStoreController {
    private MusicStoreView view;
    private AlbumDAO albumDAO;

    public MusicStoreController(MusicStoreView view) {
        this.view = view;
        this.albumDAO = new AlbumDAO();

        this.view.searchButton.addActionListener(e -> searchAlbums());
        this.view.addButton.addActionListener(e -> addNewAlbum());
        this.view.updateButton.addActionListener(e -> updateSelectedAlbum());
        this.view.deleteButton.addActionListener(e -> deleteSelectedAlbum());
    }

    private void searchAlbums() {
        String keyword = view.searchField.getText().trim();

        view.tableModel.setRowCount(0);

        ArrayList<Album> albums = albumDAO.searchAlbums(keyword);

        for (Album album : albums) {
            Object[] row = {
                    album.getAlbumID(),
                    album.getAlbumTitle(),
                    album.getArtistName(),
                    album.getGenre(),
                    album.getReleaseYear(),
                    album.getQuantity(),
                    album.getAvailability()
            };
            view.tableModel.addRow(row);
        }
    }
    private void addNewAlbum() {
        try {
            String title = view.titleInput.getText();
            String artist = view.artistInput.getText();
            String genre = view.genreInput.getText();
            int year = Integer.parseInt(view.yearInput.getText());
            int qty = Integer.parseInt(view.qtyInput.getText());
            String format = view.formatInput.getText();

            if (title.isEmpty() || artist.isEmpty() || format.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill out all fields.");
                return;
            }

            albumDAO.addAlbum(title, artist, genre, year, qty, format);
            JOptionPane.showMessageDialog(view, "Album Added Successfully!");
            
            // Clear inputs and refresh table
            view.titleInput.setText(""); view.artistInput.setText(""); 
            view.genreInput.setText(""); view.yearInput.setText(""); 
            view.qtyInput.setText(""); view.formatInput.setText("");
            searchAlbums();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Year and Quantity must be numbers.");
        }
    }

    private void updateSelectedAlbum() {
        int selectedRow = view.resultTable.getSelectedRow();
        if (selectedRow >= 0) {
            int albumId = (int) view.tableModel.getValueAt(selectedRow, 0);
            
            // Ask user for the new quantity
            String newQtyStr = JOptionPane.showInputDialog(view, "Enter new quantity:");
            if (newQtyStr != null && !newQtyStr.trim().isEmpty()) {
                try {
                    int newQty = Integer.parseInt(newQtyStr.trim());
                    albumDAO.updateAlbumQuantity(albumId, newQty);
                    searchAlbums(); // Refresh table to show new quantity
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Please enter a valid number.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Please select an album from the table to update.");
        }
    }

    private void deleteSelectedAlbum() {
        int selectedRow = view.resultTable.getSelectedRow();
        if (selectedRow >= 0) {
            int albumId = (int) view.tableModel.getValueAt(selectedRow, 0);
            String title = (String) view.tableModel.getValueAt(selectedRow, 1);
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete '" + title + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                albumDAO.deleteAlbum(albumId);
                searchAlbums(); // Refresh table to remove deleted item
            }
        } else {
            JOptionPane.showMessageDialog(view, "Please select an album from the table to delete.");
        }
    }
}