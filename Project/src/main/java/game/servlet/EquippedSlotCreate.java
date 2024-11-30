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

@WebServlet("/equippedslotcreate")
public class EquippedSlotCreate extends HttpServlet {

    private CharacterDao characterDao;
    private GearDao gearDao;
    private EquippedSlotDao equippedSlotDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
        gearDao = GearDao.getInstance();
        equippedSlotDao = EquippedSlotDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Equip Slot for Character");
        request.getRequestDispatcher("/EquippedSlotCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String slot = request.getParameter("slot");

            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("success", "Character with ID " + characterId + " does not exist.");
            } else {
                Gear gear = gearDao.getGearByItemId(itemId);
                if (gear == null) {
                    messages.put("success", "Gear with ID " + itemId + " does not exist.");
                } else {
                    EquippedSlot equippedSlot = new EquippedSlot(characterId, itemId, slot);
                    equippedSlotDao.create(equippedSlot);

                    messages.put("success", "Successfully equipped gear in slot: " + slot + " for character ID: " + characterId);
                }
            }
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to equip gear.");
        }

        request.getRequestDispatcher("/EquippedSlotCreate.jsp").forward(request, response);
    }
}
