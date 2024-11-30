package game.servlet.GET;

import game.dal.ItemDao;
import game.model.Item;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/itemgetall")
public class ItemGetAll extends HttpServlet {

    protected ItemDao itemDao;

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

        List<Item> items = new ArrayList<>();

        // Retrieve all items.
        try {
            items = itemDao.getItems();
            messages.put("success", "Displaying all available items.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Unable to retrieve items. Please try again later.");
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("items", items);

        req.getRequestDispatcher("/ItemGetAll.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
