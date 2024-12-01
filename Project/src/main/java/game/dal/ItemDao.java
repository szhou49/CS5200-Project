package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Item;

public class ItemDao {
    protected ConnectionManager connectionManager;

    private static ItemDao instance = null;

    protected ItemDao() {
        connectionManager = new ConnectionManager();
    }

    public static ItemDao getInstance() {
        if (instance == null) {
            instance = new ItemDao();
        }
        return instance;
    }

    // Create a new Item
    public Item create(Item item) throws SQLException {
        String insertItem = "INSERT INTO Item(item_name, stack_size, vendor_price, item_level) VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertItem, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, item.getItemName());
            insertStmt.setInt(2, item.getStackSize());
            insertStmt.setInt(3, item.getVendorPrice());
            insertStmt.setInt(4, item.getItemLevel());
            insertStmt.executeUpdate();

            // Get the auto-generated itemId
            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                item.setItemId(resultKey.getInt(1));
            }
            return item;
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
            if (resultKey != null) {
                resultKey.close();
            }
        }
    }

    // Retrieve an Item by itemId
    public Item getItemById(int itemId) throws SQLException {
        String selectItem = "SELECT item_id, item_name, stack_size, vendor_price, item_level FROM Item WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectItem);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String itemName = results.getString("item_name");
                int stackSize = results.getInt("stack_size");
                int vendorPrice = results.getInt("vendor_price");
                int itemLevel = results.getInt("item_level");
                return new Item(itemId, itemName, stackSize, vendorPrice, itemLevel);
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

    // Retrieve all Items
    public List<Item> getItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String selectItems = "SELECT item_id, item_name, stack_size, vendor_price, item_level FROM Item;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectItems);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int itemId = results.getInt("item_id");
                String itemName = results.getString("item_name");
                int stackSize = results.getInt("stack_size");
                int vendorPrice = results.getInt("vendor_price");
                int itemLevel = results.getInt("item_level");
                items.add(new Item(itemId, itemName, stackSize, vendorPrice, itemLevel));
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
        return items;
    }

    // Delete an Item
    public Item delete(Item item) throws SQLException {
        String deleteItem = "DELETE FROM Item WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteItem);
            deleteStmt.setInt(1, item.getItemId());
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