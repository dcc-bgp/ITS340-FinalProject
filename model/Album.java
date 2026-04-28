package model;

public abstract class Album {
    private int albumID;
    private String albumTitle;
    private String artistName;
    private String genre;
    private int releaseYear;
    private int quantity;
    protected String format; // added so that it can be record, dvd, etc. 

    public Album(int albumID, String albumTitle, String artistName, String genre, int releaseYear, int quantity) {
        this.albumID = albumID;
        this.albumTitle = albumTitle;
        this.artistName = artistName;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.quantity = quantity;
    }

    public int getAlbumID() {
        return albumID;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getFormat(){
        return format;
    }

    public String getAvailability() {
        return quantity > 0 ? "In Stock" : "Out of Stock";
    }
    public abstract String getPlaybackInstructions(); // polymorphism
}