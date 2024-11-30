package game.dal;

import game.model.Character;
import game.model.CharacterCurrency;
import game.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterCurrencyDao {
    protected ConnectionManager connectionManager;

    private static CharacterCurrencyDao instance = null;

    protected CharacterCurrencyDao() {
        connectionManager = new ConnectionManager();
    }

    public static CharacterCurrencyDao getInstance() {
        if (instance == null) {
            instance = new CharacterCurrencyDao();
        }
        return instance;
    }

    // Create a new CharacterCurrency
    public CharacterCurrency create(CharacterCurrency characterCurrency) throws SQLException {
        String insertCharacterCurrency = "INSERT INTO CharacterCurrency(character_id, currency_id, weekly_cap, amount) VALUES(?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCharacterCurrency);
            insertStmt.setInt(1, characterCurrency.getCharacter().getCharacterId());
            insertStmt.setString(2, characterCurrency.getCurrency().getCurrencyName());
            insertStmt.setInt(3, characterCurrency.getWeeklyCap());
            insertStmt.setInt(4, characterCurrency.getAmount());
            insertStmt.executeUpdate();
            return characterCurrency;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (insertStmt != null) insertStmt.close();
        }
    }

    // Retrieve a CharacterCurrency by character_id and currency_id
    public CharacterCurrency getCharacterCurrency(int characterId, String currencyId) throws SQLException {
        String selectCharacterCurrency = "SELECT character_id, currency_id, weekly_cap, amount FROM CharacterCurrency WHERE character_id = ? AND currency_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterCurrency);
            selectStmt.setInt(1, characterId);
            selectStmt.setString(2, currencyId);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int weeklyCap = results.getInt("weekly_cap");
                int amount = results.getInt("amount");

                CharacterDao characterDao = CharacterDao.getInstance();
                CurrencyDao currencyDao = CurrencyDao.getInstance();

                Character character = characterDao.getCharacterById(characterId);
                Currency currency = currencyDao.getCurrencyByName(currencyId);

                return new CharacterCurrency(weeklyCap, amount, character, currency);
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

    // Retrieve all CharacterCurrency records for a specific character_id
    public List<CharacterCurrency> getCharacterCurrenciesByCharacterId(int characterId) throws SQLException {
        List<CharacterCurrency> characterCurrencies = new ArrayList<>();
        String selectCharacterCurrencies = "SELECT character_id, currency_id, weekly_cap, amount FROM CharacterCurrency WHERE character_id = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCharacterCurrencies);
            selectStmt.setInt(1, characterId);
            results = selectStmt.executeQuery();

            CharacterDao characterDao = CharacterDao.getInstance();
            CurrencyDao currencyDao = CurrencyDao.getInstance();

            Character character = characterDao.getCharacterById(characterId);

            while (results.next()) {
                String currencyId = results.getString("currency_id");
                int weeklyCap = results.getInt("weekly_cap");
                int amount = results.getInt("amount");

                Currency currency = currencyDao.getCurrencyByName(currencyId);
                characterCurrencies.add(new CharacterCurrency(weeklyCap, amount, character, currency));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) connection.close();
            if (selectStmt != null) selectStmt.close();
            if (results != null) results.close();
        }
        return characterCurrencies;
    }

    // Delete a CharacterCurrency by character_id and currency_id
    public CharacterCurrency delete(CharacterCurrency characterCurrency) throws SQLException {
        String deleteCharacterCurrency = "DELETE FROM CharacterCurrency WHERE character_id = ? AND currency_id = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCharacterCurrency);
            deleteStmt.setInt(1, characterCurrency.getCharacter().getCharacterId());
            deleteStmt.setString(2, characterCurrency.getCurrency().getCurrencyName());
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
