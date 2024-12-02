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

@WebServlet("/playerdelete")
public class PlayerDelete extends HttpServlet {

    private PlayerDao playerDao;

    @Override
    public void init() throws ServletException {
        playerDao = PlayerDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        // Render the JSP page for deleting a player.
        messages.put("title", "Delete Player");
        request.getRequestDispatcher("/PlayerDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            messages.put("error", "Error: Email cannot be empty.");
        } else {
            try {
                Player player = playerDao.getPlayerByEmail(email);
                if (player == null) {
                    messages.put("error", "Error: No player found with email: " + email);
                } else {
                    // Delete the player
                    playerDao.delete(player);
                    messages.put("success", "Player successfully deleted with email: " + email);
                }
            } catch (Exception e) {
                e.printStackTrace();
                messages.put("error", "Database error: Unable to delete player.");
            }
        }

        request.getRequestDispatcher("/PlayerDelete.jsp").forward(request, response);
    }
}
