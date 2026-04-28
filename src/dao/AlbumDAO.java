package dao;

import db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import model.Album;
import model.AlbumFactory;

public class AlbumDAO {

    public ArrayList<Album> searchAlbums(String keyword) {
        ArrayList<Album> albums = new ArrayList<>();

        String sql = """
            SELECT al.AlbumID, al.AlbumTitle, ar.ArtistName, al.Genre, al.ReleaseYear, al.Quantity
            FROM Album al
            JOIN Artist ar ON al.ArtistID = ar.ArtistID
            WHERE al.AlbumTitle LIKE ? OR ar.ArtistName LIKE ?
            ORDER BY ar.ArtistName, al.AlbumTitle
        """;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                row.put("albumID", rs.getInt("AlbumID"));
                row.put("albumTitle", rs.getString("AlbumTitle"));
                row.put("artistName", rs.getString("ArtistName"));
                row.put("genre", rs.getString("Genre"));
                row.put("releaseYear", rs.getInt("ReleaseYear"));
                row.put("quantity", rs.getInt("Quantity"));
                String formatType = rs.getString("Format");

                Album album = AlbumFactory.createAlbum(
                        formatType,
                        (int) row.get("albumID"),
                        (String) row.get("albumTitle"),
                        (String) row.get("artistName"),
                        (String) row.get("genre"),
                        (int) row.get("releaseYear"),
                        (int) row.get("quantity")
                );

                albums.add(album);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return albums;
    }
    public void addAlbum(String title, String artistName, String genre, int year, int quantity, String format) {
        String findArtistSql = "SELECT ArtistID FROM Artist WHERE ArtistName = ?";
        String insertArtistSql = "INSERT INTO Artist (ArtistName) VALUES (?)";
        String insertAlbumSql = "INSERT INTO Album (ArtistID, AlbumTitle, Genre, ReleaseYear, Quantity, Format) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            
            // Check if Artist exists, if not, create them
            PreparedStatement findArtistStmt = conn.prepareStatement(findArtistSql);
            findArtistStmt.setString(1, artistName);
            ResultSet rs = findArtistStmt.executeQuery();
            
            int artistId;
            if (rs.next()) {
                artistId = rs.getInt("ArtistID");
            } else {
                PreparedStatement insertArtistStmt = conn.prepareStatement(insertArtistSql, PreparedStatement.RETURN_GENERATED_KEYS);
                insertArtistStmt.setString(1, artistName);
                insertArtistStmt.executeUpdate();
                ResultSet keys = insertArtistStmt.getGeneratedKeys();
                keys.next();
                artistId = keys.getInt(1);
            }

            // Insert the Album
            PreparedStatement insertAlbumStmt = conn.prepareStatement(insertAlbumSql);
            insertAlbumStmt.setInt(1, artistId);
            insertAlbumStmt.setString(2, title);
            insertAlbumStmt.setString(3, genre);
            insertAlbumStmt.setInt(4, year);
            insertAlbumStmt.setInt(5, quantity);
            insertAlbumStmt.setString(6, format); // Assuming you added Format for the Factory pattern
            insertAlbumStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE: Change the quantity of an existing album
    public void updateAlbumQuantity(int albumID, int newQuantity) {
        String sql = "UPDATE Album SET Quantity = ? WHERE AlbumID = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, albumID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE: Remove an album from the database entirely
    public void deleteAlbum(int albumID) {
        String sql = "DELETE FROM Album WHERE AlbumID = ?";
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, albumID);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}