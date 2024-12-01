package game.servlet;

import game.dal.ConsumableDao;
import game.dal.ItemDao;
import game.model.Consumable;
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

@WebServlet("/consumablecreate")
public class ConsumableCreate extends HttpServlet {

    private ItemDao itemDao;
    private ConsumableDao consumableDao;

    @Override
    public void init() throws ServletException {
        itemDao = ItemDao.getInstance();
        consumableDao = ConsumableDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        messages.put("title", "Create Consumable");
        request.getRequestDispatcher("/ConsumableCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String itemDescription = request.getParameter("itemDescription");

            Item existingItem = itemDao.getItemById(itemId);
            if (existingItem == null) {
                messages.put("error", "Item with ID " + itemId + " does not exist.");
            } else {
                Consumable consumable = new Consumable(existingItem, itemDescription);
                consumableDao.create(consumable);
                messages.put("success", "Successfully created consumable for item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create consumable.");
        }

        request.getRequestDispatcher("/ConsumableCreate.jsp").forward(request, response);
    }
}
