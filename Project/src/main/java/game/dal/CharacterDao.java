package game.dal;

import game.model.Character;
import game.model.Player;
import game.model.Weapon;
import game.model.Attribute;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterDao {
    protected ConnectionManager connectionManager;

    private static CharacterDao instance = null;

    protected CharacterDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterDao getInstance() {
        if (instance == null) {
            instance = new CharacterDao();
        }
        return instance;
    }

    // Create a new Character
    public Character create(Character character) throws SQLException {
        String insertCharacter = "INSERT INTO `Character`(first_name, last_name, email_address, main_hand) VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacter, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, character.getFirstName());
            insertStmt.setString(2, character.getLastName());
            insertStmt.setString(3, character.getPlayer().getEmailAddress());
            insertStmt.setInt(4, character.getMainHandWeapon().getItemId());
            insertStmt.executeUpdate();

            resultKey = insertStmt.getGeneratedKeys();
            if (resultKey.next()) {
                character.setCharacterId(resultKey.getInt(1));
            }
            return character;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
            if (resultKey != null) resultKey.close();
        }
    }

    // Retrieve a Character by character_id
    public Character getCharacterById(int characterId) throws SQLException {
        String selectCharacter = "SELECT character_id, first_name, last_name, email_address, main_hand FROM `Character` WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacter);
            selectStmt.setInt(1, characterId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");
                String emailAddress = results.getString("email_address");
                int mainHandId = results.getInt("main_hand");

                PlayerDao playerDao = PlayerDao.getInstance();
                WeaponDao weaponDao = WeaponDao.getInstance();

                Player player = playerDao.getPlayerByEmail(emailAddress);
                Weapon mainHandWeapon = weaponDao.getWeaponByItemId(mainHandId);

                return new Character(characterId, firstName, lastName, player, mainHandWeapon);
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

    // Retrieve all Characters
    public List<Character> getAllCharacters() throws SQLException {
        List<Character> characters = new ArrayList<>();
        String selectCharacters = "SELECT character_id, first_name, last_name, email_address, main_hand FROM `Character`;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacters);
            results = selectStmt.executeQuery();

            PlayerDao playerDao = PlayerDao.getInstance();
            WeaponDao weaponDao = WeaponDao.getInstance();

            while (results.next()) {
                int characterId = results.getInt("character_id");
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");
                String emailAddress = results.getString("email_address");
                int mainHandId = results.getInt("main_hand");

                Player player = playerDao.getPlayerByEmail(emailAddress);
                Weapon mainHandWeapon = weaponDao.getWeaponByItemId(mainHandId);

                characters.add(new Character(characterId, firstName, lastName, player, mainHandWeapon));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return characters;
    }

    // Delete a Character
    public Character delete(Character character) throws SQLException {
        String deleteCharacter = "DELETE FROM `Character` WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCharacter);
            deleteStmt.setInt(1, character.getCharacterId());
            deleteStmt.executeUpdate();
            Attribute attribute = AttributeDao.getInstance().getAttributeByCharacterId(character.getCharacterId());
            AttributeDao.getInstance().delete(attribute);
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
