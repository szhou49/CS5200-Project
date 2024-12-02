package game.servlet;

import game.dal.EquippedSlotDao;
import game.model.EquippedSlot;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/equippedslotgetbycharacter")
public class EquippedSlotGetByCharacter extends HttpServlet {

    protected EquippedSlotDao equippedSlotDao;

    @Override
    public void init() throws ServletException {
        equippedSlotDao = EquippedSlotDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<EquippedSlot> equippedSlots = new ArrayList<>();

        // Retrieve and validate characterId.
        String characterIdStr = req.getParameter("characterId");
        int characterId = -1;
        try {
            characterId = Integer.parseInt(characterIdStr);
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid Character ID.");
        }

        if (characterId < 0) {
            messages.put("success", "Please provide a valid Character ID.");
        } else {
            try {
                equippedSlots = equippedSlotDao.getEquippedSlotsByCharacter(characterId);
                if (equippedSlots.isEmpty()) {
                    messages.put("success", "No equipped slots found for Character ID: " + characterId);
                } else {
                    messages.put("success", "Displaying equipped slots for Character ID: " + characterId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                messages.put("success", "Unable to retrieve equipped slots. Please try again later.");
            }
        }

        req.setAttribute("equippedSlots", equippedSlots);
        req.getRequestDispatcher("/EquippedSlotGet.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
