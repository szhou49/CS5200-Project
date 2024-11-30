package game.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import game.dal.PlayerDao;
import game.model.Player;

@WebServlet("/player")
public class PlayerServlet extends BasicServlet {
	
	protected PlayerDao playerDao;
	
	@Override
	public void init() throws ServletException {
		playerDao = PlayerDao.getInstance();
	}
	
	protected void createPlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        
		String email = request.getParameter("email");
        String name = request.getParameter("name");
        
        if (email == null || email.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            messages.put("success", "Email and Name cannot be empty.");
        } else {
            try {
            	// validate player
                if (playerDao.getPlayerByEmail(email) != null) {
                    messages.put("success", "Player already exists with email: " + email);
                } else {
                    // create player
                    Player newPlayer = new Player(email, name);
                    playerDao.create(newPlayer);
                    messages.put("success", "Player successfully created! Email: " + email + ", Name: " + name);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        request.getRequestDispatcher("/PlayerCreate.jsp").forward(request, response);	
	}
	
	protected void deletePlayer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Map<String, String> messages = new HashMap<>();
	    request.setAttribute("messages", messages);

	    String email = request.getParameter("email");

	    if (email == null || email.trim().isEmpty()) {
	        messages.put("success", "Error: Email cannot be empty.");
	    } else {
	        try {
	            Player player = playerDao.getPlayerByEmail(email);
	            if (player == null) {
	                messages.put("success", "Error: No player found with email: " + email);
	            } else {
	                // delete player
	                playerDao.delete(player);
	                messages.put("success", "Player successfully deleted with email: " + email);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new IOException(e);
	        }
	    }

	    request.getRequestDispatcher("/PlayerDelete.jsp").forward(request, response);
	}
	
}