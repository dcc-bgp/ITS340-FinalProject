package dao;

import db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Album;
import model.AlbumFactory;

public class AlbumDAO {

    public ArrayList<Album> searchAlbums(String keyword) {
        ArrayList<Album> albums = new ArrayList<>();

        String sql = """
            SELECT al.AlbumID, al.AlbumTitle, ar.ArtistName, al.Genre, al.ReleaseYear, al.Format, IFNULL(SUM(i.Quantity), 0) as Quantity
            FROM Album al
            JOIN Artist ar ON al.ArtistID = ar.ArtistID
            LEFT JOIN Inventory i ON al.AlbumID = i.AlbumID
            WHERE al.AlbumTitle LIKE ? OR ar.ArtistName LIKE ?
            GROUP BY al.AlbumID
            ORDER BY ar.ArtistName, al.AlbumTitle
        """;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Album album = AlbumFactory.createAlbum(
                        rs.getString("Format"),
                        rs.getInt("AlbumID"),
                        rs.getString("AlbumTitle"),
                        rs.getString("ArtistName"),
                        rs.getString("Genre"),
                        rs.getInt("ReleaseYear"),
                        rs.getInt("Quantity")
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
        String insertAlbumSql = "INSERT INTO Album (ArtistID, AlbumTitle, Genre, ReleaseYear, Price, Format) VALUES (?, ?, ?, ?, 15.00, ?)";
        String insertInventorySql = "INSERT INTO Inventory (LocationID, AlbumID, Quantity) VALUES (1, ?, ?)";

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
            PreparedStatement insertAlbumStmt = conn.prepareStatement(insertAlbumSql, PreparedStatement.RETURN_GENERATED_KEYS);
            insertAlbumStmt.setInt(1, artistId);
            insertAlbumStmt.setString(2, title);
            insertAlbumStmt.setString(3, genre);
            insertAlbumStmt.setInt(4, year);
            insertAlbumStmt.setString(5, format);
            insertAlbumStmt.executeUpdate();
            
            ResultSet albumKeys = insertAlbumStmt.getGeneratedKeys();
            albumKeys.next();
            int newAlbumId = albumKeys.getInt(1);

            PreparedStatement insertInvStmt = conn.prepareStatement(insertInventorySql);
            insertInvStmt.setInt(1, newAlbumId);
            insertInvStmt.setInt(2, quantity);
            insertInvStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE: Change the quantity of an existing album
    public void updateAlbumQuantity(int albumID, int newQuantity) {
        String sql = "UPDATE Inventory SET Quantity = ? WHERE AlbumID = ?";
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