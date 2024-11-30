package game.servlet;

import game.dal.*;
import game.model.*;
import game.model.Character;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/slot")
public class SlotServlet extends BasicServlet {

    private SlotDao slotDao;
    private CharacterDao characterDao;
    private ItemDao itemDao;
    protected GearDao gearDao;
    protected EquippedSlotDao equippedSlotDao;

    @Override
    public void init() throws ServletException {
        slotDao = SlotDao.getInstance();
        characterDao = CharacterDao.getInstance();
        itemDao = ItemDao.getInstance();
        gearDao = GearDao.getInstance();
        equippedSlotDao = EquippedSlotDao.getInstance();
    }

    protected void createSlot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            int slotIndex = Integer.parseInt(request.getParameter("slotIndex"));
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("success", "Character with ID " + characterId + " does not exist.");
            } else {
                Item item = itemDao.getItemById(itemId);
                if (item == null) {
                    messages.put("success", "Item with ID " + itemId + " does not exist.");
                } else {
                    // create slot
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

    protected void deleteSlot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            int slotIndex = Integer.parseInt(request.getParameter("slotIndex"));
            
            // validate slot
            Slot existingSlot = slotDao.getSlot(characterId, slotIndex);
            if (existingSlot == null) {
                messages.put("success", "Slot for character ID " + characterId + " and slot index " + slotIndex + " does not exist.");
            } else {
                // delete slot
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

    protected void createEquippedSlot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    // create equipped slot
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

    protected void deleteEquippedSlot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            String slot = request.getParameter("slot");

            EquippedSlot existingEquippedSlot = equippedSlotDao.getEquippedSlot(characterId, slot);
            if (existingEquippedSlot == null) {
                messages.put("success", "EquippedSlot for character ID " + characterId + " and slot '" + slot + "' does not exist.");
            } else {
                // delete equipped slot
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
