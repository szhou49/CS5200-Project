package game.servlet;

import game.dal.GearDao;
import game.dal.ItemDao;
import game.model.Gear;
import game.model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/gearcreate")
public class GearCreate extends HttpServlet {

    private ItemDao itemDao;
    private GearDao gearDao;

    @Override
    public void init() throws ServletException {
        itemDao = ItemDao.getInstance();
        gearDao = GearDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        messages.put("title", "Create Gear");
        request.getRequestDispatcher("/GearCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String equippedSlot = request.getParameter("equippedSlot");
            int requiredLevel = Integer.parseInt(request.getParameter("requiredLevel"));
            int defenseRating = Integer.parseInt(request.getParameter("defenseRating"));
            int magicDefenseRating = Integer.parseInt(request.getParameter("magicDefenseRating"));

            Item existingItem = itemDao.getItemById(itemId);
            if (existingItem == null) {
                messages.put("error", "Item with ID " + itemId + " does not exist.");
            } else {
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

        request.getRequestDispatcher("/GearCreate.jsp").forward(request, response);
    }
}
