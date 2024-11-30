package game.servlet;

import game.dal.*;
import game.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/charactercurrencyupdate")
public class CharacterCurrencyUpdate extends HttpServlet {
	
	protected CharacterCurrencyDao characterCurrencyDao;
	
	@Override
	public void init() throws ServletException {
		characterCurrencyDao = CharacterCurrencyDao.getInstance();
	}
	
	
	// For getting information of an attribute by characterId
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve character currency and validate.
        String characterId = req.getParameter("characterId");
        String currencyId = req.getParameter("currencyId");
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Character id cannot be empty.");
        } else if (currencyId == null || currencyId.trim().isEmpty()) {
        	messages.put("success", "Currency id cannot be empty.");
        } else {
			int charId = Integer.valueOf(characterId);
			try {
				CharacterCurrency characterCurrency = characterCurrencyDao.getCharacterCurrency(charId, currencyId);
				if(characterCurrency == null) {
					messages.put("success", "CharacterCurrency does not exist.");
				}
				req.setAttribute("characterCurrency", characterCurrency);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        
        req.getRequestDispatcher("/CharacterCurrencyUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Validate input newAmount value
        String newAmountStr = req.getParameter("newAmount");
        if (newAmountStr == null || newAmountStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid new amount value.");
            req.getRequestDispatcher("/CharacterCurrencyUpdate.jsp").forward(req, resp);
            return;
        }
		
		int newAmount;
    	// Validate input strength is numeric
    	try {
    		newAmount = Integer.valueOf(newAmountStr);
    		// Validate the strength value non-negative
        	if (newAmount < 0) {
        		messages.put("success", "Amount must be non-negative");
        		req.getRequestDispatcher("/CharacterCurrencyUpdate.jsp").forward(req, resp);
        		return;
        	}
    	} catch (NumberFormatException e){
    		messages.put("success", "Amount must be a number.");
    		req.getRequestDispatcher("/CharacterCurrencyUpdate.jsp").forward(req, resp);
    		return;
    	}


    	// Retrieve character currency and validate.
        String characterId = req.getParameter("characterId");
        String currencyId = req.getParameter("currencyId");
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Character id cannot be empty.");
        } else if (currencyId == null || currencyId.trim().isEmpty()) {
        	messages.put("success", "Currency id cannot be empty.");
        } else {
			int charId = Integer.valueOf(characterId);
			try {
				CharacterCurrency characterCurrency = characterCurrencyDao.getCharacterCurrency(charId, currencyId);
				if(characterCurrency == null) {
					messages.put("success", "CharacterCurrency does not exist. No update to preform.");
				} else {
					characterCurrency = characterCurrencyDao.updateAmount(characterCurrency, newAmount);
					messages.put("success", "Successfullt updated " + newAmount);
				}
        		
        		req.setAttribute("characterCurrency", characterCurrency);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterCurrencyUpdate.jsp").forward(req, resp);
    }
}
