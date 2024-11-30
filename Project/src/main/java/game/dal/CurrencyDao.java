package game.dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import game.model.Currency;

public class CurrencyDao {
    protected ConnectionManager connectionManager;

    private static CurrencyDao instance = null;

    protected CurrencyDao() {
        connectionManager = new ConnectionManager();
    }

    public static CurrencyDao getInstance() {
        if (instance == null) {
            instance = new CurrencyDao();
        }
        return instance;
    }

    // Create a new Currency
    public Currency create(Currency currency) throws SQLException {
        String insertCurrency = "INSERT INTO Currency(currency_name, cap, isContinued) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCurrency);
            insertStmt.setString(1, currency.getCurrencyName());
            insertStmt.setInt(2, currency.getCap());
            insertStmt.setBoolean(3, currency.isContinued());
            insertStmt.executeUpdate();
            return currency;
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

    // Retrieve a Currency by currency_name
    public Currency getCurrencyByName(String currencyName) throws SQLException {
        String selectCurrency = "SELECT currency_name, cap, isContinued FROM Currency WHERE currency_name = ?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCurrency);
            selectStmt.setString(1, currencyName);
            results = selectStmt.executeQuery();
            if (results.next()) {
                String resultCurrencyName = results.getString("currency_name");
                int cap = results.getInt("cap");
                boolean isContinued = results.getBoolean("isContinued");
                return new Currency(resultCurrencyName, cap, isContinued);
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

    // Retrieve all active Currencies
    public List<Currency> getActiveCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String selectCurrencies = "SELECT currency_name, cap, isContinued FROM Currency WHERE isContinued = TRUE;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCurrencies);
            results = selectStmt.executeQuery();
            while (results.next()) {
                String currencyName = results.getString("currency_name");
                int cap = results.getInt("cap");
                boolean isContinued = results.getBoolean("isContinued");
                currencies.add(new Currency(currencyName, cap, isContinued));
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
        return currencies;
    }

    public List<Currency> getCurrencies() throws SQLException {
        List<Currency> currencies = new ArrayList<>();
        String selectCurrencies = "SELECT currency_name, cap, isContinued FROM Currency;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCurrencies);
            results = selectStmt.executeQuery();
            while (results.next()) {
                String currencyName = results.getString("currency_name");
                int cap = results.getInt("cap");
                boolean isContinued = results.getBoolean("isContinued");
                currencies.add(new Currency(currencyName, cap, isContinued));
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
        return currencies;
    }

    // Update the cap for a Currency
    public Currency updateCap(Currency currency, int newCap) throws SQLException {
        String updateCurrency = "UPDATE Currency SET cap = ? WHERE currency_name = ?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateCurrency);
            updateStmt.setInt(1, newCap);
            updateStmt.setString(2, currency.getCurrencyName());
            updateStmt.executeUpdate();
            currency.setCap(newCap);
            return currency;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    // Delete a Currency
    public Currency delete(Currency currency) throws SQLException {
        String deleteCurrency = "DELETE FROM Currency WHERE currency_name = ?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCurrency);
            deleteStmt.setString(1, currency.getCurrencyName());
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