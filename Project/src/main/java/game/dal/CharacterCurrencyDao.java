package game.dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.CharacterCurrency;
import game.model.Currency;
import game.model.Character;

public class CharacterCurrencyDao {
    private ConnectionManager connectionManager;
    private CharacterDao characterDao;
    private CurrencyDao currencyDao;
    private static CharacterCurrencyDao instance = null;
    
    private CharacterCurrencyDao() {
        connectionManager = new ConnectionManager();
        characterDao = CharacterDao.getInstance();
        currencyDao = CurrencyDao.getInstance();
    }
    
    public static CharacterCurrencyDao getInstance() {
		if(instance == null) {
			instance = new CharacterCurrencyDao();
		}
		return instance;
	}
    
    // Create
    public CharacterCurrency create(CharacterCurrency characterCurrency) throws SQLException {
        // Verify that both character and currency exist
        Character character = characterDao.getCharacterById(characterCurrency.getCharacterId());
        if (character == null) {
            throw new SQLException("Character with ID " + characterCurrency.getCharacterId() + " does not exist");
        }
        
        Currency currency = currencyDao.getCurrencyByName(characterCurrency.getCurrencyId());
        if (currency == null) {
            throw new SQLException("Currency " + characterCurrency.getCurrencyId() + " does not exist");
        }
        
        String insertSql = "INSERT INTO CharacterCurrency(character_id, currency_id, weekly_cap, amount) " +
                          "VALUES(?,?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setInt(1, characterCurrency.getCharacterId());
            pstmt.setString(2, characterCurrency.getCurrencyId());
            pstmt.setInt(3, characterCurrency.getWeeklyCap());
            pstmt.setInt(4, characterCurrency.getAmount());
            pstmt.executeUpdate();
            
            characterCurrency.setCharacter(character);
            characterCurrency.setCurrency(currency);
            
            return characterCurrency;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by compound primary key
    public CharacterCurrency getCharacterCurrency(int characterId, String currencyId) throws SQLException {
        String selectSql = "SELECT character_id, currency_id, weekly_cap, amount " +
                          "FROM CharacterCurrency WHERE character_id=? AND currency_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setInt(1, characterId);
            pstmt.setString(2, currencyId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                CharacterCurrency cc = new CharacterCurrency(
                    rs.getInt("character_id"),
                    rs.getString("currency_id"),
                    rs.getInt("weekly_cap"),
                    rs.getInt("amount")
                );
                cc.setCharacter(characterDao.getCharacterById(characterId));
                cc.setCurrency(currencyDao.getCurrencyByName(currencyId));
                return cc;
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Get all currencies for a character
    public List<CharacterCurrency> getCurrenciesByCharacter(int characterId) throws SQLException {
        List<CharacterCurrency> characterCurrencies = new ArrayList<>();
        String selectSql = "SELECT character_id, currency_id, weekly_cap, amount " +
                          "FROM CharacterCurrency WHERE character_id=?;";
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
                CharacterCurrency cc = new CharacterCurrency(
                    rs.getInt("character_id"),
                    rs.getString("currency_id"),
                    rs.getInt("weekly_cap"),
                    rs.getInt("amount")
                );
                cc.setCharacter(character);
                cc.setCurrency(currencyDao.getCurrencyByName(cc.getCurrencyId()));
                characterCurrencies.add(cc);
            }
            return characterCurrencies;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Update amount
    public CharacterCurrency updateAmount(CharacterCurrency characterCurrency, int newAmount) throws SQLException {
        String updateSql = "UPDATE CharacterCurrency SET amount=? WHERE character_id=? AND currency_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newAmount);
            pstmt.setInt(2, characterCurrency.getCharacterId());
            pstmt.setString(3, characterCurrency.getCurrencyId());
            pstmt.executeUpdate();
            
            characterCurrency.setAmount(newAmount);
            return characterCurrency;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(CharacterCurrency characterCurrency) throws SQLException {
        String deleteSql = "DELETE FROM CharacterCurrency WHERE character_id=? AND currency_id=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, characterCurrency.getCharacterId());
            pstmt.setString(2, characterCurrency.getCurrencyId());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 