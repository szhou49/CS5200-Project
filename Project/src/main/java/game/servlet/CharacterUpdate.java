package game.servlet;

import game.dal.*;
import game.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/characterupdate")
public class CharacterUpdate extends HttpServlet {
	
	protected CharacterDao characterDao;
	
	@Override
	public void init() throws ServletException {
		characterDao = CharacterDao.getInstance();
	}
	
	
	// For getting information of a character by characterId
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve character and validate.
        String characterId = req.getParameter("characterId");
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid characterId.");
        } else {
        	int charId = Integer.valueOf(characterId);
        	try {
        		Character character = characterDao.getCharacterById(charId);
        		if(character == null) {
        			messages.put("success", "Character does not exist.");
        		}
        		req.setAttribute("character", character);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
      
        // Retrieve character and validate.
        String characterId = req.getParameter("characterId");
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid character id.");
        } else {
        	int charId = Integer.valueOf(characterId);
        	try {
        		Character character = characterDao.getCharacterById(charId);
        		if(character == null) {
        			messages.put("success", "Character does not exist. No update to perform.");
        		} else {
        			// mainHandId passed by client
        			String mainHandId = req.getParameter("mainHandId");
        			if (mainHandId == null || mainHandId.trim().isEmpty()) {
        				messages.put("success", "Please enter a valid main hand weapon id.");
        			} else {
        				int mhId = Integer.valueOf(mainHandId);
                		character = characterDao.updateMainHand(character, mhId);
        			}
        			
        		}
        		req.setAttribute("character", character);
        		
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterUpdate.jsp").forward(req, resp);
    }
}
