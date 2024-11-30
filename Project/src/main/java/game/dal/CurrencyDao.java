package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Currency;

public class CurrencyDao {
    private ConnectionManager connectionManager;
    private static CurrencyDao instance = null;
    
    private CurrencyDao() {
        connectionManager = new ConnectionManager();
    }
    public static CurrencyDao getInstance() {
		if(instance == null) {
			instance = new CurrencyDao();
		}
		return instance;
	}
    
    
    // Create
    public Currency create(Currency currency) throws SQLException {
        String insertSql = "INSERT INTO Currency(currency_name, cap, isContinued) VALUES(?,?,?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, currency.getCurrencyName());
            pstmt.setInt(2, currency.getCap());
            pstmt.setBoolean(3, currency.getIsContinued());
            pstmt.executeUpdate();
            return currency;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Read by primary key
    public Currency getCurrencyByName(String currencyName) throws SQLException {
        String selectSql = "SELECT currency_name, cap, isContinued FROM Currency WHERE currency_name=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, currencyName);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return new Currency(
                    rs.getString("currency_name"),
                    rs.getInt("cap"),
                    rs.getBoolean("isContinued")
                );
            }
            return null;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Get all active currencies
    public List<Currency> getActiveCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String selectSql = "SELECT currency_name, cap, isContinued FROM Currency WHERE isContinued=TRUE;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                currencies.add(new Currency(
                    rs.getString("currency_name"),
                    rs.getInt("cap"),
                    rs.getBoolean("isContinued")
                ));
            }
            return currencies;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Get all currencies
    public List<Currency> getCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String selectSql = "SELECT currency_name, cap, isContinued FROM Currency;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(selectSql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                currencies.add(new Currency(
                    rs.getString("currency_name"),
                    rs.getInt("cap"),
                    rs.getBoolean("isContinued")
                ));
            }
            return currencies;
        } finally {
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }

    // Update cap
    public Currency updateCap(Currency currency, int newCap) throws SQLException {
        String updateSql = "UPDATE Currency SET cap=? WHERE currency_name=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(updateSql);
            pstmt.setInt(1, newCap);
            pstmt.setString(2, currency.getCurrencyName());
            pstmt.executeUpdate();
            currency.setCap(newCap);
            return currency;
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
    
    // Delete
    public void delete(Currency currency) throws SQLException {
        String deleteSql = "DELETE FROM Currency WHERE currency_name=?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connectionManager.getConnection();
            pstmt = conn.prepareStatement(deleteSql);
            pstmt.setString(1, currency.getCurrencyName());
            pstmt.executeUpdate();
        } finally {
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }
    }
} 