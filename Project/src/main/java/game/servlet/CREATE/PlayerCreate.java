package game.servlet;

import game.dal.PlayerDao;
import game.model.Player;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/playercreate")
public class PlayerCreate extends HttpServlet {

    private PlayerDao playerDao;

    @Override
    public void init() throws ServletException {
        playerDao = PlayerDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        // Render the JSP page for creating a player
        messages.put("title", "Create Player");
        request.getRequestDispatcher("/PlayerCreate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        String email = request.getParameter("email");
        String name = request.getParameter("name");

        // validate input
        if (email == null || email.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            messages.put("error", "Email and Name cannot be empty.");
        } else {
            try {
                if (playerDao.getPlayerByEmail(email) != null) {
                    messages.put("error", "Player already exists with email: " + email);
                } else {
                    // create a new player
                    Player newPlayer = new Player(email, name);
                    playerDao.create(newPlayer);
                    messages.put("success", "Player successfully created! Email: " + email + ", Name: " + name);
                }
            } catch (Exception e) {
                e.printStackTrace();
                messages.put("error", "Database error: Unable to create player.");
            }
        }
        
        request.getRequestDispatcher("/PlayerCreate.jsp").forward(request, response);
    }
}
