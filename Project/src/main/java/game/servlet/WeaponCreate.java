package game.servlet;

import game.dal.ItemDao;
import game.dal.WeaponDao;
import game.model.Item;
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

@WebServlet("/weaponcreate")
public class WeaponCreate extends HttpServlet {

    private ItemDao itemDao;
    private WeaponDao weaponDao;

    @Override
    public void init() throws ServletException {
        itemDao = ItemDao.getInstance();
        weaponDao = WeaponDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        // Render the JSP page
        req.getRequestDispatcher("/WeaponCreate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            int requiredLevel = Integer.parseInt(request.getParameter("requiredLevel"));
            int damage = Integer.parseInt(request.getParameter("damage"));
            double autoAttack = Double.parseDouble(request.getParameter("autoAttack"));
            double attackDelay = Double.parseDouble(request.getParameter("attackDelay"));
            
            Item existingItem = itemDao.getItemById(itemId);
            if (existingItem == null) {
                messages.put("error", "Item with ID " + itemId + " does not exist.");
            } else {
                Weapon weapon = new Weapon(itemId, requiredLevel, damage, autoAttack, attackDelay, existingItem);
                weaponDao.create(weapon);
                messages.put("success", "Successfully created weapon for item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create weapon.");
        }

        request.getRequestDispatcher("/WeaponCreate.jsp").forward(request, response);
    }
}
