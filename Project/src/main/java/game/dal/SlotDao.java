package game.dal;

import game.model.Character;
import game.model.Item;
import game.model.Slot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlotDao {
    protected ConnectionManager connectionManager;

    private static SlotDao instance = null;

    protected SlotDao() {
        connectionManager = new ConnectionManager();
    }

    public static SlotDao getInstance() {
        if (instance == null) {
            instance = new SlotDao();
        }
        return instance;
    }

    // Create a new Slot
    public Slot create(Slot slot) throws SQLException {
        String insertSlot = "INSERT INTO Slot(character_id, slot_index, item_id, quantity) VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertSlot);
            insertStmt.setInt(1, slot.getCharacter().getCharacterId());
            insertStmt.setInt(2, slot.getSlotIndex());
            insertStmt.setInt(3, slot.getItem().getItemId());
            insertStmt.setInt(4, slot.getQuantity());
            insertStmt.executeUpdate();
            return slot;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
        }
    }

    // Retrieve a Slot by character_id and slot_index
    public Slot getSlotByCharacterAndIndex(int characterId, int slotIndex) throws SQLException {
        String selectSlot = "SELECT character_id, slot_index, item_id, quantity FROM Slot WHERE character_id = ? AND slot_index = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectSlot);
            selectStmt.setInt(1, characterId);
            selectStmt.setInt(2, slotIndex);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int itemId = results.getInt("item_id");
                int quantity = results.getInt("quantity");

                CharacterDao characterDao = CharacterDao.getInstance();
                ItemDao itemDao = ItemDao.getInstance();

                Character character = characterDao.getCharacterById(characterId);
                Item item = itemDao.getItemById(itemId);

                return new Slot(slotIndex, quantity, character, item);
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

    // Retrieve all Slots for a character
    public List<Slot> getSlotsByCharacterId(int characterId) throws SQLException {
        List<Slot> slots = new ArrayList<>();
        String selectSlots = "SELECT character_id, slot_index, item_id, quantity FROM Slot WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectSlots);
            selectStmt.setInt(1, characterId);
            results = selectStmt.executeQuery();

            CharacterDao characterDao = CharacterDao.getInstance();
            ItemDao itemDao = ItemDao.getInstance();
            Character character = characterDao.getCharacterById(characterId);

            while (results.next()) {
                int slotIndex = results.getInt("slot_index");
                int itemId = results.getInt("item_id");
                int quantity = results.getInt("quantity");

                Item item = itemDao.getItemById(itemId);
                slots.add(new Slot(slotIndex, quantity, character, item));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return slots;
    }

    // Update the quantity of an existing Slot
    public Slot updateQuantity(Slot slot, int newQuantity) throws SQLException {
        String updateSlot = "UPDATE Slot SET quantity = ? WHERE character_id = ? AND slot_index = ?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateSlot);
            updateStmt.setInt(1, newQuantity);
            updateStmt.setInt(2, slot.getCharacter().getCharacterId());
            updateStmt.setInt(3, slot.getSlotIndex());
            updateStmt.executeUpdate();
            slot.setQuantity(newQuantity);
            return slot;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (updateStmt != null) updateStmt.close();
        }
    }

    // Delete a Slot
    public Slot delete(Slot slot) throws SQLException {
        String deleteSlot = "DELETE FROM Slot WHERE character_id = ? AND slot_index = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteSlot);
            deleteStmt.setInt(1, slot.getCharacter().getCharacterId());
            deleteStmt.setInt(2, slot.getSlotIndex());
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
