package game.dal;

import game.model.Gear;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GearDao {
    protected ConnectionManager connectionManager;

    private static GearDao instance = null;

    protected GearDao() {
        connectionManager = new ConnectionManager();
    }

    public static GearDao getInstance() {
        if (instance == null) {
            instance = new GearDao();
        }
        return instance;
    }

    // Create a new Gear
    public Gear create(Gear gear) throws SQLException {
        String insertGear = "INSERT INTO Gear(item_id, equipped_slot, required_level, defense_rating, magic_defense_rating) VALUES(?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertGear);
            insertStmt.setInt(1, gear.getItemId());
            insertStmt.setString(2, gear.getEquippedSlot());
            insertStmt.setInt(3, gear.getRequiredLevel());
            insertStmt.setInt(4, gear.getDefenseRating());
            insertStmt.setInt(5, gear.getMagicDefenseRating());
            insertStmt.executeUpdate();
            ItemDao.getInstance().create(gear);
            return gear;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
        }
    }

    // Retrieve a Gear by item_id
    public Gear getGearByItemId(int itemId) throws SQLException {
        String selectGear = "SELECT item_id, equipped_slot, required_level, defense_rating, magic_defense_rating FROM Gear WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectGear);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String equippedSlot = results.getString("equipped_slot");
                int requiredLevel = results.getInt("required_level");
                int defenseRating = results.getInt("defense_rating");
                int magicDefenseRating = results.getInt("magic_defense_rating");
                return new Gear(ItemDao.getInstance().getItemById(itemId), equippedSlot, requiredLevel, defenseRating, magicDefenseRating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }

    // Retrieve all Gear
    public List<Gear> getAllGear() throws SQLException {
        List<Gear> gearList = new ArrayList<>();
        String selectGear = "SELECT item_id, equipped_slot, required_level, defense_rating, magic_defense_rating FROM Gear;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectGear);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int itemId = results.getInt("item_id");
                String equippedSlot = results.getString("equipped_slot");
                int requiredLevel = results.getInt("required_level");
                int defenseRating = results.getInt("defense_rating");
                int magicDefenseRating = results.getInt("magic_defense_rating");
                gearList.add(new Gear(ItemDao.getInstance().getItemById(itemId), equippedSlot, requiredLevel, defenseRating, magicDefenseRating));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return gearList;
    }

    // Delete a Gear
    public Gear delete(Gear gear) throws SQLException {
        String deleteGear = "DELETE FROM Gear WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteGear);
            deleteStmt.setInt(1, gear.getItemId());
            deleteStmt.executeUpdate();
            ItemDao.getInstance().delete(gear);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
}
