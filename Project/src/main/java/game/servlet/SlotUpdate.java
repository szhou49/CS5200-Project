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


@WebServlet("/slotupdate")
public class SlotUpdate extends HttpServlet {
	
	protected SlotDao slotDao;
	
	@Override
	public void init() throws ServletException {
		slotDao = SlotDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve slot and validate.
        String characterId = req.getParameter("characterId");
        String slotIndexStr = req.getParameter("slotIndex");
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "CharacterId cannot be empty.");
        } else if (slotIndexStr == null || slotIndexStr.trim().isEmpty()) {
        	messages.put("success", "Slot index cannot be empty.");
        } else {
			int charId = Integer.valueOf(characterId);
			int slotIndex = Integer.valueOf(slotIndexStr);
			try {
				Slot slot = slotDao.getSlot(charId, slotIndex);
				if(slot == null) {
					messages.put("success", "Slot does not exist.");
				}
				req.setAttribute("slot", slot);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        
        req.getRequestDispatcher("/SlotUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Validate input quantity value
        String newQuantityStr = req.getParameter("newQuantity");
        if (newQuantityStr == null || newQuantityStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid quantity value.");
            req.getRequestDispatcher("/SlotUpdate.jsp").forward(req, resp);
            return;
        }
		
		int newQuantity;
    	// Validate input strength is numeric
    	try {
    		newQuantity = Integer.valueOf(newQuantityStr);
        	if (newQuantity < 0) {
        		messages.put("success", "Quantity must be non-negative");
        		req.getRequestDispatcher("/SlotUpdate.jsp").forward(req, resp);
        		return;
        	}
    	} catch (NumberFormatException e){
    		messages.put("success", "Quantity must be a number.");
    		req.getRequestDispatcher("/SlotUpdate.jsp").forward(req, resp);
    		return;
    	}


    	// Retrieve slot and validate.
        String characterId = req.getParameter("characterId");
        String slotIndexStr = req.getParameter("slotIndex");
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "CharacterId cannot be empty.");
        } else if (slotIndexStr == null || slotIndexStr.trim().isEmpty()) {
        	messages.put("success", "Slot index cannot be empty.");
        } else {
			int charId = Integer.valueOf(characterId);
			int slotIndex = Integer.valueOf(slotIndexStr);
			try {
				Slot slot = slotDao.getSlot(charId, slotIndex);
				if(slot == null) {
					messages.put("success", "Slot does not exist. No update to perform.");
				} else {
					slot = slotDao.updateQuantity(slot, newQuantity);
					messages.put("success", "Successfully updated slot quantity.");
				}
        		
        		req.setAttribute("slot", slot);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/SlotUpdate.jsp").forward(req, resp);
    }
}
