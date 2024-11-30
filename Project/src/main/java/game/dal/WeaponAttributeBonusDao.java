package game.dal;

import game.model.Weapon;
import game.model.WeaponAttributeBonus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeaponAttributeBonusDao {
    protected ConnectionManager connectionManager;

    private static WeaponAttributeBonusDao instance = null;

    protected WeaponAttributeBonusDao() {
        connectionManager = new ConnectionManager();
    }

    public static WeaponAttributeBonusDao getInstance() {
        if (instance == null) {
            instance = new WeaponAttributeBonusDao();
        }
        return instance;
    }

    // Create a new WeaponAttributeBonus
    public WeaponAttributeBonus create(WeaponAttributeBonus bonus) throws SQLException {
        String insertBonus = "INSERT INTO WeaponAttributeBonus(item_id, attribute, bonus_value) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertBonus);
            insertStmt.setInt(1, bonus.getWeapon().getItemId());
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

    // Retrieve a WeaponAttributeBonus by item_id and attribute
    public WeaponAttributeBonus getWeaponAttributeBonus(int itemId, String attribute) throws SQLException {
        String selectBonus = "SELECT item_id, attribute, bonus_value FROM WeaponAttributeBonus WHERE item_id = ? AND attribute = ?;";
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
                WeaponDao weaponDao = WeaponDao.getInstance();
                Weapon weapon = weaponDao.getWeaponByItemId(itemId);
                return new WeaponAttributeBonus(weapon, attribute, bonusValue);
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

    // Retrieve all WeaponAttributeBonus records for a specific item_id
    public List<WeaponAttributeBonus> getWeaponAttributeBonusesByItemId(int itemId) throws SQLException {
        List<WeaponAttributeBonus> bonuses = new ArrayList<>();
        String selectBonuses = "SELECT item_id, attribute, bonus_value FROM WeaponAttributeBonus WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectBonuses);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            WeaponDao weaponDao = WeaponDao.getInstance();
            Weapon weapon = weaponDao.getWeaponByItemId(itemId);
            while (results.next()) {
                String attribute = results.getString("attribute");
                int bonusValue = results.getInt("bonus_value");
                bonuses.add(new WeaponAttributeBonus(weapon, attribute, bonusValue));
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

    // Delete a WeaponAttributeBonus by item_id and attribute
    public WeaponAttributeBonus delete(WeaponAttributeBonus bonus) throws SQLException {
        String deleteBonus = "DELETE FROM WeaponAttributeBonus WHERE item_id = ? AND attribute = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteBonus);
            deleteStmt.setInt(1, bonus.getWeapon().getItemId());
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
