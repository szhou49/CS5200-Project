package game.servlet;

import game.dal.WeaponAttributeBonusDao;
import game.model.WeaponAttributeBonus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/weaponattributebonusdelete")
public class WeaponAttributeBonusDelete extends HttpServlet {

    private WeaponAttributeBonusDao weaponAttributeBonusDao;

    @Override
    public void init() throws ServletException {
        weaponAttributeBonusDao = WeaponAttributeBonusDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        try {
            // Retrieve parameters
            int weaponId = Integer.parseInt(req.getParameter("weaponId"));
            String attribute = req.getParameter("attribute");

            // Validate WeaponAttributeBonus
            WeaponAttributeBonus bonus = weaponAttributeBonusDao.getWeaponAttributeBonus(weaponId, attribute);
            if (bonus == null) {
                messages.put("error", "No attribute bonus found for weapon ID: " + weaponId + " and attribute: " + attribute);
            } else {
                // Delete WeaponAttributeBonus
                weaponAttributeBonusDao.delete(bonus);
                messages.put("success", "Successfully deleted attribute bonus for weapon ID: " + weaponId + " and attribute: " + attribute);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete attribute bonus.");
        }

        req.getRequestDispatcher("/WeaponAttributeBonusDelete.jsp").forward(req, resp);
    }
}
