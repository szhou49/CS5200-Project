package game.servlet;

import game.dal.ConsumableDao;
import game.model.Consumable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/consumabledelete")
public class ConsumableDelete extends HttpServlet {

    private ConsumableDao consumableDao;

    @Override
    public void init() throws ServletException {
        consumableDao = ConsumableDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        messages.put("title", "Delete Consumable");
        request.getRequestDispatcher("/ConsumableDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));

            Consumable consumable = consumableDao.getConsumableByItemId(itemId);
            if (consumable == null) {
                messages.put("error", "Consumable with item ID " + itemId + " does not exist.");
            } else {
                consumableDao.delete(consumable);
                messages.put("success", "Successfully deleted consumable with item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid item ID format. Please check your input.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete consumable.");
        }

        request.getRequestDispatcher("/ConsumableDelete.jsp").forward(request, response);
    }
}
