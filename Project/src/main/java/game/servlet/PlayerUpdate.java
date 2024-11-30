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


@WebServlet("/playerupdate")
public class PlayerUpdate extends HttpServlet {
	
	protected PlayerDao playerDao;
	
	@Override
	public void init() throws ServletException {
		playerDao = PlayerDao.getInstance();
	}
	
	
	// For getting information of a player by email
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve player and validate.
        String playerEmail = req.getParameter("email");
        if (playerEmail == null || playerEmail.trim().isEmpty()) {
            messages.put("success", "Please enter a valid player email.");
        } else {
        	try {
        		Player player = playerDao.getPlayerByEmail(playerEmail);
        		if(player == null) {
        			messages.put("success", "Player does not exist.");
        		}
        		req.setAttribute("player", player);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PlayerUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
      
     // Retrieve player and validate.
        String playerEmail = req.getParameter("email");
        if (playerEmail == null || playerEmail.trim().isEmpty()) {
            messages.put("success", "Please enter a valid player email.");
        } else {
        	try {
        		Player player = playerDao.getPlayerByEmail(playerEmail);
        		if(player == null) {
        			messages.put("success", "Player does not exist. No update to preform.");
        		} else {
        			String newName = req.getParameter("newName");
        			if (newName == null || newName.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid name.");
        	        } else {
        	        	player = playerDao.updatePlayerName(player, newName);
        	        	messages.put("success", "Successfully updated " + newName);
        	        }
        		}
        		req.setAttribute("player", player);
        		
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/PlayerUpdate.jsp").forward(req, resp);
    }
}
