package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Item;
import game.model.Weapon;

public class WeaponDao {
    private ConnectionManager connectionManager;
    private ItemDao itemDao;
    private static WeaponDao instance = null;
    private WeaponDao() {
        connectionManager = new ConnectionManager();
        itemDao = ItemDao.getInstance();
    }
    public static WeaponDao getInstance() {
		if(instance == null) {
			instance = new WeaponDao();
		}
		return instance;
	}
    
    // Create
    public Weapon create(Weapon weapon) throws SQLException {
        // First, create the Item if it doesn't exist
        if (weapon.getItem().getItemId() == null) {
            weapon.setItem(itemDao.create(weapon.getItem()));
            weapon.setItemId(weapon.getItem().getItemId());
        }
        
        String insertSql = "INSERT INTO Weapon(item_id, required_level, damage, " +
                          "auto_attack, attack_delay) VALUES(?,?,?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, weapon.getItemId());
            pstmt.setInt(2, weapon.getRequiredLevel());
            pstmt.setInt(3, weapon.getDamage());
            pstmt.setDouble(4, weapon.getAutoAttack());
            pstmt.setDouble(5, weapon.getAttackDelay());
            pstmt.executeUpdate();
            return weapon;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by primary key
    public Weapon getWeaponByItemId(int itemId) throws SQLException {
        String selectSql = "SELECT w.item_id, w.required_level, w.damage, " +
                          "w.auto_attack, w.attack_delay " +
                          "FROM Weapon w WHERE w.item_id=?;";
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
                return new Weapon(
                    rs.getInt("item_id"),
                    rs.getInt("required_level"),
                    rs.getInt("damage"),
                    rs.getDouble("auto_attack"),
                    rs.getDouble("attack_delay"),
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
    
    // Search by damage range
    public List<Weapon> getWeaponsByDamageRange(int minDamage, int maxDamage) throws SQLException {
        List<Weapon> weapons = new ArrayList<>();
        String selectSql = "SELECT w.item_id, w.required_level, w.damage, " +
                          "w.auto_attack, w.attack_delay " +
                          "FROM Weapon w WHERE w.damage BETWEEN ? AND ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, minDamage);
            pstmt.setInt(2, maxDamage);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                int itemId = rs.getInt("item_id");
                Item item = itemDao.getItemById(itemId);
                weapons.add(new Weapon(
                    itemId,
                    rs.getInt("required_level"),
                    rs.getInt("damage"),
                    rs.getDouble("auto_attack"),
                    rs.getDouble("attack_delay"),
                    item
                ));
            }
            return weapons;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }

    // Get all weapons
    public List<Item> getWeapons() throws SQLException {
        List<Item> items = new ArrayList<>();
        String selectSql = "SELECT item_id, item_name, stack_size, vendor_price, item_level, required_level, damage, auto_attack, attack_delay " +
                          "FROM Weapon LEFT JOIN Item WHERE Weapon.item_id = Item.item_id;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
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
    
    // Update damage
    public Weapon updateDamage(Weapon weapon, int newDamage) throws SQLException {
        String updateSql = "UPDATE Weapon SET damage=? WHERE item_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newDamage);
            pstmt.setInt(2, weapon.getItemId());
            pstmt.executeUpdate();
            weapon.setDamage(newDamage);
            return weapon;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Weapon weapon) throws SQLException {
        String deleteSql = "DELETE FROM Weapon WHERE item_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, weapon.getItemId());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 