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


@WebServlet("/attributeupdate")
public class AttributeUpdate extends HttpServlet {
	
	protected AttributeDao attributeDao;
	
	@Override
	public void init() throws ServletException {
		attributeDao = AttributeDao.getInstance();
	}
	
	
	// For getting information of an attribute by characterId
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve attribute and validate.
        String characterId = req.getParameter("characterId");
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "CharacterId cannot be empty.");
        } else {
    		int charId = Integer.valueOf(characterId);
        	try {
        		Attribute attribute = attributeDao.getAttributeByCharacterId(charId);
        		if(attribute == null) {
        			messages.put("success", "Attribute does not exist.");
        		}
        		req.setAttribute("attribute", attribute);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/AttributeUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Validate input strength value
        String newStrength = req.getParameter("newStrength");
        if (newStrength == null || newStrength.trim().isEmpty()) {
            messages.put("success", "Please enter a valid strength value.");
            req.getRequestDispatcher("/AttributeUpdate.jsp").forward(req, resp);
            return;
        }
		
		int newStrengthValue;
    	// Validate input strength is numeric
    	try {
    		newStrengthValue = Integer.valueOf(newStrength);
    		// Validate the strength value non-negative
        	if (newStrengthValue < 0) {
        		messages.put("success", "Strength must be non-negative");
        		req.getRequestDispatcher("/AttributeUpdate.jsp").forward(req, resp);
        		return;
        	}
    	} catch (NumberFormatException e){
    		messages.put("success", "Strength must be a number.");
    		req.getRequestDispatcher("/AttributeUpdate.jsp").forward(req, resp);
    		return;
    	}


        // Retrieve character and validate.
        String characterId = req.getParameter("characterId");
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid characterId.");
        } else {
        	int charId = Integer.valueOf(characterId);
        	try {
        		// Get the attribute
        		Attribute attribute = attributeDao.getAttributeByCharacterId(charId);
        		if(attribute == null) {
        			messages.put("success", "Attribute does not exist. No update to perform.");
        		} else {
    	        	attribute = attributeDao.updateStrength(attribute, newStrengthValue);
    	        	messages.put("success", "Successfully updated attribute strength.");
    	        }
        		
        		req.setAttribute("attribute", attribute);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/AttributeUpdate.jsp").forward(req, resp);
    }
}
