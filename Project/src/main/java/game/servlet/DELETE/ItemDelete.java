package game.servlet;

import game.dal.ItemDao;
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

@WebServlet("/itemdelete")
public class ItemDelete extends HttpServlet {

    private ItemDao itemDao;

    @Override
    public void init() throws ServletException {
        itemDao = ItemDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Item");
        req.getRequestDispatcher("/ItemDelete.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            Item item = itemDao.getItemById(itemId);

            if (item == null) {
                messages.put("error", "Item with ID " + itemId + " does not exist.");
            } else {
                itemDao.delete(item);
                messages.put("success", "Successfully deleted item with ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid item ID format. Please check your input.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete item.");
        }

        request.getRequestDispatcher("/ItemDelete.jsp").forward(request, response);
    }
}
