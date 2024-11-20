package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Player;
import game.model.Weapon;
import game.model.Character;

public class CharacterDao {
    private ConnectionManager connectionManager;
    private PlayerDao playerDao;
    private WeaponDao weaponDao;
    private static CharacterDao instance = null;
    private CharacterDao() {
        connectionManager = new ConnectionManager();
        playerDao = PlayerDao.getInstance();
        weaponDao = WeaponDao.getInstance();
    }
    public static CharacterDao getInstance() {
		if(instance == null) {
			instance = new CharacterDao();
		}
		return instance;
	}
    
    // Create
    public Character create(Character character) throws SQLException {
        // Verify that the player exists
        Player player = playerDao.getPlayerByEmail(character.getEmailAddress());
        if (player == null) {
            throw new SQLException("Player with email " + character.getEmailAddress() + " does not exist");
        }
        
        // Verify that the weapon exists
        Weapon weapon = weaponDao.getWeaponByItemId(character.getMainHand());
        if (weapon == null) {
            throw new SQLException("Weapon with ID " + character.getMainHand() + " does not exist");
        }
        
        String insertSql = "INSERT INTO `Character`(first_name, last_name, email_address, main_hand) " +
                          "VALUES(?,?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, character.getFirstName());
            pstmt.setString(2, character.getLastName());
            pstmt.setString(3, character.getEmailAddress());
            pstmt.setInt(4, character.getMainHand());
            pstmt.executeUpdate();
            
            // Retrieve the auto-generated character_id
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                character.setCharacterId(rs.getInt(1));
            }
            
            // Set the related objects
            character.setPlayer(player);
            character.setMainHandWeapon(weapon);
            
            return character;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by primary key
    public Character getCharacterById(int characterId) throws SQLException {
        String selectSql = "SELECT character_id, first_name, last_name, email_address, main_hand " +
                          "FROM `Character` WHERE character_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, characterId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Character character = new Character(
                    rs.getInt("character_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email_address"),
                    rs.getInt("main_hand")
                );
                
                // Load related objects
                character.setPlayer(playerDao.getPlayerByEmail(character.getEmailAddress()));
                character.setMainHandWeapon(weaponDao.getWeaponByItemId(character.getMainHand()));
                
                return character;
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Search by player's email
    public List<Character> getCharactersByPlayerEmail(String emailAddress) throws SQLException {
        List<Character> characters = new ArrayList<>();
        String selectSql = "SELECT character_id, first_name, last_name, email_address, main_hand " +
                          "FROM `Character` WHERE email_address=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, emailAddress);
            rs = pstmt.executeQuery();
            
            Player player = playerDao.getPlayerByEmail(emailAddress);
            
            while(rs.next()) {
                Character character = new Character(
                    rs.getInt("character_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email_address"),
                    rs.getInt("main_hand")
                );
                character.setPlayer(player);
                character.setMainHandWeapon(weaponDao.getWeaponByItemId(character.getMainHand()));
                characters.add(character);
            }
            return characters;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update main hand weapon
    public Character updateMainHand(Character character, int newMainHandId) throws SQLException {
        // Verify that the new weapon exists
        Weapon newWeapon = weaponDao.getWeaponByItemId(newMainHandId);
        if (newWeapon == null) {
            throw new SQLException("Weapon with ID " + newMainHandId + " does not exist");
        }
        
        String updateSql = "UPDATE `Character` SET main_hand=? WHERE character_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newMainHandId);
            pstmt.setInt(2, character.getCharacterId());
            pstmt.executeUpdate();
            
            character.setMainHand(newMainHandId);
            character.setMainHandWeapon(newWeapon);
            
            return character;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Character character) throws SQLException {
        String deleteSql = "DELETE FROM `Character` WHERE character_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, character.getCharacterId());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 