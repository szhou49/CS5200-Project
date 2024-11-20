package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Item;
import game.model.Slot;
import game.model.Character;

public class SlotDao {
    private ConnectionManager connectionManager;
    private CharacterDao characterDao;
    private ItemDao itemDao;
    private static SlotDao instance = null;
    
    private SlotDao() {
        connectionManager = new ConnectionManager();
        characterDao = CharacterDao.getInstance();
        itemDao = ItemDao.getInstance();
    }
    public static SlotDao getInstance() {
		if(instance == null) {
			instance = new SlotDao();
		}
		return instance;
	}
    
    // Create
    public Slot create(Slot slot) throws SQLException {
        // Verify that both character and item exist
        Character character = characterDao.getCharacterById(slot.getCharacterId());
        if (character == null) {
            throw new SQLException("Character with ID " + slot.getCharacterId() + " does not exist");
        }
        
        Item item = itemDao.getItemById(slot.getItemId());
        if (item == null) {
            throw new SQLException("Item with ID " + slot.getItemId() + " does not exist");
        }
        
        String insertSql = "INSERT INTO Slot(character_id, slot_index, item_id, quantity) VALUES(?,?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, slot.getCharacterId());
            pstmt.setInt(2, slot.getSlotIndex());
            pstmt.setInt(3, slot.getItemId());
            pstmt.setInt(4, slot.getQuantity());
            pstmt.executeUpdate();
            
            slot.setCharacter(character);
            slot.setItem(item);
            
            return slot;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by compound primary key
    public Slot getSlot(int characterId, int slotIndex) throws SQLException {
        String selectSql = "SELECT character_id, slot_index, item_id, quantity " +
                          "FROM Slot WHERE character_id=? AND slot_index=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, characterId);
            pstmt.setInt(2, slotIndex);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Slot slot = new Slot(
                    rs.getInt("character_id"),
                    rs.getInt("slot_index"),
                    rs.getInt("item_id"),
                    rs.getInt("quantity")
                );
                slot.setCharacter(characterDao.getCharacterById(characterId));
                slot.setItem(itemDao.getItemById(slot.getItemId()));
                return slot;
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Get all slots for a character
    public List<Slot> getSlotsByCharacter(int characterId) throws SQLException {
        List<Slot> slots = new ArrayList<>();
        String selectSql = "SELECT character_id, slot_index, item_id, quantity " +
                          "FROM Slot WHERE character_id=? ORDER BY slot_index;";
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
                Slot slot = new Slot(
                    rs.getInt("character_id"),
                    rs.getInt("slot_index"),
                    rs.getInt("item_id"),
                    rs.getInt("quantity")
                );
                slot.setCharacter(character);
                slot.setItem(itemDao.getItemById(slot.getItemId()));
                slots.add(slot);
            }
            return slots;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update quantity
    public Slot updateQuantity(Slot slot, int newQuantity) throws SQLException {
        String updateSql = "UPDATE Slot SET quantity=? WHERE character_id=? AND slot_index=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, slot.getCharacterId());
            pstmt.setInt(3, slot.getSlotIndex());
            pstmt.executeUpdate();
            
            slot.setQuantity(newQuantity);
            return slot;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Slot slot) throws SQLException {
        String deleteSql = "DELETE FROM Slot WHERE character_id=? AND slot_index=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, slot.getCharacterId());
            pstmt.setInt(2, slot.getSlotIndex());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 