package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Job;


public class JobDao {
    protected ConnectionManager connectionManager;

    private static JobDao instance = null;

    protected JobDao() {
        connectionManager = new ConnectionManager();
    }

    public static JobDao getInstance() {
        if (instance == null) {
            instance = new JobDao();
        }
        return instance;
    }

    // Create a new Job
    public Job create(Job job) throws SQLException {
        String insertJob = "INSERT INTO Job(job_name) VALUES(?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertJob);
            insertStmt.setString(1, job.getJobName());
            insertStmt.executeUpdate();
            return job;
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

    // Retrieve a Job by jobName
    public Job getJobByName(String jobName) throws SQLException {
        String selectJob = "SELECT job_name FROM Job WHERE job_name = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectJob);
            selectStmt.setString(1, jobName);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String resultJobName = results.getString("job_name");
                return new Job(resultJobName);
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

    // Retrieve all Jobs
    public List<Job> getAllJobs() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String selectJobs = "SELECT job_name FROM Job;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectJobs);
            results = selectStmt.executeQuery();
            while (results.next()) {
                String jobName = results.getString("job_name");
                jobs.add(new Job(jobName));
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
        return jobs;
    }

    // Delete a Job
    public Job delete(Job job) throws SQLException {
        String deleteJob = "DELETE FROM Job WHERE job_name = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteJob);
            deleteStmt.setString(1, job.getJobName());
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