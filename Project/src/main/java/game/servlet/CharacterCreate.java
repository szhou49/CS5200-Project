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

@WebServlet("/charactercreate")
public class CharacterCreate extends HttpServlet {

    protected CharacterDao characterDao;
    protected PlayerDao playerDao;
    protected WeaponDao weaponDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
        playerDao = PlayerDao.getInstance();
        weaponDao = WeaponDao.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String email = request.getParameter("email");
            int mainHand = Integer.parseInt(request.getParameter("mainhand"));

            // Validate player
            Player player = playerDao.getPlayerByEmail(email);
            if (player == null) {
                messages.put("success", "Error: Player with email '" + email + "' does not exist.");
                request.getRequestDispatcher("/CharacterCreate.jsp").forward(request, response);
                return;
            }

            // Validate weapon
            Weapon weapon = weaponDao.getWeaponByItemId(mainHand);
            if (weapon == null) {
                messages.put("success", "Error: Weapon with ID '" + mainHand + "' does not exist.");
                request.getRequestDispatcher("/CharacterCreate.jsp").forward(request, response);
                return;
            }

            if (characterDao.getCharactersByPlayerEmail(email) != null) {
                messages.put("success", "Error: Character already exists for email '" + email + "'.");
            } else {
                Character newCharacter = new Character(null, firstname, lastname, email, mainHand);
                characterDao.create(newCharacter);
                messages.put("success", "Successfully created character: " + firstname + " " + lastname);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        request.getRequestDispatcher("/CharacterCreate.jsp").forward(request, response);
    }
}
