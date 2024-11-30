package game.servlet.GET;

import game.dal.WeaponDao;
import game.model.Weapon;

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

@WebServlet("/weapongetall")
public class WeaponGetAll extends HttpServlet {

    protected WeaponDao weaponDao;

    @Override
    public void init() throws ServletException {
        weaponDao = WeaponDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Weapon> weapons = new ArrayList<>();

        // Retrieve all weapons.
        try {
            weapons = weaponDao.getAllWeapons();
            messages.put("success", "Displaying all available weapons.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Unable to retrieve weapons. Please try again later.");
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("weapons", weapons);

        req.getRequestDispatcher("/WeaponGetAll.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
