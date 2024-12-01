package game.servlet;

import game.dal.WeaponDao;
import game.model.Weapon;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/weapondelete")
public class WeaponDelete extends HttpServlet {

    private WeaponDao weaponDao;

    @Override
    public void init() throws ServletException {
        weaponDao = WeaponDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        // Render the JSP page for deleting a weapon.
        messages.put("title", "Delete Weapon");
        request.getRequestDispatcher("/WeaponDelete.jsp").forward(request, response);
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            Weapon existingWeapon = weaponDao.getWeaponByItemId(itemId);

            if (existingWeapon == null) {
                messages.put("error", "Weapon with ID " + itemId + " does not exist.");
            } else {
                weaponDao.delete(existingWeapon);
                messages.put("success", "Successfully deleted weapon with item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid item ID format. Please check your input.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete weapon.");
        }

        request.getRequestDispatcher("/WeaponDelete.jsp").forward(request, response);
    }
}
