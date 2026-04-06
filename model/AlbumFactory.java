package model;

public class AlbumFactory {
    public static Album createAlbum(int albumID, String albumTitle, String artistName,
                                    String genre, int releaseYear, int quantity) {
        return new Album(albumID, albumTitle, artistName, genre, releaseYear, quantity);
    }
}