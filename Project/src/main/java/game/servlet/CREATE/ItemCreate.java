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

@WebServlet("/itemcreate")
public class ItemCreate extends HttpServlet {

    private ItemDao itemDao;

    @Override
    public void init() throws ServletException {
        itemDao = ItemDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        request.getRequestDispatcher("/ItemCreate.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String itemName = request.getParameter("itemName");
            int stackSize = Integer.parseInt(request.getParameter("stackSize"));
            int vendorPrice = Integer.parseInt(request.getParameter("vendorPrice"));
            int itemLevel = Integer.parseInt(request.getParameter("itemLevel"));

            Item item = new Item(null, itemName, stackSize, vendorPrice, itemLevel);
            itemDao.create(item);
            messages.put("success", "Successfully created item: " + itemName);
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create item.");
        }

        request.getRequestDispatcher("/ItemCreate.jsp").forward(request, response);
    }
}
