package game.dal;

import java.sql.*;
import game.model.Character;
import java.util.ArrayList;
import java.util.List;
import game.model.Attribute;

public class AttributeDao {
    private ConnectionManager connectionManager;
    private CharacterDao characterDao;
    private static AttributeDao instance = null;
    private AttributeDao() {
		connectionManager = new ConnectionManager();
		characterDao = CharacterDao.getInstance();
	}
    
	public static AttributeDao getInstance() {
		if(instance == null) {
			instance = new AttributeDao();
		}
		return instance;
	}
    
    // Create
    public Attribute create(Attribute attribute) throws SQLException {
        // Verify that the character exists
        Character character = characterDao.getCharacterById(attribute.getCharacterId());
        if (character == null) {
            throw new SQLException("Character with ID " + attribute.getCharacterId() + " does not exist");
        }
        
        String insertSql = "INSERT INTO Attribute(character_id, strength, dexterity, vitality, " +
            "intelligence, mind, critical_hit, determination, direct_hit_rate, defense, " +
            "magical_defense, attack_power, skill_speed, attack_magic_potency, " +
            "healing_magic_potency, spell_speed, average_item_level, tenacity, piety) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            
            pstmt.setInt(1, attribute.getCharacterId());
            pstmt.setInt(2, attribute.getStrength());
            pstmt.setInt(3, attribute.getDexterity());
            pstmt.setInt(4, attribute.getVitality());
            pstmt.setInt(5, attribute.getIntelligence());
            pstmt.setInt(6, attribute.getMind());
            pstmt.setInt(7, attribute.getCriticalHit());
            pstmt.setInt(8, attribute.getDetermination());
            pstmt.setInt(9, attribute.getDirectHitRate());
            pstmt.setInt(10, attribute.getDefense());
            pstmt.setInt(11, attribute.getMagicalDefense());
            pstmt.setInt(12, attribute.getAttackPower());
            pstmt.setInt(13, attribute.getSkillSpeed());
            pstmt.setInt(14, attribute.getAttackMagicPotency());
            pstmt.setInt(15, attribute.getHealingMagicPotency());
            pstmt.setInt(16, attribute.getSpellSpeed());
            pstmt.setInt(17, attribute.getAverageItemLevel());
            pstmt.setInt(18, attribute.getTenacity());
            pstmt.setInt(19, attribute.getPiety());
            
            pstmt.executeUpdate();
            
            // Set the character reference
            attribute.setCharacter(character);
            
            return attribute;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by primary key (character_id)
    public Attribute getAttributeByCharacterId(int characterId) throws SQLException {
        String selectSql = "SELECT character_id, strength, dexterity, vitality, intelligence, " +
            "mind, critical_hit, determination, direct_hit_rate, defense, magical_defense, " +
            "attack_power, skill_speed, attack_magic_potency, healing_magic_potency, " +
            "spell_speed, average_item_level, tenacity, piety FROM Attribute WHERE character_id=?;";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, characterId);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                Attribute attribute = new Attribute(
                    rs.getInt("character_id"),
                    rs.getInt("strength"),
                    rs.getInt("dexterity"),
                    rs.getInt("vitality"),
                    rs.getInt("intelligence"),
                    rs.getInt("mind"),
                    rs.getInt("critical_hit"),
                    rs.getInt("determination"),
                    rs.getInt("direct_hit_rate"),
                    rs.getInt("defense"),
                    rs.getInt("magical_defense"),
                    rs.getInt("attack_power"),
                    rs.getInt("skill_speed"),
                    rs.getInt("attack_magic_potency"),
                    rs.getInt("healing_magic_potency"),
                    rs.getInt("spell_speed"),
                    rs.getInt("average_item_level"),
                    rs.getInt("tenacity"),
                    rs.getInt("piety")
                );
                
                attribute.setCharacter(characterDao.getCharacterById(characterId));
                return attribute;
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Search by item level range
    public List<Attribute> getAttributesByItemLevelRange(int minLevel, int maxLevel) throws SQLException {
        List<Attribute> attributes = new ArrayList<>();
        String selectSql = "SELECT * FROM Attribute WHERE average_item_level BETWEEN ? AND ?;";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, minLevel);
            pstmt.setInt(2, maxLevel);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                Attribute attribute = new Attribute(
                    rs.getInt("character_id"),
                    rs.getInt("strength"),
                    rs.getInt("dexterity"),
                    rs.getInt("vitality"),
                    rs.getInt("intelligence"),
                    rs.getInt("mind"),
                    rs.getInt("critical_hit"),
                    rs.getInt("determination"),
                    rs.getInt("direct_hit_rate"),
                    rs.getInt("defense"),
                    rs.getInt("magical_defense"),
                    rs.getInt("attack_power"),
                    rs.getInt("skill_speed"),
                    rs.getInt("attack_magic_potency"),
                    rs.getInt("healing_magic_potency"),
                    rs.getInt("spell_speed"),
                    rs.getInt("average_item_level"),
                    rs.getInt("tenacity"),
                    rs.getInt("piety")
                );
                
                attribute.setCharacter(characterDao.getCharacterById(attribute.getCharacterId()));
                attributes.add(attribute);
            }
            return attributes;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update strength
    public Attribute updateStrength(Attribute attribute, int newStrength) throws SQLException {
        String updateSql = "UPDATE Attribute SET strength=? WHERE character_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newStrength);
            pstmt.setInt(2, attribute.getCharacterId());
            pstmt.executeUpdate();
            
            attribute.setStrength(newStrength);
            return attribute;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Attribute attribute) throws SQLException {
        String deleteSql = "DELETE FROM Attribute WHERE character_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, attribute.getCharacterId());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 