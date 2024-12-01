package game.dal;

import game.model.Character;
import game.model.CharacterJob;
import game.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterJobDao {
    protected ConnectionManager connectionManager;

    private static CharacterJobDao instance = null;

    protected CharacterJobDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterJobDao getInstance() {
        if (instance == null) {
            instance = new CharacterJobDao();
        }
        return instance;
    }

    // Create a new CharacterJob
    public CharacterJob create(CharacterJob characterJob) throws SQLException {
        String insertCharacterJob = "INSERT INTO CharacterJob(character_id, job_name, job_level, current_exp, threshold) VALUES(?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacterJob);
            insertStmt.setInt(1, characterJob.getCharacter().getCharacterId());
            insertStmt.setString(2, characterJob.getJob().getJobName());
            insertStmt.executeUpdate();
            return characterJob;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
        }
    }

    // Retrieve a CharacterJob by character_id and job_name
    public CharacterJob getCharacterJob(int characterId, String jobName) throws SQLException {
        String selectCharacterJob = "SELECT character_id, job_name, job_level, current_exp, threshold FROM CharacterJob WHERE character_id = ? AND job_name = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterJob);
            selectStmt.setInt(1, characterId);
            selectStmt.setString(2, jobName);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int jobLevel = results.getInt("job_level");
                int currentExp = results.getInt("current_exp");
                int threshold = results.getInt("threshold");

                CharacterDao characterDao = CharacterDao.getInstance();
                JobDao jobDao = JobDao.getInstance();

                Character character = characterDao.getCharacterById(characterId);
                Job job = jobDao.getJobByName(jobName);

                return new CharacterJob(jobLevel, currentExp, threshold, character, job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return null;
    }

    // Retrieve all CharacterJobs for a specific character_id
    public List<CharacterJob> getCharacterJobsByCharacterId(int characterId) throws SQLException {
        List<CharacterJob> characterJobs = new ArrayList<>();
        String selectCharacterJobs = "SELECT character_id, job_name, job_level, current_exp, threshold FROM CharacterJob WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterJobs);
            selectStmt.setInt(1, characterId);
            results = selectStmt.executeQuery();

            CharacterDao characterDao = CharacterDao.getInstance();
            JobDao jobDao = JobDao.getInstance();
            Character character = characterDao.getCharacterById(characterId);

            while (results.next()) {
                String jobName = results.getString("job_name");
                int jobLevel = results.getInt("job_level");
                int currentExp = results.getInt("current_exp");
                int threshold = results.getInt("threshold");

                Job job = jobDao.getJobByName(jobName);
                characterJobs.add(new CharacterJob(jobLevel, currentExp, threshold, character, job));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return characterJobs;
    }

    // Delete a CharacterJob
    public CharacterJob delete(CharacterJob characterJob) throws SQLException {
        String deleteCharacterJob = "DELETE FROM CharacterJob WHERE character_id = ? AND job_name = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCharacterJob);
            deleteStmt.setInt(1, characterJob.getCharacter().getCharacterId());
            deleteStmt.setString(2, characterJob.getJob().getJobName());
            deleteStmt.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (deleteStmt != null) deleteStmt.close();
        }
    }
}
