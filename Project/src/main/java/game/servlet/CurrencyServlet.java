package game.servlet;

import game.dal.*;
import game.model.*;
import game.model.Character;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/currency")
public class CurrencyServlet extends BasicServlet {

    private CurrencyDao currencyDao;
    private CharacterDao characterDao;
    private CharacterCurrencyDao characterCurrencyDao;

    @Override
    public void init() throws ServletException {
        currencyDao = CurrencyDao.getInstance();
        characterDao = CharacterDao.getInstance();
        characterCurrencyDao = CharacterCurrencyDao.getInstance();
    }

    protected void createCurrency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String currencyName = request.getParameter("currencyName");
            int cap = Integer.parseInt(request.getParameter("cap"));
            boolean isContinued = Boolean.parseBoolean(request.getParameter("isContinued"));

            if (currencyName == null || currencyName.trim().isEmpty()) {
                messages.put("success", "Currency name cannot be empty.");
            } else {
                Currency currency = new Currency(currencyName, cap, isContinued);
                currencyDao.create(currency);
                messages.put("success", "Successfully created currency: " + currencyName);
            }
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to create currency.");
        }

        request.getRequestDispatcher("/CurrencyCreate.jsp").forward(request, response);
    }

    protected void deleteCurrency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String currencyName = request.getParameter("currencyName");

            if (currencyName == null || currencyName.trim().isEmpty()) {
                messages.put("success", "Currency name cannot be empty.");
            } else {
                Currency currency = currencyDao.getCurrencyByName(currencyName);
                if (currency == null) {
                    messages.put("success", "Currency with name '" + currencyName + "' does not exist.");
                } else {
                    currencyDao.delete(currency);
                    messages.put("success", "Successfully deleted currency: " + currencyName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to delete currency.");
        }

        request.getRequestDispatcher("/CurrencyDelete.jsp").forward(request, response);
    }

    protected void createCharacterCurrency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Map<String, String> messages = new HashMap<>();
	    request.setAttribute("messages", messages);
	
	    try {
	        int characterId = Integer.parseInt(request.getParameter("characterId"));
	        String currencyId = request.getParameter("currencyId");
	        int weeklyCap = Integer.parseInt(request.getParameter("weeklyCap"));
	        int amount = Integer.parseInt(request.getParameter("amount"));
	
	        Character character = characterDao.getCharacterById(characterId);
	        if (character == null) {
	            messages.put("success", "Character with ID " + characterId + " does not exist.");
	        } else {
	            Currency currency = currencyDao.getCurrencyByName(currencyId);
	            if (currency == null) {
	                messages.put("success", "Currency " + currencyId + " does not exist.");
	            } else {
	                CharacterCurrency characterCurrency = new CharacterCurrency(characterId, currencyId, weeklyCap, amount);
	                characterCurrencyDao.create(characterCurrency);
	                messages.put("success", "Successfully created CharacterCurrency for character ID: " + characterId + " and currency: " + currencyId);
	            }
	        }
	    } catch (NumberFormatException e) {
	        messages.put("success", "Invalid number format. Please check your inputs.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        messages.put("success", "Database error: Unable to create CharacterCurrency.");
	    }

	    request.getRequestDispatcher("/CharacterCurrencyCreate.jsp").forward(request, response);
}

    protected void deleteCharacterCurrency(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            String currencyId = request.getParameter("currencyId");

            CharacterCurrency characterCurrency = characterCurrencyDao.getCharacterCurrency(characterId, currencyId);
            if (characterCurrency == null) {
                messages.put("success", "CharacterCurrency for character ID " + characterId + " and currency " + currencyId + " does not exist.");
            } else {
                // delete
                characterCurrencyDao.delete(characterCurrency);
                messages.put("success", "Successfully deleted CharacterCurrency for character ID: " + characterId + " and currency: " + currencyId);
            }
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to delete CharacterCurrency.");
        }

        request.getRequestDispatcher("/CharacterCurrencyDelete.jsp").forward(request, response);
    }
}

