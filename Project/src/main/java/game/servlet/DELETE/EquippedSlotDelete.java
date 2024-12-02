package game.servlet;

import game.dal.*;
import game.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/equippedslotdelete")
public class EquippedSlotDelete extends HttpServlet {

    private EquippedSlotDao equippedSlotDao;

    @Override
    public void init() throws ServletException {
        equippedSlotDao = EquippedSlotDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Unequip Slot for Character");
        request.getRequestDispatcher("/EquippedSlotDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            String slot = request.getParameter("slot");

            EquippedSlot existingEquippedSlot = equippedSlotDao.getEquippedSlot(characterId, slot);
            if (existingEquippedSlot == null) {
                messages.put("success", "EquippedSlot for character ID " + characterId + " and slot '" + slot + "' does not exist.");
            } else {
                equippedSlotDao.delete(existingEquippedSlot);
                messages.put("success", "Successfully unequipped slot: " + slot + " for character ID: " + characterId);
            }
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to unequip slot.");
        }

        request.getRequestDispatcher("/EquippedSlotDelete.jsp").forward(request, response);
    }
}
