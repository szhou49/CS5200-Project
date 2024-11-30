package game.dal;

import game.model.Consumable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsumableDao {
    protected ConnectionManager connectionManager;

    private static ConsumableDao instance = null;

    protected ConsumableDao() {
        connectionManager = new ConnectionManager();
    }

    public static ConsumableDao getInstance() {
        if (instance == null) {
            instance = new ConsumableDao();
        }
        return instance;
    }

    // Create a new Consumable
    public Consumable create(Consumable consumable) throws SQLException {
        String insertConsumable = "INSERT INTO Consumable(item_id, item_description) VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertConsumable);
            insertStmt.setInt(1, consumable.getItemId());
            insertStmt.setString(2, consumable.getItemDescription());
            insertStmt.executeUpdate();
            ItemDao.getInstance().create(consumable);
            return consumable;
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

    // Retrieve a Consumable by item_id
    public Consumable getConsumableByItemId(int itemId) throws SQLException {
        String selectConsumable = "SELECT item_id, item_description FROM Consumable WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectConsumable);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String itemDescription = results.getString("item_description");
                return new Consumable(ItemDao.getInstance().getItemById(itemId), itemDescription);
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

    // Retrieve all Consumables
    public List<Consumable> getAllConsumables() throws SQLException {
        List<Consumable> consumables = new ArrayList<>();
        String selectConsumables = "SELECT item_id, item_description FROM Consumable;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectConsumables);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int itemId = results.getInt("item_id");
                String itemDescription = results.getString("item_description");
                consumables.add(new Consumable(ItemDao.getInstance().getItemById(itemId), itemDescription));
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
        return consumables;
    }

    // Delete a Consumable
    public Consumable delete(Consumable consumable) throws SQLException {
        String deleteConsumable = "DELETE FROM Consumable WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteConsumable);
            deleteStmt.setInt(1, consumable.getItemId());
            deleteStmt.executeUpdate();
            ItemDao.getInstance().delete(consumable);
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
