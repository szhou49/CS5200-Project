package game.servlet;

import game.dal.GearAttributeBonusDao;
import game.model.GearAttributeBonus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/gearattributebonusdelete")
public class GearAttributeBonusDelete extends HttpServlet {

    private GearAttributeBonusDao gearAttributeBonusDao;

    @Override
    public void init() throws ServletException {
        gearAttributeBonusDao = GearAttributeBonusDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        try {
            // Retrieve parameters
            int gearId = Integer.parseInt(req.getParameter("gearId"));
            String attribute = req.getParameter("attribute");

            // Validate GearAttributeBonus
            GearAttributeBonus bonus = gearAttributeBonusDao.getGearAttributeBonus(gearId, attribute);
            if (bonus == null) {
                messages.put("error", "No attribute bonus found for gear ID: " + gearId + " and attribute: " + attribute);
            } else {
                // Delete GearAttributeBonus
                gearAttributeBonusDao.delete(bonus);
                messages.put("success", "Successfully deleted attribute bonus for gear ID: " + gearId + " and attribute: " + attribute);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete attribute bonus.");
        }

        req.getRequestDispatcher("/GearAttributeBonusDelete.jsp").forward(req, resp);
    }
}
