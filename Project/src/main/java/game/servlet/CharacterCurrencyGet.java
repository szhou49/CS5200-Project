package game.servlet;

import game.dal.CharacterCurrencyDao;
import game.model.CharacterCurrency;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CharacterCurrencyGet is a servlet to retrieve CharacterCurrency data.
 */
@WebServlet("/characterurrencyget")
public class CharacterCurrencyGet extends HttpServlet {

    protected CharacterCurrencyDao characterCurrencyDao;

    @Override
    public void init() throws ServletException {
        characterCurrencyDao = CharacterCurrencyDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<CharacterCurrency> characterCurrencies = new ArrayList<>();

        String characterIdStr = req.getParameter("characterId");
        if (characterIdStr == null || characterIdStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid character ID.");
        } else {
            try {
                int characterId = Integer.parseInt(characterIdStr);
                characterCurrencies = characterCurrencyDao.getCurrenciesByCharacter(characterId);
                if (characterCurrencies.isEmpty()) {
                    messages.put("success", "No currencies found for character ID " + characterId);
                } else {
                    messages.put("success", "Displaying currencies for character ID " + characterId);
                }
            } catch (NumberFormatException e) {
                messages.put("success", "Character ID must be a valid number.");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }

        req.setAttribute("characterCurrencies", characterCurrencies);
        req.getRequestDispatcher("/CharacterCurrencyGet.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}