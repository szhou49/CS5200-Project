package game.servlet;

import game.dal.*;
import game.model.*;
import game.model.Character;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/characterjobcreate")
public class CharacterJobCreate extends HttpServlet {

    private CharacterDao characterDao;
    private JobDao jobDao;
    private CharacterJobDao characterJobDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
        jobDao = JobDao.getInstance();
        characterJobDao = CharacterJobDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Create Character Job");
        request.getRequestDispatcher("/CharacterJobCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            String jobName = request.getParameter("jobName");

            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("error", "Character with ID " + characterId + " does not exist.");
            } else {
                Job job = jobDao.getJobByName(jobName);
                if (job == null) {
                    messages.put("error", "Job " + jobName + " does not exist.");
                } else {
                    CharacterJob characterJob = new CharacterJob(character, job);
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
}
