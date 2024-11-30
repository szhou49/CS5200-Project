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


@WebServlet("/weaponupdate")
public class WeaponUpdate extends HttpServlet {
	
	protected WeaponDao weaponDao;
	
	@Override
	public void init() throws ServletException {
		weaponDao = WeaponDao.getInstance();
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
				Weapon weapon = weaponDao.getWeaponByItemId(itemId);
				if(weapon == null) {
					messages.put("success", "Weapon does not exist.");
				}
				req.setAttribute("weapon", weapon);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        
        req.getRequestDispatcher("/WeaponUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Validate input newDamage value
        String newDamageStr = req.getParameter("newDamage");
        if (newDamageStr == null || newDamageStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid damage value.");
            req.getRequestDispatcher("/WeaponUpdate.jsp").forward(req, resp);
            return;
        }
		
		int newDamage;
    	try {
    		newDamage = Integer.valueOf(newDamageStr);
        	if (newDamage < 0) {
        		messages.put("success", "Damage must be non-negative");
        		req.getRequestDispatcher("/WeaponUpdate.jsp").forward(req, resp);
        		return;
        	}
    	} catch (NumberFormatException e){
    		messages.put("success", "Damage must be a number.");
    		req.getRequestDispatcher("/WeaponUpdate.jsp").forward(req, resp);
    		return;
    	}

        String itemIdStr = req.getParameter("itemId");
        
        if (itemIdStr == null || itemIdStr.trim().isEmpty()) {
            messages.put("success", "Item id cannot be empty.");
        } else {
			try {
				int itemId = Integer.valueOf(itemIdStr);
				Weapon weapon = weaponDao.getWeaponByItemId(itemId);
				if(weapon == null) {
					messages.put("success", "Weapon does not exist. No update to perform.");
				} else {
					weapon = weaponDao.updateDamage(weapon, newDamage);
					messages.put("success", "Successfully updated weapon damage to " + newDamage);
				}
        		
        		req.setAttribute("weapon", weapon);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/WeaponUpdate.jsp").forward(req, resp);
    }
}
