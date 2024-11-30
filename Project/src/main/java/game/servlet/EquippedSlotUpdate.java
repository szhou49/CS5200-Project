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


@WebServlet("/equippedslotupdate")
public class EquippedSlotUpdate extends HttpServlet {
	
	protected EquippedSlotDao equippedSlotDao;
	
	@Override
	public void init() throws ServletException {
		equippedSlotDao = EquippedSlotDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String characterId = req.getParameter("characterId");
        String slot = req.getParameter("slot");
        
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Character id cannot be empty.");
        } else if (slot == null || slot.trim().isEmpty()) {
        	messages.put("success", "Slot cannot be empty.");
        } else {
			try {
				int charId = Integer.valueOf(characterId);
				
				EquippedSlot equippedSlot = equippedSlotDao.getEquippedSlot(charId, slot);
				if(equippedSlot == null) {
					messages.put("success", "Equipped slot does not exist.");
				}
				req.setAttribute("equippedSlot", equippedSlot);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        
        req.getRequestDispatcher("/EquippedSlotUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Validate input newDamage value
        String newItemIdStr = req.getParameter("newItemId");
        if (newItemIdStr == null || newItemIdStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid item id value.");
            req.getRequestDispatcher("/EquippedSlotUpdate.jsp").forward(req, resp);
            return;
        }
		
		int newItemId = Integer.valueOf(newItemIdStr);
 

		String characterId = req.getParameter("characterId");
        String slot = req.getParameter("slot");
        
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Character id cannot be empty.");
        } else if (slot == null || slot.trim().isEmpty()) {
        	messages.put("success", "Slot cannot be empty.");
        } else {
			try {
				int charId = Integer.valueOf(characterId);
				
				EquippedSlot equippedSlot = equippedSlotDao.getEquippedSlot(charId, slot);
				if(equippedSlot == null) {
					messages.put("success", "Equipped slot does not exist.");
				} else {
					equippedSlot = equippedSlotDao.updateEquippedItem(equippedSlot, newItemId);
				}
        		
        		req.setAttribute("equippedSlot", equippedSlot);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/EquippedSlotUpdate.jsp").forward(req, resp);
    }
}
