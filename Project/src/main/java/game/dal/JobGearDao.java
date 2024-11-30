package game.dal;

import game.model.Job;
import game.model.Gear;
import game.model.JobGear;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobGearDao {
    protected ConnectionManager connectionManager;

    private static JobGearDao instance = null;

    protected JobGearDao() {
        connectionManager = new ConnectionManager();
    }

    public static JobGearDao getInstance() {
        if (instance == null) {
            instance = new JobGearDao();
        }
        return instance;
    }

    // Create a new JobGear
    public JobGear create(JobGear jobGear) throws SQLException {
        String insertJobGear = "INSERT INTO JobGear(item_id, job_name) VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertJobGear);
            insertStmt.setInt(1, jobGear.getGear().getItemId());
            insertStmt.setString(2, jobGear.getJob().getJobName());
            insertStmt.executeUpdate();
            return jobGear;
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

    // Retrieve a JobGear by item_id and job_name
    public JobGear getJobGear(int itemId, String jobName) throws SQLException {
        String selectJobGear = "SELECT item_id, job_name FROM JobGear WHERE item_id = ? AND job_name = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectJobGear);
            selectStmt.setInt(1, itemId);
            selectStmt.setString(2, jobName);
            results = selectStmt.executeQuery();
            if (results.next()) {
                GearDao gearDao = GearDao.getInstance();
                JobDao jobDao = JobDao.getInstance();
                Gear gear = gearDao.getGearByItemId(itemId);
                Job job = jobDao.getJobByName(jobName);
                return new JobGear(gear, job);
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

    // Retrieve all JobGear for a specific job_name
    public List<JobGear> getJobGearByJobName(String jobName) throws SQLException {
        List<JobGear> jobGears = new ArrayList<>();
        String selectJobGears = "SELECT item_id, job_name FROM JobGear WHERE job_name = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectJobGears);
            selectStmt.setString(1, jobName);
            results = selectStmt.executeQuery();
            GearDao gearDao = GearDao.getInstance();
            JobDao jobDao = JobDao.getInstance();
            while (results.next()) {
                int itemId = results.getInt("item_id");
                Gear gear = gearDao.getGearByItemId(itemId);
                Job job = jobDao.getJobByName(jobName);
                jobGears.add(new JobGear(gear, job));
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
        return jobGears;
    }

    // Delete a JobGear by item_id and job_name
    public JobGear delete(JobGear jobGear) throws SQLException {
        String deleteJobGear = "DELETE FROM JobGear WHERE item_id = ? AND job_name = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteJobGear);
            deleteStmt.setInt(1, jobGear.getGear().getItemId());
            deleteStmt.setString(2, jobGear.getJob().getJobName());
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
