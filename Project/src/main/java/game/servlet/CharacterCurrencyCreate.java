package game.servlet;

import game.dal.*;
import game.model.*;
import game.model.Character;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/charactercurrencycreate")
public class CharacterCurrencyCreate extends HttpServlet {

    private CharacterDao characterDao;
    private CurrencyDao currencyDao;
    private CharacterCurrencyDao characterCurrencyDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
        currencyDao = CurrencyDao.getInstance();
        characterCurrencyDao = CharacterCurrencyDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Create Character Currency");
        request.getRequestDispatcher("/CharacterCurrencyCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
