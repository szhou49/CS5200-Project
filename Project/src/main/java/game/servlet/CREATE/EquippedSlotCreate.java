package game.servlet;

import game.dal.*;
import game.model.*;
import game.model.Character;
import game.model.EquippedSlot.SlotEnum;

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
    private SlotDao slotDao;
    private EquippedSlotDao equippedSlotDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
        gearDao = GearDao.getInstance();
        slotDao = SlotDao.getInstance();
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
            String slotName = request.getParameter("slot"); // read body slot

            // validate and parse the slot
            SlotEnum slotEnum;
            try {
                slotEnum = SlotEnum.valueOf(slotName.toUpperCase());
            } catch (IllegalArgumentException e) {
                messages.put("success", "Invalid slot value: " + slotName);
                request.getRequestDispatcher("/EquipSlotCreate.jsp").forward(request, response);
                return;
            }
            
            
            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("success", "Character with ID " + characterId + " does not exist.");
            }
            
            Gear gear = gearDao.getGearByItemId(itemId);
            if (gear == null) {
                messages.put("success", "gear with ID " + itemId + " does not exist.");
            }
            
            EquippedSlot equippedSlot = new EquippedSlot(slotEnum, character, gear);
            equippedSlotDao.create(equippedSlot);

            
        } catch (NumberFormatException e) {
            messages.put("success", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("success", "Database error: Unable to equip gear.");
        }

        request.getRequestDispatcher("/EquippedSlotCreate.jsp").forward(request, response);
    }
}
