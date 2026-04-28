package model;

public class Vinyl extends Album {
    public Vinyl(int albumID, String albumTitle, String artistName, String genre, int releaseYear, int quantity) {
        super(albumID, albumTitle, artistName, genre, releaseYear, quantity, "Vinyl");
    }

    @Override
    public String getPlaybackInstructions() {
        return "Requires a turntable and stylus.";
    }
}