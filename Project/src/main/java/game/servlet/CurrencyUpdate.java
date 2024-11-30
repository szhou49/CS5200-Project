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


@WebServlet("/currencyupdate")
public class CurrencyUpdate extends HttpServlet {
	
	protected CurrencyDao currencyDao;
	
	@Override
	public void init() throws ServletException {
		currencyDao = CurrencyDao.getInstance();
	}
	
	
	// For getting information of an attribute by characterId
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve currency and validate.
        String currencyName = req.getParameter("currencyName");
        
        if (currencyName == null || currencyName.trim().isEmpty()) {
            messages.put("success", "CurrencyName cannot be empty.");
        } else {
			try {
				Currency currency = currencyDao.getCurrencyByName(currencyName);
				if(currency == null) {
					messages.put("success", "Currency does not exist.");
				}
				req.setAttribute("currency", currency);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        
        req.getRequestDispatcher("/CurrencyUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Validate input quantity value
        String newCapStr = req.getParameter("newCap");
        if (newCapStr == null || newCapStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid cap value.");
            req.getRequestDispatcher("/CurrencyUpdate.jsp").forward(req, resp);
            return;
        }
		
		int newCap;
    	// Validate input strength is numeric
    	try {
    		newCap = Integer.valueOf(newCapStr);
    		// Validate the strength value non-negative
        	if (newCap < 0) {
        		messages.put("success", "Cap must be non-negative");
        		req.getRequestDispatcher("/CurrencyUpdate.jsp").forward(req, resp);
        		return;
        	}
    	} catch (NumberFormatException e){
    		messages.put("success", "Cap must be a number.");
    		req.getRequestDispatcher("/CurrencyUpdate.jsp").forward(req, resp);
    		return;
    	}


    	// Retrieve currency and validate.
        String currencyName = req.getParameter("currencyName");
        
        if (currencyName == null || currencyName.trim().isEmpty()) {
            messages.put("success", "CurrencyName cannot be empty.");
        } else {
			try {
				Currency currency = currencyDao.getCurrencyByName(currencyName);
				if(currency == null) {
					messages.put("success", "Currency does not exist. No update to perform.");
				} else {
					currency = currencyDao.updateCap(currency, newCap);
					messages.put("success", "Successfully updated cap as to " + newCap);
				}
        		
        		req.setAttribute("currency", currency);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CurrencyUpdate.jsp").forward(req, resp);
    }
}
