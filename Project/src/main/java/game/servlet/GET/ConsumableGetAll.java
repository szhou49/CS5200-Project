package game.servlet.GET;

import game.dal.ConsumableDao;
import game.model.Consumable;

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

@WebServlet("/consumablegetall")
public class ConsumableGetAll extends HttpServlet {

    protected ConsumableDao consumableDao;

    @Override
    public void init() throws ServletException {
        consumableDao = ConsumableDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Consumable> consumables = new ArrayList<>();

        // Retrieve all consumables.
        try {
            consumables = consumableDao.getAllConsumables();
            messages.put("success", "Displaying all available consumable items.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Unable to retrieve consumable items. Please try again later.");
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("consumables", consumables);

        req.getRequestDispatcher("/ConsumableGetAll.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
