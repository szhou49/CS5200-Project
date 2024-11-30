package game.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import game.dal.*;
import game.model.*;

@WebServlet("/item")
public class ItemServlet extends BasicServlet {
	
	protected ItemDao itemDao;
    protected WeaponDao weaponDao;
    protected GearDao gearDao;
    
    @Override
    public void init() throws ServletException {
        itemDao = ItemDao.getInstance();
        weaponDao = WeaponDao.getInstance();
        gearDao = GearDao.getInstance();
    }
    
    protected void createItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String itemName = request.getParameter("itemName");
            int stackSize = Integer.parseInt(request.getParameter("stackSize"));
            int vendorPrice = Integer.parseInt(request.getParameter("vendorPrice"));
            int itemLevel = Integer.parseInt(request.getParameter("itemLevel"));
            // create item
            Item item = new Item(null, itemName, stackSize, vendorPrice, itemLevel);
            itemDao.create(item);
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create item.");
        }
        request.getRequestDispatcher("/ItemCreate.jsp").forward(request, response);
    }
    
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String itemIdStr = request.getParameter("itemId");
            int itemId = Integer.parseInt(itemIdStr);

            Item item = itemDao.getItemById(itemId);
            if (item == null) {
                messages.put("error", "Item with ID " + itemId + " does not exist.");
            } else {
                itemDao.delete(item);
                messages.put("success", "Successfully deleted item with ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid item ID. Please provide a valid number.");
        } catch (Exception e) {
            e.printStackTrace();
            messages.put("error", "An error occurred while attempting to delete the item.");
        }

        request.getRequestDispatcher("/ItemDelete.jsp").forward(request, response);
    }

    protected void createWeapon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            int requiredLevel = Integer.parseInt(request.getParameter("requiredLevel"));
            int damage = Integer.parseInt(request.getParameter("damage"));
            double autoAttack = Double.parseDouble(request.getParameter("autoAttack"));
            double attackDelay = Double.parseDouble(request.getParameter("attackDelay"));

            // validate item
            Item existingItem = itemDao.getItemById(itemId);
            if (existingItem == null) {
                messages.put("error", "Item with ID " + itemId + " does not exist.");
            } else {
                // create weapon using existed item
                Weapon weapon = new Weapon(itemId, requiredLevel, damage, autoAttack, attackDelay, existingItem);
                weaponDao.create(weapon);
                messages.put("success", "Successfully created weapon for item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create weapon.");
        }

        request.getRequestDispatcher("/ItemCreate.jsp").forward(request, response);
    }
	
    protected void deleteWeapon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            Weapon existingWeapon = weaponDao.getWeaponByItemId(itemId);
            
            if (existingWeapon == null) {
                messages.put("error", "Weapon with ID " + itemId + " does not exist.");
            } else {
                weaponDao.delete(existingWeapon);
                messages.put("success", "Successfully deleted weapon with item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid item ID format. Please check your input.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete weapon.");
        }

        request.getRequestDispatcher("/ItemDelete.jsp").forward(request, response);
    }

    protected void createGear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String equippedSlot = request.getParameter("equippedSlot");
            int requiredLevel = Integer.parseInt(request.getParameter("requiredLevel"));
            int defenseRating = Integer.parseInt(request.getParameter("defenseRating"));
            int magicDefenseRating = Integer.parseInt(request.getParameter("magicDefenseRating"));

            // validate item
            Item existingItem = itemDao.getItemById(itemId);
            if (existingItem == null) {
                messages.put("error", "Item with ID " + itemId + " does not exist.");
            } else {
            	// create gear using existed item
                Gear gear = new Gear(itemId, equippedSlot, requiredLevel, defenseRating, magicDefenseRating, existingItem);
                gearDao.create(gear);
                messages.put("success", "Successfully created gear for item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create gear.");
        }

        request.getRequestDispatcher("/ItemCreate.jsp").forward(request, response);
    }

    protected void deleteGear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));

            Gear existingGear = gearDao.getGearByItemId(itemId);
            if (existingGear == null) {
                messages.put("error", "Gear with ID " + itemId + " does not exist.");
            } else {
                gearDao.delete(existingGear);
                messages.put("success", "Successfully deleted gear with item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid item ID format. Please check your input.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete gear.");
        }

        request.getRequestDispatcher("/ItemDelete.jsp").forward(request, response);
    }
	
	protected void createConsumable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO: need to add Consumable model and DAO
	}
	
}