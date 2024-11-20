package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Player;

public class PlayerDao {
    private ConnectionManager connectionManager;
    private static PlayerDao instance = null;
    private PlayerDao() {
        connectionManager = new ConnectionManager();
    }
    public static PlayerDao getInstance() {
		if(instance == null) {
			instance = new PlayerDao();
		}
		return instance;
	}
    
    // Create
    public Player create(Player player) throws SQLException {
        String insertSql = "INSERT INTO Player(email_address, player_name) VALUES(?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, player.getEmailAddress());
            pstmt.setString(2, player.getPlayerName());
            pstmt.executeUpdate();
            return player;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by primary key
    public Player getPlayerByEmail(String emailAddress) throws SQLException {
        String selectSql = "SELECT email_address, player_name FROM Player WHERE email_address=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, emailAddress);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return new Player(
                    rs.getString("email_address"),
                    rs.getString("player_name")
                );
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Example of search method returning a list
    public List<Player> getPlayersByPartialName(String partialName) throws SQLException {
        List<Player> players = new ArrayList<>();
        String selectSql = "SELECT email_address, player_name FROM Player WHERE player_name LIKE ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, "%" + partialName + "%");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                players.add(new Player(
                    rs.getString("email_address"),
                    rs.getString("player_name")
                ));
            }
            return players;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update
    public Player updatePlayerName(Player player, String newName) throws SQLException {
        String updateSql = "UPDATE Player SET player_name=? WHERE email_address=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setString(1, newName);
            pstmt.setString(2, player.getEmailAddress());
            pstmt.executeUpdate();
            player.setPlayerName(newName);
            return player;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Player player) throws SQLException {
        String deleteSql = "DELETE FROM Player WHERE email_address=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setString(1, player.getEmailAddress());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 