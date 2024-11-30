package game.servlet.GET;

import game.dal.PlayerDao;
import game.model.Player;

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

@WebServlet("/playergetbypartialname")
public class PlayerGetByPartialName extends HttpServlet {

    protected PlayerDao playerDao;

    @Override
    public void init() throws ServletException {
        playerDao = PlayerDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Player> players = new ArrayList<>();

        // Retrieve and validate player name.
        String partialName = req.getParameter("playername");
        if (partialName == null || partialName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid player name.");
        } else {
            // Retrieve players, and store as a message.
            try {
                players = playerDao.getPlayersByPartialName(partialName);
                messages.put("success", "Displaying results for players matching: " + partialName);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
            // Save the previous search term, so it can be used as the default in the input box.
            messages.put("previousPlayerName", partialName);
        }
        req.setAttribute("players", players);

        req.getRequestDispatcher("/PlayerGetByPartialName.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<>();
        req.setAttribute("messages", messages);

        List<Player> players = new ArrayList<>();

        // Retrieve and validate player name.
        String partialName = req.getParameter("playername");
        if (partialName == null || partialName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid player name.");
        } else {
            // Retrieve players, and store as a message.
            try {
                players = playerDao.getPlayersByPartialName(partialName);
                messages.put("success", "Displaying results for players matching: " + partialName);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        req.setAttribute("players", players);

        req.getRequestDispatcher("/PlayerGetByPartialName.jsp").forward(req, resp);
    }
}