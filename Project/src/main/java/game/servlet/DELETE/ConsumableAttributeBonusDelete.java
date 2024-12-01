package game.servlet;

import game.dal.ConsumableAttributeBonusDao;
import game.dal.ConsumableDao;
import game.model.ConsumableAttributeBonus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/consumableattributebonusdelete")
public class ConsumableAttributeBonusDelete extends HttpServlet {

    private ConsumableAttributeBonusDao consumableAttributeBonusDao;

    @Override
    public void init() throws ServletException {
        consumableAttributeBonusDao = ConsumableAttributeBonusDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        try {
            // Retrieve parameters
            int consumableId = Integer.parseInt(req.getParameter("consumableId"));
            String attribute = req.getParameter("attribute");

            // Validate ConsumableAttributeBonus
            ConsumableAttributeBonus bonus = consumableAttributeBonusDao.getConsumableAttributeBonus(consumableId, attribute);
            if (bonus == null) {
                messages.put("error", "No attribute bonus found for consumable ID: " + consumableId + " and attribute: " + attribute);
            } else {
                // Delete ConsumableAttributeBonus
                consumableAttributeBonusDao.delete(bonus);
                messages.put("success", "Successfully deleted attribute bonus for consumable ID: " + consumableId + " and attribute: " + attribute);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete attribute bonus.");
        }

        req.getRequestDispatcher("/ConsumableAttributeBonusDelete.jsp").forward(req, resp);
    }
}
