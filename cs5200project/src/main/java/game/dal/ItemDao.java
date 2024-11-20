package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Item;

public class ItemDao {
    private ConnectionManager connectionManager;
    private static ItemDao instance = null;
    private ItemDao() {
        connectionManager = new ConnectionManager();
    }
    public static ItemDao getInstance() {
		if(instance == null) {
			instance = new ItemDao();
		}
		return instance;
	}
    
    
    // Create
    public Item create(Item item) throws SQLException {
        String insertSql = "INSERT INTO Item(item_name, stack_size, vendor_price, item_level) " +
                          "VALUES(?,?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, item.getItemName());
            pstmt.setInt(2, item.getStackSize());
            pstmt.setInt(3, item.getVendorPrice());
            pstmt.setInt(4, item.getItemLevel());
            pstmt.executeUpdate();
            
            // Retrieve the auto-generated item_id
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                item.setItemId(rs.getInt(1));
            }
            return item;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by primary key
    public Item getItemById(int itemId) throws SQLException {
        String selectSql = "SELECT item_id, item_name, stack_size, vendor_price, item_level " +
                          "FROM Item WHERE item_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, itemId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return new Item(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getInt("stack_size"),
                    rs.getInt("vendor_price"),
                    rs.getInt("item_level")
                );
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Search by partial name
    public List<Item> getItemsByPartialName(String partialName) throws SQLException {
        List<Item> items = new ArrayList<>();
        String selectSql = "SELECT item_id, item_name, stack_size, vendor_price, item_level " +
                          "FROM Item WHERE item_name LIKE ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, "%" + partialName + "%");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                items.add(new Item(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getInt("stack_size"),
                    rs.getInt("vendor_price"),
                    rs.getInt("item_level")
                ));
            }
            return items;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update vendor price
    public Item updateVendorPrice(Item item, int newVendorPrice) throws SQLException {
        String updateSql = "UPDATE Item SET vendor_price=? WHERE item_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newVendorPrice);
            pstmt.setInt(2, item.getItemId());
            pstmt.executeUpdate();
            item.setVendorPrice(newVendorPrice);
            return item;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Item item) throws SQLException {
        String deleteSql = "DELETE FROM Item WHERE item_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, item.getItemId());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 