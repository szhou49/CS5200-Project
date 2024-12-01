package game.servlet;

import game.dal.*;
import game.model.*;
import game.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/characterdelete")
public class CharacterDelete extends HttpServlet {

	private CharacterDao characterDao;
    private AttributeDao attributeDao;
    private CharacterJobDao characterJobDao;
    private CharacterCurrencyDao characterCurrencyDao;
    private EquippedSlotDao equippedSlotDao;

    @Override
    public void init() throws ServletException {
    	characterDao = CharacterDao.getInstance();
        attributeDao = AttributeDao.getInstance();
        characterJobDao = CharacterJobDao.getInstance();
        characterCurrencyDao = CharacterCurrencyDao.getInstance();
        equippedSlotDao = EquippedSlotDao.getInstance();
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
                messages.put("error", "Character with ID " + characterId + " does not exist.");
                request.getRequestDispatcher("/CharacterDelete.jsp").forward(request, response);
                return;
            }
            
            // delete character attribute
            Attribute attribute = attributeDao.getAttributeByCharacterId(characterId);
            
            // delete associated character jobs
            List<CharacterJob> characterJobs = characterJobDao.getCharacterJobsByCharacterId(characterId);
            for (CharacterJob job : characterJobs) {
                characterJobDao.delete(job);
            }
            
            // if exist, delete associated character currency
            List<CharacterCurrency> characterCurrencies = characterCurrencyDao.getCharacterCurrenciesByCharacterId(characterId);
            if (characterCurrencies != null) {
            	for (CharacterCurrency currency : characterCurrencies) {
            		characterCurrencyDao.delete(currency);
                }
            }
            
            // if exist, delete associated character equipped slot
            List<EquippedSlot> equippedSlots = equippedSlotDao.getEquippedSlotsByCharacterId(characterId);
            if (equippedSlots != null) {
            	for (EquippedSlot slot : equippedSlots) {
                    equippedSlotDao.delete(slot);
                }
            }
            // delete character
            characterDao.delete(character);
            
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid character ID format.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        request.getRequestDispatcher("/CharacterDelete.jsp").forward(request, response);
    }
}
