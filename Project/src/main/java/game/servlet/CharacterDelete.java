package game.servlet;

import game.dal.*;
import game.model.*;
import game.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/characterdelete")
public class CharacterDelete extends HttpServlet {

    protected CharacterDao characterDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);
        messages.put("title", "Delete Character");
        request.getRequestDispatcher("/CharacterDelete.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String characterIdStr = request.getParameter("characterId");
            int characterId = Integer.parseInt(characterIdStr);

            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("success", "Error: Character with ID " + characterId + " does not exist.");
            } else {
                characterDao.delete(character);
                messages.put("success", "Successfully deleted character with ID " + characterId + ".");
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid character ID format.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        request.getRequestDispatcher("/CharacterDelete.jsp").forward(request, response);
    }
}
