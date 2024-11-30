package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Gear;
import game.model.Item;

public class GearDao {
    private ConnectionManager connectionManager;
    private ItemDao itemDao;  // We'll need this to handle the Item relationship
    private static GearDao instance = null;
    private GearDao() {
        connectionManager = new ConnectionManager();
        itemDao = ItemDao.getInstance();
    }
    
    public static GearDao getInstance() {
		if(instance == null) {
			instance = new GearDao();
		}
		return instance;
	}
    
    // Create
    public Gear create(Gear gear) throws SQLException {
        // First, create the Item if it doesn't exist
        if (gear.getItem().getItemId() == null) {
            gear.setItem(itemDao.create(gear.getItem()));
            gear.setItemId(gear.getItem().getItemId());
        }
        
        String insertSql = "INSERT INTO Gear(item_id, equipped_slot, required_level, " +
                          "defense_rating, magic_defense_rating) VALUES(?,?,?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, gear.getItemId());
            pstmt.setString(2, gear.getEquippedSlot());
            pstmt.setInt(3, gear.getRequiredLevel());
            pstmt.setInt(4, gear.getDefenseRating());
            pstmt.setInt(5, gear.getMagicDefenseRating());
            pstmt.executeUpdate();
            return gear;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by primary key
    public Gear getGearByItemId(int itemId) throws SQLException {
        String selectSql = "SELECT g.item_id, g.equipped_slot, g.required_level, " +
                          "g.defense_rating, g.magic_defense_rating " +
                          "FROM Gear g WHERE g.item_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, itemId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Item item = itemDao.getItemById(itemId);
                return new Gear(
                    rs.getInt("item_id"),
                    rs.getString("equipped_slot"),
                    rs.getInt("required_level"),
                    rs.getInt("defense_rating"),
                    rs.getInt("magic_defense_rating"),
                    item
                );
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Example of search method returning a list - find gear by defense rating range
    public List<Gear> getGearByDefenseRange(int minDefense, int maxDefense) throws SQLException {
        List<Gear> gearList = new ArrayList<>();
        String selectSql = "SELECT g.item_id, g.equipped_slot, g.required_level, " +
                          "g.defense_rating, g.magic_defense_rating " +
                          "FROM Gear g WHERE g.defense_rating BETWEEN ? AND ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, minDefense);
            pstmt.setInt(2, maxDefense);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                int itemId = rs.getInt("item_id");
                Item item = itemDao.getItemById(itemId);
                Gear gear = new Gear(
                    itemId,
                    rs.getString("equipped_slot"),
                    rs.getInt("required_level"),
                    rs.getInt("defense_rating"),
                    rs.getInt("magic_defense_rating"),
                    item
                );
                gearList.add(gear);
            }
            return gearList;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update defense rating
    public Gear updateDefenseRating(Gear gear, int newDefenseRating) throws SQLException {
        String updateSql = "UPDATE Gear SET defense_rating=? WHERE item_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newDefenseRating);
            pstmt.setInt(2, gear.getItemId());
            pstmt.executeUpdate();
            gear.setDefenseRating(newDefenseRating);
            return gear;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Gear gear) throws SQLException {
        String deleteSql = "DELETE FROM Gear WHERE item_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, gear.getItemId());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 