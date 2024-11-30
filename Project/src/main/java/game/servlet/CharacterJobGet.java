package game.servlet;

import game.dal.CharacterJobDao;
import game.model.CharacterJob;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CharacterJobGet is a servlet to handle HTTP requests for retrieving character jobs.
 */
@WebServlet("/characterjobget")
public class CharacterJobGet extends HttpServlet {

    private CharacterJobDao characterJobDao;

    @Override
    public void init() throws ServletException {
        characterJobDao = CharacterJobDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<CharacterJob> characterJobs = new ArrayList<>();

        // Retrieve and validate characterId parameter
        String characterIdStr = req.getParameter("characterId");
        if (characterIdStr == null || characterIdStr.trim().isEmpty()) {
            messages.put("success", "Please provide a valid Character ID.");
        } else {
            try {
                int characterId = Integer.parseInt(characterIdStr);
                characterJobs = characterJobDao.getJobsByCharacter(characterId);
                if (characterJobs.isEmpty()) {
                    messages.put("success", "No jobs found for Character ID: " + characterId);
                } else {
                    messages.put("success", "Displaying jobs for Character ID: " + characterId);
                }
            } catch (NumberFormatException e) {
                messages.put("success", "Invalid Character ID format.");
            } catch (SQLException e) {
                throw new IOException(e);
            }
        }

        req.setAttribute("characterJobs", characterJobs);
        req.getRequestDispatcher("/CharacterJobGet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}