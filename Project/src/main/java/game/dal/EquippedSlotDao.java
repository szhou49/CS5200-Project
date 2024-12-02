package game.dal;

import game.model.Character;
import game.model.EquippedSlot;
import game.model.EquippedSlot.SlotEnum;
import game.model.Gear;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquippedSlotDao {
    protected ConnectionManager connectionManager;

    private static EquippedSlotDao instance = null;

    protected EquippedSlotDao() {
        connectionManager = new ConnectionManager();
    }

    public static EquippedSlotDao getInstance() {
        if (instance == null) {
            instance = new EquippedSlotDao();
        }
        return instance;
    }

    // Create a new EquippedSlot
    public EquippedSlot create(EquippedSlot equippedSlot) throws SQLException {
        String insertEquippedSlot = "INSERT INTO EquippedSlot(character_id, item_id, slot) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEquippedSlot);
            insertStmt.setInt(1, equippedSlot.getCharacter().getCharacterId());
            insertStmt.setInt(2, equippedSlot.getGear().getItemId());
            insertStmt.setString(3, equippedSlot.getSlot().toString());
            insertStmt.executeUpdate();
            return equippedSlot;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
        }
    }

    // Retrieve an EquippedSlot by character_id and slot
    public EquippedSlot getEquippedSlot(int characterId, String slot) throws SQLException {
        String selectEquippedSlot = "SELECT character_id, item_id, slot FROM EquippedSlot WHERE character_id = ? AND slot = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectEquippedSlot);
            selectStmt.setInt(1, characterId);
            selectStmt.setString(2, slot);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int itemId = results.getInt("item_id");
                GearDao gearDao = GearDao.getInstance();
                CharacterDao characterDao = CharacterDao.getInstance();
                Gear gear = gearDao.getGearByItemId(itemId);
                Character character = characterDao.getCharacterById(characterId);
                return new EquippedSlot(SlotEnum.valueOf(slot), character, gear);
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

    // Retrieve all EquippedSlots for a character
    public List<EquippedSlot> getEquippedSlotsByCharacterId(int characterId) throws SQLException {
        List<EquippedSlot> equippedSlots = new ArrayList<>();
        String selectEquippedSlots = "SELECT character_id, item_id, slot FROM EquippedSlot WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectEquippedSlots);
            selectStmt.setInt(1, characterId);
            results = selectStmt.executeQuery();
            GearDao gearDao = GearDao.getInstance();
            CharacterDao characterDao = CharacterDao.getInstance();
            Character character = characterDao.getCharacterById(characterId);

            while (results.next()) {
                int itemId = results.getInt("item_id");
                String slot = results.getString("slot");
                Gear gear = gearDao.getGearByItemId(itemId);
                equippedSlots.add(new EquippedSlot(SlotEnum.valueOf(slot), character, gear));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return equippedSlots;
    }

    // Delete an EquippedSlot
    public EquippedSlot delete(EquippedSlot equippedSlot) throws SQLException {
        String deleteEquippedSlot = "DELETE FROM EquippedSlot WHERE character_id = ? AND slot = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteEquippedSlot);
            deleteStmt.setInt(1, equippedSlot.getCharacter().getCharacterId());
            deleteStmt.setString(2, equippedSlot.getSlot().toString());
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
