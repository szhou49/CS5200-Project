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


@WebServlet("/gearupdate")
public class GearUpdate extends HttpServlet {
	
	protected GearDao gearDao;
	
	@Override
	public void init() throws ServletException {
		gearDao = GearDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String itemIdStr = req.getParameter("itemId");
        
        if (itemIdStr == null || itemIdStr.trim().isEmpty()) {
            messages.put("success", "Item id cannot be empty.");
        } else {
			try {
				int itemId = Integer.valueOf(itemIdStr);
				Gear gear = gearDao.getGearByItemId(itemId);
				if(gear == null) {
					messages.put("success", "Gear does not exist.");
				}
				req.setAttribute("gear", gear);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        
        req.getRequestDispatcher("/GearUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Validate input newDamage value
        String newDefenseRatingStr = req.getParameter("newDefenseRating");
        if (newDefenseRatingStr == null || newDefenseRatingStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid defense rating value.");
            req.getRequestDispatcher("/GearUpdate.jsp").forward(req, resp);
            return;
        }
		
		int newDefenseRating;
    	try {
    		newDefenseRating = Integer.valueOf(newDefenseRatingStr);
        	if (newDefenseRating < 0) {
        		messages.put("success", "Defense rating must be non-negative");
        		req.getRequestDispatcher("/GearUpdate.jsp").forward(req, resp);
        		return;
        	}
    	} catch (NumberFormatException e){
    		messages.put("success", "Defense rating must be a number.");
    		req.getRequestDispatcher("/GearUpdate.jsp").forward(req, resp);
    		return;
    	}

    	 String itemIdStr = req.getParameter("itemId");
         
         if (itemIdStr == null || itemIdStr.trim().isEmpty()) {
             messages.put("success", "Item id cannot be empty.");
         } else {
 			try {
 				int itemId = Integer.valueOf(itemIdStr);
 				Gear gear = gearDao.getGearByItemId(itemId);
 				if(gear == null) {
 					messages.put("success", "Gear does not exist.");
 				} else {
 					gear = gearDao.updateDefenseRating(gear, newDefenseRating);
 					messages.put("success", "Successfully updated defense rating to " + newDefenseRating);
 				}
        		
        		req.setAttribute("gear", gear);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/GearUpdate.jsp").forward(req, resp);
    }
}
