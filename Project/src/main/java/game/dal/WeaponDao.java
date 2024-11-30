package game.dal;

import game.model.Weapon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeaponDao {
    protected ConnectionManager connectionManager;

    private static WeaponDao instance = null;

    protected WeaponDao() {
        connectionManager = new ConnectionManager();
    }

    public static WeaponDao getInstance() {
        if (instance == null) {
            instance = new WeaponDao();
        }
        return instance;
    }

    // Create a new Weapon
    public Weapon create(Weapon weapon) throws SQLException {
        String insertWeapon = "INSERT INTO Weapon(item_id, required_level, damage, auto_attack, attack_delay) VALUES(?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertWeapon);
            insertStmt.setInt(1, weapon.getItemId());
            insertStmt.setInt(2, weapon.getRequiredLevel());
            insertStmt.setInt(3, weapon.getDamage());
            insertStmt.setDouble(4, weapon.getAutoAttack());
            insertStmt.setDouble(5, weapon.getAttackDelay());
            insertStmt.executeUpdate();
            ItemDao.getInstance().create(weapon);
            return weapon;
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

    // Retrieve a Weapon by item_id
    public Weapon getWeaponByItemId(int itemId) throws SQLException {
        String selectWeapon = "SELECT item_id, required_level, damage, auto_attack, attack_delay FROM Weapon WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWeapon);
            selectStmt.setInt(1, itemId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int requiredLevel = results.getInt("required_level");
                int damage = results.getInt("damage");
                double autoAttack = results.getDouble("auto_attack");
                double attackDelay = results.getDouble("attack_delay");
                return new Weapon(ItemDao.getInstance().getItemById(itemId), requiredLevel, damage, autoAttack, attackDelay);
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

    // Retrieve all Weapons
    public List<Weapon> getAllWeapons() throws SQLException {
        List<Weapon> weapons = new ArrayList<>();
        String selectWeapons = "SELECT item_id, required_level, damage, auto_attack, attack_delay FROM Weapon;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectWeapons);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int itemId = results.getInt("item_id");
                int requiredLevel = results.getInt("required_level");
                int damage = results.getInt("damage");
                double autoAttack = results.getDouble("auto_attack");
                double attackDelay = results.getDouble("attack_delay");
                weapons.add(new Weapon(ItemDao.getInstance().getItemById(itemId), requiredLevel, damage, autoAttack, attackDelay));
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
        return weapons;
    }

    // Delete a Weapon
    public Weapon delete(Weapon weapon) throws SQLException {
        String deleteWeapon = "DELETE FROM Weapon WHERE item_id = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteWeapon);
            deleteStmt.setInt(1, weapon.getItemId());
            deleteStmt.executeUpdate();
            ItemDao.getInstance().delete(weapon);
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
