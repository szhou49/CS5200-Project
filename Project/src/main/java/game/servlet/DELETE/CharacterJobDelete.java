package game.servlet;

import game.dal.CharacterJobDao;
import game.model.CharacterJob;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/characterjobdelete")
public class CharacterJobDelete extends HttpServlet {

    private CharacterJobDao characterJobDao;

    @Override
    public void init() throws ServletException {
        characterJobDao = CharacterJobDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Delete Character Job");
        request.getRequestDispatcher("/CharacterJobDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
