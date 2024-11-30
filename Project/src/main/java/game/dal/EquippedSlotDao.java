package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.EquippedSlot;
import game.model.Gear;
import game.model.Character;

public class EquippedSlotDao {
    private ConnectionManager connectionManager;
    private CharacterDao characterDao;
    private GearDao gearDao;
    private static EquippedSlotDao instance = null;
    
    private EquippedSlotDao() {
        connectionManager = new ConnectionManager();
        characterDao = CharacterDao.getInstance();
        gearDao = GearDao.getInstance();
    }

    public static EquippedSlotDao getInstance() {
		if(instance == null) {
			instance = new EquippedSlotDao();
		}
		return instance;
    }
    
    // Create
    public EquippedSlot create(EquippedSlot equippedSlot) throws SQLException {
        // Verify that both character and gear exist
        Character character = characterDao.getCharacterById(equippedSlot.getCharacterId());
        if (character == null) {
            throw new SQLException("Character with ID " + equippedSlot.getCharacterId() + " does not exist");
        }
        
        Gear gear = gearDao.getGearByItemId(equippedSlot.getItemId());
        if (gear == null) {
            throw new SQLException("Gear with ID " + equippedSlot.getItemId() + " does not exist");
        }
        
        String insertSql = "INSERT INTO EquippedSlot(character_id, item_id, slot) VALUES(?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, equippedSlot.getCharacterId());
            pstmt.setInt(2, equippedSlot.getItemId());
            pstmt.setString(3, equippedSlot.getSlot());
            pstmt.executeUpdate();
            
            equippedSlot.setCharacter(character);
            equippedSlot.setGear(gear);
            
            return equippedSlot;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by compound primary key
    public EquippedSlot getEquippedSlot(int characterId, String slot) throws SQLException {
        String selectSql = "SELECT character_id, item_id, slot FROM EquippedSlot WHERE character_id=? AND slot=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, characterId);
            pstmt.setString(2, slot);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                EquippedSlot es = new EquippedSlot(
                    rs.getInt("character_id"),
                    rs.getInt("item_id"),
                    rs.getString("slot")
                );
                es.setCharacter(characterDao.getCharacterById(characterId));
                es.setGear(gearDao.getGearByItemId(es.getItemId()));
                return es;
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Get all equipped slots for a character
    public List<EquippedSlot> getEquippedSlotsByCharacter(int characterId) throws SQLException {
        List<EquippedSlot> equippedSlots = new ArrayList<>();
        String selectSql = "SELECT character_id, item_id, slot FROM EquippedSlot WHERE character_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, characterId);
            rs = pstmt.executeQuery();
            
            Character character = characterDao.getCharacterById(characterId);
            
            while(rs.next()) {
                EquippedSlot es = new EquippedSlot(
                    rs.getInt("character_id"),
                    rs.getInt("item_id"),
                    rs.getString("slot")
                );
                es.setCharacter(character);
                es.setGear(gearDao.getGearByItemId(es.getItemId()));
                equippedSlots.add(es);
            }
            return equippedSlots;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update item in slot
    public EquippedSlot updateEquippedItem(EquippedSlot equippedSlot, int newItemId) throws SQLException {
        // Verify that the new gear exists
        Gear newGear = gearDao.getGearByItemId(newItemId);
        if (newGear == null) {
            throw new SQLException("Gear with ID " + newItemId + " does not exist");
        }
        
        String updateSql = "UPDATE EquippedSlot SET item_id=? WHERE character_id=? AND slot=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newItemId);
            pstmt.setInt(2, equippedSlot.getCharacterId());
            pstmt.setString(3, equippedSlot.getSlot());
            pstmt.executeUpdate();
            
            equippedSlot.setItemId(newItemId);
            equippedSlot.setGear(newGear);
            return equippedSlot;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(EquippedSlot equippedSlot) throws SQLException {
        String deleteSql = "DELETE FROM EquippedSlot WHERE character_id=? AND slot=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, equippedSlot.getCharacterId());
            pstmt.setString(2, equippedSlot.getSlot());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 