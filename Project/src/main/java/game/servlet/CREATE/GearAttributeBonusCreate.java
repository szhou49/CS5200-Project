package game.servlet;

import game.dal.GearAttributeBonusDao;
import game.dal.GearDao;
import game.model.Gear;
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

@WebServlet("/gearattributebonuscreate")
public class GearAttributeBonusCreate extends HttpServlet {

    private GearAttributeBonusDao gearAttributeBonusDao;
    private GearDao gearDao;

    @Override
    public void init() throws ServletException {
        gearAttributeBonusDao = GearAttributeBonusDao.getInstance();
        gearDao = GearDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        try {
            // Retrieve parameters
            int gearId = Integer.parseInt(req.getParameter("gearId"));
            String attribute = req.getParameter("attribute");
            int bonusValue = Integer.parseInt(req.getParameter("bonusValue"));

            // Validate Gear
            Gear gear = gearDao.getGearByItemId(gearId);
            if (gear == null) {
                messages.put("error", "Gear with ID " + gearId + " does not exist.");
            } else {
                // Create GearAttributeBonus
                GearAttributeBonus bonus = new GearAttributeBonus(gear, attribute, bonusValue);
                gearAttributeBonusDao.create(bonus);
                messages.put("success", "Successfully created attribute bonus for gear ID: " + gearId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create attribute bonus.");
        }

        req.getRequestDispatcher("/GearAttributeBonusCreate.jsp").forward(req, resp);
    }
}
