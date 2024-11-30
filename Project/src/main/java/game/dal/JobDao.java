package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Job;

public class JobDao {
    private ConnectionManager connectionManager;
    private static JobDao instance = null;
    
    private JobDao() {
        connectionManager = new ConnectionManager();
    }
    public static JobDao getInstance() {
		if(instance == null) {
			instance = new JobDao();
		}
		return instance;	
	}
    
    // Create
    public Job create(Job job) throws SQLException {
        String insertSql = "INSERT INTO Job(job_name) VALUES(?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, job.getJobName());
            pstmt.executeUpdate();
            return job;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by primary key
    public Job getJobByName(String jobName) throws SQLException {
        String selectSql = "SELECT job_name FROM Job WHERE job_name=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, jobName);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return new Job(rs.getString("job_name"));
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Get all jobs
    public List<Job> getAllJobs() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String selectSql = "SELECT job_name FROM Job;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                jobs.add(new Job(rs.getString("job_name")));
            }
            return jobs;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Job job) throws SQLException {
        String deleteSql = "DELETE FROM Job WHERE job_name=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setString(1, job.getJobName());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 