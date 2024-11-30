package game.dal;

import game.model.Consumable;
import game.model.ConsumableAttributeBonus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsumableAttributeBonusDao {
    protected ConnectionManager connectionManager;

    private static ConsumableAttributeBonusDao instance = null;

    protected ConsumableAttributeBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static ConsumableAttributeBonusDao getInstance() {
        if (instance == null) {
            instance = new ConsumableAttributeBonusDao();
        }
        return instance;
    }

    // Create a new ConsumableAttributeBonus
    public ConsumableAttributeBonus create(ConsumableAttributeBonus bonus) throws SQLException {
        String insertBonus = "INSERT INTO ConsumableAttributeBonus(item_id, attribute, percentage_value, maximum_cap) VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertBonus);
            insertStmt.setInt(1, bonus.getConsumable().getItemId());
            insertStmt.setString(2, bonus.getAttribute());
            insertStmt.setDouble(3, bonus.getPercentage_value());
            insertStmt.setInt(4, bonus.getMaximumCap());
            insertStmt.executeUpdate();
            return bonus;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
        }
    }

    // Retrieve a ConsumableAttributeBonus by item_id and attribute
    public ConsumableAttributeBonus getConsumableAttributeBonus(int itemId, String attribute) throws SQLException {
        String selectBonus = "SELECT item_id, attribute, percentage_value, maximum_cap FROM ConsumableAttributeBonus WHERE item_id = ? AND attribute = ?;";
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
                double percentageValue = results.getDouble("percentage_value");
                int maximumCap = results.getInt("maximum_cap");
                ConsumableDao consumableDao = ConsumableDao.getInstance();
                Consumable consumable = consumableDao.getConsumableByItemId(itemId);
                return new ConsumableAttributeBonus(consumable, attribute, percentageValue, maximumCap);
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

    // Retrieve all ConsumableAttributeBonus records for a specific item_id
    public List<ConsumableAttributeBonus> getConsumableAttributeBonusesByItemId(int itemId) throws SQLException {
        List<ConsumableAttributeBonus> bonuses = new ArrayList<>();
        String selectBonuses = "SELECT item_id, attribute, percentage_value, maximum_cap FROM ConsumableAttributeBonus WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectBonuses);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            ConsumableDao consumableDao = ConsumableDao.getInstance();
            Consumable consumable = consumableDao.getConsumableByItemId(itemId);
            while (results.next()) {
                String attribute = results.getString("attribute");
                double percentageValue = results.getDouble("percentage_value");
                int maximumCap = results.getInt("maximum_cap");
                bonuses.add(new ConsumableAttributeBonus(consumable, attribute, percentageValue, maximumCap));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return bonuses;
    }

    // Delete a ConsumableAttributeBonus
    public ConsumableAttributeBonus delete(ConsumableAttributeBonus bonus) throws SQLException {
        String deleteBonus = "DELETE FROM ConsumableAttributeBonus WHERE item_id = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteBonus);
            deleteStmt.setInt(1, bonus.getConsumable().getItemId());
            deleteStmt.setString(2, bonus.getAttribute());
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
