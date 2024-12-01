package game.servlet;

import game.dal.ConsumableAttributeBonusDao;
import game.dal.ConsumableDao;
import game.model.Consumable;
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

@WebServlet("/consumableattributebonuscreate")
public class ConsumableAttributeBonusCreate extends HttpServlet {

    private ConsumableAttributeBonusDao consumableAttributeBonusDao;
    private ConsumableDao consumableDao;

    @Override
    public void init() throws ServletException {
        consumableAttributeBonusDao = ConsumableAttributeBonusDao.getInstance();
        consumableDao = ConsumableDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        try {
            // Retrieve parameters
            int consumableId = Integer.parseInt(req.getParameter("consumableId"));
            String attribute = req.getParameter("attribute");
            double percentageValue = Double.parseDouble(req.getParameter("percentage_value"));
            int maximumCap = Integer.parseInt(req.getParameter("maximumCap"));

            // Validate consumable
            Consumable consumable = consumableDao.getConsumableByItemId(consumableId);
            if (consumable == null) {
                messages.put("error", "Consumable with ID " + consumableId + " does not exist.");
            } else {
                // Create ConsumableAttributeBonus
                ConsumableAttributeBonus bonus = new ConsumableAttributeBonus(consumable, attribute, percentageValue, maximumCap);
                consumableAttributeBonusDao.create(bonus);
                messages.put("success", "Successfully created attribute bonus for consumable ID: " + consumableId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create attribute bonus.");
        }

        req.getRequestDispatcher("/ConsumableAttributeBonusCreate.jsp").forward(req, resp);
    }
}
