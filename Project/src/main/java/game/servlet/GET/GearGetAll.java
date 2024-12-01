package game.servlet.GET;


import game.dal.GearDao;
import game.model.Gear;

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

@WebServlet("/geargetall")
public class GearGetAll extends HttpServlet {

    protected GearDao gearDao;

    @Override
    public void init() throws ServletException {
        gearDao = GearDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Gear> gearList = new ArrayList<>();

        // Retrieve all gear.
        try {
            gearList = gearDao.getAllGear();
            messages.put("success", "Displaying all available gear.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Unable to retrieve gear. Please try again later.");
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("gearList", gearList);

        req.getRequestDispatcher("/GearGetAll.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
