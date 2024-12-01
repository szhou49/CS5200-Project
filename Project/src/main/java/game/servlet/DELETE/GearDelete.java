package game.servlet;

import game.dal.GearDao;
import game.model.Gear;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/geardelete")
public class GearDelete extends HttpServlet {

    private GearDao gearDao;

    @Override
    public void init() throws ServletException {
        gearDao = GearDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        messages.put("title", "Delete Gear");
        request.getRequestDispatcher("/GearDelete.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            Gear existingGear = gearDao.getGearByItemId(itemId);

            if (existingGear == null) {
                messages.put("error", "Gear with ID " + itemId + " does not exist.");
            } else {
                gearDao.delete(existingGear);
                messages.put("success", "Successfully deleted gear with item ID: " + itemId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid item ID format. Please check your input.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete gear.");
        }

        request.getRequestDispatcher("/GearDelete.jsp").forward(request, response);
    }
}
