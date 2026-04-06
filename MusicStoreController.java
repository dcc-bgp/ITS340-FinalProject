package controller;

import dao.AlbumDAO;
import model.Album;
import view.MusicStoreView;

import java.util.ArrayList;

public class MusicStoreController {
    private MusicStoreView view;
    private AlbumDAO albumDAO;

    public MusicStoreController(MusicStoreView view) {
        this.view = view;
        this.albumDAO = new AlbumDAO();

        this.view.searchButton.addActionListener(e -> searchAlbums());
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
}