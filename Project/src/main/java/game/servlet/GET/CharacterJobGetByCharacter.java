
import game.dal.CharacterJobDao;
import game.model.CharacterJob;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/characterjobgetbycharacter")
public class CharacterJobGetByCharacter extends HttpServlet {

    protected CharacterJobDao characterJobDao;

    @Override
    public void init() throws ServletException {
        characterJobDao = CharacterJobDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String characterIdStr = req.getParameter("characterId");
        List<CharacterJob> characterJobs = null;

        if (characterIdStr == null || characterIdStr.trim().isEmpty()) {
            messages.put("error", "Invalid Character ID.");
        } else {
            try {
                int characterId = Integer.parseInt(characterIdStr);
                characterJobs = characterJobDao.getCharacterJobsByCharacterId(characterId);

                if (characterJobs == null || characterJobs.isEmpty()) {
                    messages.put("error", "No jobs found for Character ID " + characterId + ".");
                } else {
                    messages.put("success", "Displaying jobs for Character ID: " + characterId);
                }
            } catch (NumberFormatException e) {
                messages.put("error", "Invalid Character ID format.");
            } catch (SQLException e) {
                e.printStackTrace();
                messages.put("error", "Unable to retrieve jobs. Please try again later.");
            }
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("characterJobs", characterJobs);

        req.getRequestDispatcher("/CharacterJobGetByCharacter.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}