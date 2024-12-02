package game.servlet.GET;

import game.dal.SlotDao;
import game.model.Slot;

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

@WebServlet("/slotgetbycharacterbycharacter")
public class SlotGetByCharacter extends HttpServlet {

    protected SlotDao slotDao;

    @Override
    public void init() throws ServletException {
        slotDao = SlotDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String characterIdStr = req.getParameter("characterId");
        List<Slot> slots = null;

        if (characterIdStr == null || characterIdStr.trim().isEmpty()) {
            messages.put("error", "Invalid Character ID.");
        } else {
            try {
                int characterId = Integer.parseInt(characterIdStr);
                slots = slotDao.getSlotsByCharacterId(characterId);

                if (slots == null || slots.isEmpty()) {
                    messages.put("error", "No slots found for Character ID " + characterId + ".");
                } else {
                    messages.put("success", "Displaying slots for Character ID: " + characterId);
                }
            } catch (NumberFormatException e) {
                messages.put("error", "Invalid Character ID format.");
            } catch (SQLException e) {
                e.printStackTrace();
                messages.put("error", "Unable to retrieve slots. Please try again later.");
            }
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("slots", slots);

        req.getRequestDispatcher("/SlotGetByCharacter.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
