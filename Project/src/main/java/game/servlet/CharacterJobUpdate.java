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


@WebServlet("/characterjobupdate")
public class CharacterJobUpdate extends HttpServlet {
	
	protected CharacterJobDao characterJobDao;
	
	@Override
	public void init() throws ServletException {
		characterJobDao = CharacterJobDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String characterId = req.getParameter("characterId");
        String jobName = req.getParameter("jobName");
        
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Character id cannot be empty.");
        } else if (jobName == null || jobName.trim().isEmpty()){
        	messages.put("success", "Job name cannot be empty.");
        } else {
			try {
				int charId = Integer.valueOf(characterId);
				CharacterJob characterJob = characterJobDao.getCharacterJob(charId, jobName);
				if(characterJob == null) {
					messages.put("success", "Character job does not exist.");
				}
				req.setAttribute("characterJob", characterJob);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
        }
        
        req.getRequestDispatcher("/CharacterJobUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Validate input new value
        String newLevelStr = req.getParameter("newLevel");
        Integer newLevel = validateNumericParameter(newLevelStr, "newLevel", messages, req, resp);
        if (newLevel == null) return;
    	
    	// Validate input new value
        String newExpStr = req.getParameter("newExp");
        Integer newExp = validateNumericParameter(newExpStr, "newExp", messages, req, resp);
        if (newExp == null) return;
        
    	// Validate input new value
        String newThresholdStr = req.getParameter("newThreshold");
        Integer newThreshold = validateNumericParameter(newThresholdStr, "newThreshold", messages, req, resp);
        if (newThreshold == null) return;

    	String characterId = req.getParameter("characterId");
        String jobName = req.getParameter("jobName");
        
        if (characterId == null || characterId.trim().isEmpty()) {
            messages.put("success", "Character id cannot be empty.");
        } else if (jobName == null || jobName.trim().isEmpty()){
        	messages.put("success", "Job name cannot be empty.");
        } else {
			try {
				int charId = Integer.valueOf(characterId);
				CharacterJob characterJob = characterJobDao.getCharacterJob(charId, jobName);
				if(characterJob == null) {
					messages.put("success", "Character job does not exist. No update to perform.");
				} else {
					characterJob = characterJobDao.updateLevelAndExp(characterJob, newLevel, newExp, newThreshold);
					messages.put("success", "Successfully updated character job.");
				}
        		
        		req.setAttribute("characterJob", characterJob);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CharacterJobUpdate.jsp").forward(req, resp);
    }
	
	
	private Integer validateNumericParameter(String paramValue, String paramName,
            Map<String, String> messages, HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        if (paramValue == null || paramValue.trim().isEmpty()) {
            messages.put("success", "Please enter a valid " + paramName + " value.");
            req.getRequestDispatcher("/CharacterJobUpdate.jsp").forward(req, resp);
            return null;
        }
        
        try {
            int value = Integer.valueOf(paramValue);
            if (value < 0) {
                messages.put("success", paramName + " must be non-negative");
                req.getRequestDispatcher("/CharacterJobUpdate.jsp").forward(req, resp);
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            messages.put("success", paramName + " must be a number.");
            req.getRequestDispatcher("/CharacterJobUpdate.jsp").forward(req, resp);
            return null;
        }
    }
}
