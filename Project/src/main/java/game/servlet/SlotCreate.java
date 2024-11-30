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

@WebServlet("/slotcreate")
public class SlotCreate extends HttpServlet {

    private SlotDao slotDao;
    private CharacterDao characterDao;
    private ItemDao itemDao;

    @Override
    public void init() throws ServletException {
        slotDao = SlotDao.getInstance();
        characterDao = CharacterDao.getInstance();
        itemDao = ItemDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        // Provide a title for the JSP page
        messages.put("title", "Create Slot");
        request.getRequestDispatcher("/SlotCreate.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            int slotIndex = Integer.parseInt(request.getParameter("slotIndex"));
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Validate character existence
            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("success", "Character with ID " + characterId + " does not exist.");
            } else {
                // Validate item existence
                Item item = itemDao.getItemById(itemId);
                if (item == null) {
                    messages.put("success", "Item with ID " + itemId + " does not exist.");
                } else {
                    // Create the slot
                    Slot slot = new Slot(characterId, slotIndex, itemId, quantity);
                    slotDao.create(slot);

                    messages.put("success", "Successfully created slot for character ID: " + characterId + " and item ID: " + itemId);
                }
            }
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to create slot.");
        }

        request.getRequestDispatcher("/SlotCreate.jsp").forward(request, response);
    }
}
