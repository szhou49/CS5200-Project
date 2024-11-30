package game.dal;

import game.model.Job;
import game.model.Weapon;
import game.model.JobWeapon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobWeaponDao {
    protected ConnectionManager connectionManager;

    private static JobWeaponDao instance = null;

    protected JobWeaponDao() {
        connectionManager = new ConnectionManager();
    }

    public static JobWeaponDao getInstance() {
        if (instance == null) {
            instance = new JobWeaponDao();
        }
        return instance;
    }

    // Create a new JobWeapon
    public JobWeapon create(JobWeapon jobWeapon) throws SQLException {
        String insertJobWeapon = "INSERT INTO JobWeapon(item_id, job_name) VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertJobWeapon);
            insertStmt.setInt(1, jobWeapon.getWeapon().getItemId());
            insertStmt.setString(2, jobWeapon.getJob().getJobName());
            insertStmt.executeUpdate();
            return jobWeapon;
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

    // Retrieve a JobWeapon by item_id and job_name
    public JobWeapon getJobWeapon(int itemId, String jobName) throws SQLException {
        String selectJobWeapon = "SELECT item_id, job_name FROM JobWeapon WHERE item_id = ? AND job_name = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectJobWeapon);
            selectStmt.setInt(1, itemId);
            selectStmt.setString(2, jobName);
            results = selectStmt.executeQuery();
            if (results.next()) {
                WeaponDao weaponDao = WeaponDao.getInstance();
                JobDao jobDao = JobDao.getInstance();
                Weapon weapon = weaponDao.getWeaponByItemId(itemId);
                Job job = jobDao.getJobByName(jobName);
                return new JobWeapon(weapon, job);
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

    // Retrieve all JobWeapon for a specific job_name
    public List<JobWeapon> getJobWeaponsByJobName(String jobName) throws SQLException {
        List<JobWeapon> jobWeapons = new ArrayList<>();
        String selectJobWeapons = "SELECT item_id, job_name FROM JobWeapon WHERE job_name = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectJobWeapons);
            selectStmt.setString(1, jobName);
            results = selectStmt.executeQuery();
            WeaponDao weaponDao = WeaponDao.getInstance();
            JobDao jobDao = JobDao.getInstance();
            while (results.next()) {
                int itemId = results.getInt("item_id");
                Weapon weapon = weaponDao.getWeaponByItemId(itemId);
                Job job = jobDao.getJobByName(jobName);
                jobWeapons.add(new JobWeapon(weapon, job));
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
        return jobWeapons;
    }

    // Delete a JobWeapon by item_id and job_name
    public JobWeapon delete(JobWeapon jobWeapon) throws SQLException {
        String deleteJobWeapon = "DELETE FROM JobWeapon WHERE item_id = ? AND job_name = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteJobWeapon);
            deleteStmt.setInt(1, jobWeapon.getWeapon().getItemId());
            deleteStmt.setString(2, jobWeapon.getJob().getJobName());
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
