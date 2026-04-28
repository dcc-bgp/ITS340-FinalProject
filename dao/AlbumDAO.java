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
}