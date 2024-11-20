package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.CharacterJob;
import game.model.Job;
import game.model.Character;

public class CharacterJobDao {
    private ConnectionManager connectionManager;
    private CharacterDao characterDao;
    private JobDao jobDao;
    private static CharacterJobDao instance = null; 
    
    private CharacterJobDao() {
        connectionManager = new ConnectionManager();
        characterDao = CharacterDao.getInstance();
        jobDao = JobDao.getInstance();
    }
    
    public static CharacterJobDao getInstance() {
 		if(instance == null) {
 			instance = new CharacterJobDao();
 		}
 		return instance;
 	}
    
    // Create
    public CharacterJob create(CharacterJob characterJob) throws SQLException {
        // Verify that both character and job exist
        Character character = characterDao.getCharacterById(characterJob.getCharacterId());
        if (character == null) {
            throw new SQLException("Character with ID " + characterJob.getCharacterId() + " does not exist");
        }
        
        Job job = jobDao.getJobByName(characterJob.getJobName());
        if (job == null) {
            throw new SQLException("Job " + characterJob.getJobName() + " does not exist");
        }
        
        String insertSql = "INSERT INTO CharacterJob(character_id, job_name, job_level, current_exp, threshold) " +
                          "VALUES(?,?,?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, characterJob.getCharacterId());
            pstmt.setString(2, characterJob.getJobName());
            pstmt.setInt(3, characterJob.getJobLevel());
            pstmt.setInt(4, characterJob.getCurrentExp());
            pstmt.setInt(5, characterJob.getThreshold());
            pstmt.executeUpdate();
            
            characterJob.setCharacter(character);
            characterJob.setJob(job);
            
            return characterJob;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by compound primary key
    public CharacterJob getCharacterJob(int characterId, String jobName) throws SQLException {
        String selectSql = "SELECT character_id, job_name, job_level, current_exp, threshold " +
                          "FROM CharacterJob WHERE character_id=? AND job_name=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, characterId);
            pstmt.setString(2, jobName);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                CharacterJob cj = new CharacterJob(
                    rs.getInt("character_id"),
                    rs.getString("job_name"),
                    rs.getInt("job_level"),
                    rs.getInt("current_exp"),
                    rs.getInt("threshold")
                );
                cj.setCharacter(characterDao.getCharacterById(characterId));
                cj.setJob(jobDao.getJobByName(jobName));
                return cj;
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Get all jobs for a character
    public List<CharacterJob> getJobsByCharacter(int characterId) throws SQLException {
        List<CharacterJob> characterJobs = new ArrayList<>();
        String selectSql = "SELECT character_id, job_name, job_level, current_exp, threshold " +
                          "FROM CharacterJob WHERE character_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, characterId);
            rs = pstmt.executeQuery();
            
            Character character = characterDao.getCharacterById(characterId);
            
            while(rs.next()) {
                CharacterJob cj = new CharacterJob(
                    rs.getInt("character_id"),
                    rs.getString("job_name"),
                    rs.getInt("job_level"),
                    rs.getInt("current_exp"),
                    rs.getInt("threshold")
                );
                cj.setCharacter(character);
                cj.setJob(jobDao.getJobByName(cj.getJobName()));
                characterJobs.add(cj);
            }
            return characterJobs;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update job level and exp
    public CharacterJob updateLevelAndExp(CharacterJob characterJob, 
                                        int newLevel, int newExp, int newThreshold) throws SQLException {
        String updateSql = "UPDATE CharacterJob SET job_level=?, current_exp=?, threshold=? " +
                          "WHERE character_id=? AND job_name=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newLevel);
            pstmt.setInt(2, newExp);
            pstmt.setInt(3, newThreshold);
            pstmt.setInt(4, characterJob.getCharacterId());
            pstmt.setString(5, characterJob.getJobName());
            pstmt.executeUpdate();
            
            characterJob.setJobLevel(newLevel);
            characterJob.setCurrentExp(newExp);
            characterJob.setThreshold(newThreshold);
            return characterJob;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(CharacterJob characterJob) throws SQLException {
        String deleteSql = "DELETE FROM CharacterJob WHERE character_id=? AND job_name=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, characterJob.getCharacterId());
            pstmt.setString(2, characterJob.getJobName());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 