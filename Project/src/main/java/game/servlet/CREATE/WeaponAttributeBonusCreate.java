package game.servlet;

import game.dal.WeaponAttributeBonusDao;
import game.dal.WeaponDao;
import game.model.Weapon;
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

@WebServlet("/weaponattributebonuscreate")
public class WeaponAttributeBonusCreate extends HttpServlet {

    private WeaponAttributeBonusDao weaponAttributeBonusDao;
    private WeaponDao weaponDao;

    @Override
    public void init() throws ServletException {
        weaponAttributeBonusDao = WeaponAttributeBonusDao.getInstance();
        weaponDao = WeaponDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        try {
            // Retrieve parameters
            int weaponId = Integer.parseInt(req.getParameter("weaponId"));
            String attribute = req.getParameter("attribute");
            int bonusValue = Integer.parseInt(req.getParameter("bonusValue"));

            // Validate Weapon
            Weapon weapon = weaponDao.getWeaponByItemId(weaponId);
            if (weapon == null) {
                messages.put("error", "Weapon with ID " + weaponId + " does not exist.");
            } else {
                // Create WeaponAttributeBonus
                WeaponAttributeBonus bonus = new WeaponAttributeBonus(weapon, attribute, bonusValue);
                weaponAttributeBonusDao.create(bonus);
                messages.put("success", "Successfully created attribute bonus for weapon ID: " + weaponId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create attribute bonus.");
        }

        req.getRequestDispatcher("/WeaponAttributeBonusCreate.jsp").forward(req, resp);
    }
}
