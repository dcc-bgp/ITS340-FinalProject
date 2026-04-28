package model;

public class AlbumFactory {
    
    public static Album createAlbum(String format, int albumID, String albumTitle, String artistName, 
                                    String genre, int releaseYear, int quantity) {
        
        // The Factory decides which subclass to instantiate
        switch (format.toUpperCase()) {
            case "VINYL":
                return new Vinyl(albumID, albumTitle, artistName, genre, releaseYear, quantity);
            case "CD":
                return new CD(albumID, albumTitle, artistName, genre, releaseYear, quantity);
            default:
                throw new IllegalArgumentException("Unknown media format: " + format);
        }
    }
}