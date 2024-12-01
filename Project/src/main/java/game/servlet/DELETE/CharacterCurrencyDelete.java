package game.servlet;

import game.dal.*;
import game.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/charactercurrencydelete")
public class CharacterCurrencyDelete extends HttpServlet {

    private CharacterCurrencyDao characterCurrencyDao;

    @Override
    public void init() throws ServletException {
        characterCurrencyDao = CharacterCurrencyDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Delete Character Currency");
        request.getRequestDispatcher("/CharacterCurrencyDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            String currencyId = request.getParameter("currencyId");

            CharacterCurrency characterCurrency = characterCurrencyDao.getCharacterCurrency(characterId, currencyId);
            if (characterCurrency == null) {
                messages.put("success", "CharacterCurrency for character ID " + characterId + " and currency " + currencyId + " does not exist.");
            } else {
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
