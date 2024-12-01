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

@WebServlet("/playergetall")
public class PlayerGetAll extends HttpServlet {

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
        try {
            players = playerDao.getAllPlayers();
                messages.put("success", "Displaying results for all players matching: ");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        // Save the previous search term, so it can be used as the default in the input box.
        req.setAttribute("players", players);

        req.getRequestDispatcher("/PlayerGetAll.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }
}