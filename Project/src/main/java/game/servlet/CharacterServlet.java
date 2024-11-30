package game.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import game.dal.AttributeDao;
import game.dal.CharacterDao;
import game.dal.CharacterJobDao;
import game.dal.PlayerDao;
import game.dal.WeaponDao;
import game.model.*;
import game.model.Character;

@WebServlet("/character")
public class CharacterServlet extends BasicServlet {
	
	protected CharacterDao characterDao;
	protected PlayerDao playerDao;
	protected WeaponDao weaponDao;
	protected CharacterJobDao characterJobDao;
	protected AttributeDao attributeDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
        playerDao = PlayerDao.getInstance();
        weaponDao = WeaponDao.getInstance();
        characterJobDao = CharacterJobDao.getInstance();
        attributeDao = AttributeDao.getInstance();
    }
	
    protected void createCharacter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String email = request.getParameter("email");
            int mainHand = Integer.parseInt(request.getParameter("mainhand"));

            // validate player
            Player player = playerDao.getPlayerByEmail(email);
            if (player == null) {
                messages.put("success", "Error: Player with email '" + email + "' does not exist.");
                request.getRequestDispatcher("/CharacterCreate.jsp").forward(request, response);
                return;
            }

            // validate weapon
            Weapon weapon = weaponDao.getWeaponByItemId(mainHand);
            if (weapon == null) {
                messages.put("success", "Error: Weapon with ID '" + mainHand + "' does not exist.");
                request.getRequestDispatcher("/CharacterCreate.jsp").forward(request, response);
                return;
            }
            
            if (characterDao.getCharactersByPlayerEmail(email) != null) {
                messages.put("success", "Error: Character '" + firstname + " " + lastname + "' already exists for the email '" + email + "'.");
            } else {
                Character newCharacter = new Character(null, firstname, lastname, email, mainHand);
                newCharacter = characterDao.create(newCharacter);

                messages.put("success", "Successfully created character: " + firstname + " " + lastname +
                        " with main hand weapon ID: " + mainHand + ".");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        request.getRequestDispatcher("/CharacterCreate.jsp").forward(request, response);
    }
	
    protected void deleteCharacter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            String characterIdStr = request.getParameter("characterId");
            int characterId = Integer.parseInt(characterIdStr);

            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("success", "Error: Character with ID " + characterId + " does not exist.");
            } else {
            	// delete
                characterDao.delete(character);
                messages.put("success", "Successfully deleted character with ID " + characterId + ".");
            }
        } catch (NumberFormatException e) {
            messages.put("success", "Error: Character ID must be a valid number.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        
        request.getRequestDispatcher("/CharacterDelete.jsp").forward(request, response);
    }

    protected void createAttribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));
            int strength = Integer.parseInt(request.getParameter("strength"));
            int dexterity = Integer.parseInt(request.getParameter("dexterity"));
            int vitality = Integer.parseInt(request.getParameter("vitality"));
            int intelligence = Integer.parseInt(request.getParameter("intelligence"));
            int mind = Integer.parseInt(request.getParameter("mind"));
            int criticalHit = Integer.parseInt(request.getParameter("criticalHit"));
            int determination = Integer.parseInt(request.getParameter("determination"));
            int directHitRate = Integer.parseInt(request.getParameter("directHitRate"));
            int defense = Integer.parseInt(request.getParameter("defense"));
            int magicalDefense = Integer.parseInt(request.getParameter("magicalDefense"));
            int attackPower = Integer.parseInt(request.getParameter("attackPower"));
            int skillSpeed = Integer.parseInt(request.getParameter("skillSpeed"));
            int attackMagicPotency = Integer.parseInt(request.getParameter("attackMagicPotency"));
            int healingMagicPotency = Integer.parseInt(request.getParameter("healingMagicPotency"));
            int spellSpeed = Integer.parseInt(request.getParameter("spellSpeed"));
            int averageItemLevel = Integer.parseInt(request.getParameter("averageItemLevel"));
            int tenacity = Integer.parseInt(request.getParameter("tenacity"));
            int piety = Integer.parseInt(request.getParameter("piety"));

            Character character = characterDao.getCharacterById(characterId);
            if (character == null) {
                messages.put("error", "Character with ID " + characterId + " does not exist.");
            } else {
                // createAttribute
                Attribute attribute = new Attribute(characterId, strength, dexterity, vitality, intelligence, mind,
                                                     criticalHit, determination, directHitRate, defense, magicalDefense,
                                                     attackPower, skillSpeed, attackMagicPotency, healingMagicPotency,
                                                     spellSpeed, averageItemLevel, tenacity, piety);
                attributeDao.create(attribute);
                messages.put("success", "Successfully created attributes for character ID: " + characterId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format. Please check your inputs.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to create attributes.");
        }

        request.getRequestDispatcher("/AttributeCreate.jsp").forward(request, response);
    }

    protected void deleteAttribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            int characterId = Integer.parseInt(request.getParameter("characterId"));

            Attribute existingAttribute = attributeDao.getAttributeByCharacterId(characterId);
            if (existingAttribute == null) {
                messages.put("error", "Attributes for character ID " + characterId + " do not exist.");
            } else {
                // delete attribute
                attributeDao.delete(existingAttribute);
                messages.put("success", "Successfully deleted attributes for character ID: " + characterId);
            }
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid character ID format. Please check your input.");
        } catch (SQLException e) {
            e.printStackTrace();
            messages.put("error", "Database error: Unable to delete attributes.");
        }

        request.getRequestDispatcher("/AttributeDelete.jsp").forward(request, response);
    }

}