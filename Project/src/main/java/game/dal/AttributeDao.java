package game.dal;

import game.model.Attribute;
import game.model.Character;

import java.sql.*;

public class AttributeDao {
    protected ConnectionManager connectionManager;

    private static AttributeDao instance = null;

    protected AttributeDao() {
        connectionManager = new ConnectionManager();
    }

    public static AttributeDao getInstance() {
        if (instance == null) {
            instance = new AttributeDao();
        }
        return instance;
    }

    // Create a new Attribute
    public Attribute create(Attribute attribute) throws SQLException {
        String insertAttribute = "INSERT INTO Attribute(character_id, strength, dexterity, vitality, intelligence, mind, critical_hit, " +
                "determination, direct_hit_rate, defense, magical_defense, attack_power, skill_speed, attack_magic_potency, " +
                "healing_magic_potency, spell_speed, average_item_level, tenacity, piety) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertAttribute);
            insertStmt.setInt(1, attribute.getCharacter().getCharacterId());
            insertStmt.setInt(2, attribute.getStrength());
            insertStmt.setInt(3, attribute.getDexterity());
            insertStmt.setInt(4, attribute.getVitality());
            insertStmt.setInt(5, attribute.getIntelligence());
            insertStmt.setInt(6, attribute.getMind());
            insertStmt.setInt(7, attribute.getCriticalHit());
            insertStmt.setInt(8, attribute.getDetermination());
            insertStmt.setInt(9, attribute.getDirectHitRate());
            insertStmt.setInt(10, attribute.getDefense());
            insertStmt.setInt(11, attribute.getMagicalDefense());
            insertStmt.setInt(12, attribute.getAttackPower());
            insertStmt.setInt(13, attribute.getSkillSpeed());
            insertStmt.setInt(14, attribute.getAttackMagicPotency());
            insertStmt.setInt(15, attribute.getHealingMagicPotency());
            insertStmt.setInt(16, attribute.getSpellSpeed());
            insertStmt.setInt(17, attribute.getAverageItemLevel());
            insertStmt.setInt(18, attribute.getTenacity());
            insertStmt.setInt(19, attribute.getPiety());
            insertStmt.executeUpdate();
            return attribute;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
        }
    }

    // Retrieve an Attribute by character_id
    public Attribute getAttributeByCharacterId(int characterId) throws SQLException {
        String selectAttribute = "SELECT * FROM Attribute WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAttribute);
            selectStmt.setInt(1, characterId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                CharacterDao characterDao = CharacterDao.getInstance();
                Character character = characterDao.getCharacterById(characterId);
                return new Attribute(
                        results.getInt("strength"),
                        results.getInt("dexterity"),
                        results.getInt("vitality"),
                        results.getInt("intelligence"),
                        results.getInt("mind"),
                        results.getInt("critical_hit"),
                        results.getInt("determination"),
                        results.getInt("direct_hit_rate"),
                        results.getInt("defense"),
                        results.getInt("magical_defense"),
                        results.getInt("attack_power"),
                        results.getInt("skill_speed"),
                        results.getInt("attack_magic_potency"),
                        results.getInt("healing_magic_potency"),
                        results.getInt("spell_speed"),
                        results.getInt("average_item_level"),
                        results.getInt("tenacity"),
                        results.getInt("piety"),
                        character
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return null;
    }

    // Update Attribute
    public Attribute updateAttribute(Attribute attribute) throws SQLException {
        String updateAttribute = "UPDATE Attribute SET strength = ?, dexterity = ?, vitality = ?, intelligence = ?, mind = ?, " +
                "critical_hit = ?, determination = ?, direct_hit_rate = ?, defense = ?, magical_defense = ?, attack_power = ?, " +
                "skill_speed = ?, attack_magic_potency = ?, healing_magic_potency = ?, spell_speed = ?, average_item_level = ?, " +
                "tenacity = ?, piety = ? WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateAttribute);
            updateStmt.setInt(1, attribute.getStrength());
            updateStmt.setInt(2, attribute.getDexterity());
            updateStmt.setInt(3, attribute.getVitality());
            updateStmt.setInt(4, attribute.getIntelligence());
            updateStmt.setInt(5, attribute.getMind());
            updateStmt.setInt(6, attribute.getCriticalHit());
            updateStmt.setInt(7, attribute.getDetermination());
            updateStmt.setInt(8, attribute.getDirectHitRate());
            updateStmt.setInt(9, attribute.getDefense());
            updateStmt.setInt(10, attribute.getMagicalDefense());
            updateStmt.setInt(11, attribute.getAttackPower());
            updateStmt.setInt(12, attribute.getSkillSpeed());
            updateStmt.setInt(13, attribute.getAttackMagicPotency());
            updateStmt.setInt(14, attribute.getHealingMagicPotency());
            updateStmt.setInt(15, attribute.getSpellSpeed());
            updateStmt.setInt(16, attribute.getAverageItemLevel());
            updateStmt.setInt(17, attribute.getTenacity());
            updateStmt.setInt(18, attribute.getPiety());
            updateStmt.setInt(19, attribute.getCharacter().getCharacterId());
            updateStmt.executeUpdate();
            return attribute;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (updateStmt != null) updateStmt.close();
        }
    }

    // Delete Attribute
    public Attribute delete(Attribute attribute) throws SQLException {
        String deleteAttribute = "DELETE FROM Attribute WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteAttribute);
            deleteStmt.setInt(1, attribute.getCharacter().getCharacterId());
            deleteStmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (deleteStmt != null) deleteStmt.close();
        }
    }
}
