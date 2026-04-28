package model;

public class CD extends Album {
    public CD(int albumID, String albumTitle, String artistName, String genre, int releaseYear, int quantity) {
        super(albumID, albumTitle, artistName, genre, releaseYear, quantity, "CD");
    }

    @Override
    public String getPlaybackInstructions() {
        return "Requires a CD player or disc drive.";
    }
}