package game.servlet;

import game.dal.CharacterDao;
import game.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CharacterGet is a servlet to retrieve and display Character information.
 */
@WebServlet("/characterget")
public class CharacterGet extends HttpServlet {

    protected CharacterDao characterDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Character> characters = new ArrayList<>();

        // Retrieve and validate email address.
        String emailAddress = req.getParameter("emailAddress");
        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            messages.put("success", "Please enter a valid email address.");
        } else {
            // Retrieve Characters and store as a message.
            try {
                characters = characterDao.getCharactersByPlayerEmail(emailAddress);
                if (characters.isEmpty()) {
                    messages.put("success", "No characters found for email: " + emailAddress);
                } else {
                    messages.put("success", "Displaying results for email: " + emailAddress);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.setAttribute("characters", characters);

        req.getRequestDispatcher("/CharacterGet.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Character> characters = new ArrayList<>();

        // Retrieve and validate email address.
        String emailAddress = req.getParameter("emailAddress");
        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            messages.put("success", "Please enter a valid email address.");
        } else {
            // Retrieve Characters and store as a message.
            try {
                characters = characterDao.getCharactersByPlayerEmail(emailAddress);
                if (characters.isEmpty()) {
                    messages.put("success", "No characters found for email: " + emailAddress);
                } else {
                    messages.put("success", "Displaying results for email: " + emailAddress);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.setAttribute("characters", characters);

        req.getRequestDispatcher("/CharacterGet.jsp").forward(req, resp);
    }
}
