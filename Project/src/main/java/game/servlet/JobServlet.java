package game.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import game.dal.*;
import game.model.*;
import game.model.Character;

@WebServlet("/job")
public class JobServlet extends BasicServlet {
	
	protected JobDao jobDao;
	protected CharacterJobDao characterJobDao;
	protected CharacterDao characterDao;
	
	@Override
    public void init() throws ServletException {
		jobDao = JobDao.getInstance();
        characterJobDao = CharacterJobDao.getInstance();
        characterDao = CharacterDao.getInstance();
    }
	
	protected void createJob(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Map<String, String> messages = new HashMap<>();
	    request.setAttribute("messages", messages);

	    try {
	        String jobName = request.getParameter("jobName");

	        if (jobName == null || jobName.trim().isEmpty()) {
	            messages.put("error", "Job name cannot be empty.");
	        } else {
	        	// validate job
	            Job existingJob = jobDao.getJobByName(jobName);
	            if (existingJob != null) {
	                messages.put("error", "Job with name '" + jobName + "' already exists.");
	            } else {
	                // create job
	                Job job = new Job(jobName);
	                jobDao.create(job);
	                messages.put("success", "Successfully created job: " + jobName);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        messages.put("error", "Database error: Unable to create job.");
	    }

	    request.getRequestDispatcher("/JobCreate.jsp").forward(request, response);
	}
	
	protected void createCharacterJob(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            String jobName = request.getParameter("jobName");
            int jobLevel = Integer.parseInt(request.getParameter("jobLevel"));
            int currentExp = Integer.parseInt(request.getParameter("currentExp"));
            int threshold = Integer.parseInt(request.getParameter("threshold"));

            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("error", "Character with ID " + characterId + " does not exist.");
            } else {
                Job job = jobDao.getJobByName(jobName);
                if (job == null) {
                    messages.put("error", "Job " + jobName + " does not exist.");
                } else {
                    // create character job
                    CharacterJob characterJob = new CharacterJob(characterId, jobName, jobLevel, currentExp, threshold);
                    characterJobDao.create(characterJob);

                    messages.put("success", "Successfully created CharacterJob for character ID: " + characterId + " and job: " + jobName);
                }
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create CharacterJob.");
        }

        request.getRequestDispatcher("/CharacterJobCreate.jsp").forward(request, response);
    }
	
	protected void deleteJob(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Map<String, String> messages = new HashMap<>();
	    request.setAttribute("messages", messages);

	    try {
	        String jobName = request.getParameter("jobName");
	        if (jobName == null || jobName.trim().isEmpty()) {
	            messages.put("error", "Job name cannot be empty.");
	        } else {
	            Job existingJob = jobDao.getJobByName(jobName);
	            if (existingJob == null) {
	                messages.put("error", "Job with name '" + jobName + "' does not exist.");
	            } else {
	                jobDao.delete(existingJob);
	                messages.put("success", "Successfully deleted job: " + jobName);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        messages.put("error", "Database error: Unable to delete job.");
	    }

	    request.getRequestDispatcher("/JobDelete.jsp").forward(request, response);
	}

	protected void deleteCharacterJob(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Map<String, String> messages = new HashMap<>();
	    request.setAttribute("messages", messages);

	    try {
	        int characterId = Integer.parseInt(request.getParameter("characterId"));
	        String jobName = request.getParameter("jobName");

	        if (jobName == null || jobName.trim().isEmpty()) {
	            messages.put("error", "Job name cannot be empty.");
	        } else {
	            CharacterJob existingCharacterJob = characterJobDao.getCharacterJob(characterId, jobName);
	            if (existingCharacterJob == null) {
	                messages.put("error", "CharacterJob for character ID " + characterId + " and job '" + jobName + "' does not exist.");
	            } else {
	                // delete character job
	                characterJobDao.delete(existingCharacterJob);
	                messages.put("success", "Successfully deleted CharacterJob for character ID: " + characterId + " and job: " + jobName);
	            }
	        }
	    } catch (NumberFormatException e) {
	        messages.put("error", "Invalid character ID format. Please check your input.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        messages.put("error", "Database error: Unable to delete CharacterJob.");
	    }

	    request.getRequestDispatcher("/CharacterJobDelete.jsp").forward(request, response);
	}

}