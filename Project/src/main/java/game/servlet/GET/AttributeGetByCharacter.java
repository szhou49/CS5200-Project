package game.servlet.GET;

import game.dal.AttributeDao;
import game.model.Attribute;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/attributegetbycharacter")
public class AttributeGetByCharacter extends HttpServlet {

    protected AttributeDao attributeDao;

    @Override
    public void init() throws ServletException {
        attributeDao = AttributeDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        String characterIdStr = req.getParameter("characterId");
        Attribute attribute = null;

        if (characterIdStr == null || characterIdStr.trim().isEmpty()) {
            messages.put("error", "Invalid Character ID.");
        } else {
            try {
                int characterId = Integer.parseInt(characterIdStr);
                attribute = attributeDao.getAttributeByCharacterId(characterId);

                if (attribute == null) {
                    messages.put("error", "Attributes for Character ID " + characterId + " do not exist.");
                } else {
                    messages.put("success", "Displaying attributes for Character ID: " + characterId);
                }
            } catch (NumberFormatException e) {
                messages.put("error", "Invalid Character ID format.");
            } catch (SQLException e) {
                e.printStackTrace();
                messages.put("error", "Unable to retrieve attributes. Please try again later.");
            }
        }

        // Save the result in request scope for rendering in JSP.
        req.setAttribute("attribute", attribute);

        req.getRequestDispatcher("/AttributeGetByCharacter.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}