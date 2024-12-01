package game.dal;

import game.model.Gear;
import game.model.GearAttributeBonus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GearAttributeBonusDao {
    protected ConnectionManager connectionManager;

    private static GearAttributeBonusDao instance = null;

    protected GearAttributeBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static GearAttributeBonusDao getInstance() {
        if (instance == null) {
            instance = new GearAttributeBonusDao();
        }
        return instance;
    }

    // Create a new GearAttributeBonus
    public GearAttributeBonus create(GearAttributeBonus bonus) throws SQLException {
        String insertBonus = "INSERT INTO GearAttributeBonus(item_id, attribute, bonus_value) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertBonus);
            insertStmt.setInt(1, bonus.getGear().getItemId());
            insertStmt.setString(2, bonus.getAttribute());
            insertStmt.setInt(3, bonus.getBonusValue());
            insertStmt.executeUpdate();
            return bonus;
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

    // Retrieve a GearAttributeBonus by item_id and attribute
    public GearAttributeBonus getGearAttributeBonus(int itemId, String attribute) throws SQLException {
        String selectBonus = "SELECT item_id, attribute, bonus_value FROM GearAttributeBonus WHERE item_id = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectBonus);
            selectStmt.setInt(1, itemId);
            selectStmt.setString(2, attribute);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int bonusValue = results.getInt("bonus_value");
                GearDao gearDao = GearDao.getInstance();
                Gear gear = gearDao.getGearByItemId(itemId);
                return new GearAttributeBonus(gear, attribute, bonusValue);
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

    // Retrieve all GearAttributeBonus records for a specific item_id
    public List<GearAttributeBonus> getGearAttributeBonusesByItemId(int itemId) throws SQLException {
        List<GearAttributeBonus> bonuses = new ArrayList<>();
        String selectBonuses = "SELECT item_id, attribute, bonus_value FROM GearAttributeBonus WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectBonuses);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            GearDao gearDao = GearDao.getInstance();
            Gear gear = gearDao.getGearByItemId(itemId);
            while (results.next()) {
                String attribute = results.getString("attribute");
                int bonusValue = results.getInt("bonus_value");
                bonuses.add(new GearAttributeBonus(gear, attribute, bonusValue));
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
        return bonuses;
    }

    // Delete a GearAttributeBonus by item_id and attribute
    public GearAttributeBonus delete(GearAttributeBonus bonus) throws SQLException {
        String deleteBonus = "DELETE FROM GearAttributeBonus WHERE item_id = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteBonus);
            deleteStmt.setInt(1, bonus.getGear().getItemId());
            deleteStmt.setString(2, bonus.getAttribute());
            deleteStmt.executeUpdate();
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
