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

@WebServlet("/slotdelete")
public class SlotDelete extends HttpServlet {

    private SlotDao slotDao;

    @Override
    public void init() throws ServletException {
        slotDao = SlotDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        // Provide a title for the JSP page
        messages.put("title", "Delete Slot");
        request.getRequestDispatcher("/SlotDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            int slotIndex = Integer.parseInt(request.getParameter("slotIndex"));

            // Validate if the slot exists
            Slot existingSlot = slotDao.getSlot(characterId, slotIndex);
            if (existingSlot == null) {
                messages.put("success", "Slot for character ID " + characterId + " and slot index " + slotIndex + " does not exist.");
            } else {
                // Delete the slot
                slotDao.delete(existingSlot);
                messages.put("success", "Successfully deleted slot for character ID: " + characterId + " and slot index: " + slotIndex);
            }
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to delete slot.");
        }

        request.getRequestDispatcher("/SlotDelete.jsp").forward(request, response);
    }
}
