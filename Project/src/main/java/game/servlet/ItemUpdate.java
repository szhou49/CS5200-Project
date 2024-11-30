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


@WebServlet("/itemupdate")
public class ItemUpdate extends HttpServlet {
	
	protected ItemDao itemDao;
	
	@Override
	public void init() throws ServletException {
		itemDao = ItemDao.getInstance();
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
				Item item = itemDao.getItemById(itemId);
				if(item == null) {
					messages.put("success", "Item does not exist.");
				}
				req.setAttribute("item", item);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        
        req.getRequestDispatcher("/ItemUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        String newVendorPriceStr = req.getParameter("newVendorPrice");
        if (newVendorPriceStr == null || newVendorPriceStr.trim().isEmpty()) {
            messages.put("success", "Please enter a valid price value.");
            req.getRequestDispatcher("/ItemUpdate.jsp").forward(req, resp);
            return;
        }
		
		int newVendorPrice;
    	try {
    		newVendorPrice = Integer.valueOf(newVendorPriceStr);
        	if (newVendorPrice < 0) {
        		messages.put("success", "Price must be non-negative");
        		req.getRequestDispatcher("/ItemUpdate.jsp").forward(req, resp);
        		return;
        	}
    	} catch (NumberFormatException e){
    		messages.put("success", "Price must be a number.");
    		req.getRequestDispatcher("/ItemUpdate.jsp").forward(req, resp);
    		return;
    	}


    	String itemIdStr = req.getParameter("itemId");
        
        if (itemIdStr == null || itemIdStr.trim().isEmpty()) {
            messages.put("success", "Item id cannot be empty.");
        } else {
			try {
				int itemId = Integer.valueOf(itemIdStr);
				Item item = itemDao.getItemById(itemId);
				if(item == null) {
					messages.put("success", "Item does not exist. No update to perform.");
				} else {
					item = itemDao.updateVendorPrice(item, newVendorPrice);
					messages.put("success", "Successfully updated vendor price to " + newVendorPrice);
				}
        		
        		req.setAttribute("item", item);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/ItemUpdate.jsp").forward(req, resp);
    }
}
