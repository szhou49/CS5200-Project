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
    private JobDao jobDao;
    private CharacterJobDao characterJobDao;
    protected AttributeDao attributeDao;

    @Override
    public void init() throws ServletException {
        characterDao = CharacterDao.getInstance();
        playerDao = PlayerDao.getInstance();
        weaponDao = WeaponDao.getInstance();
        jobDao = JobDao.getInstance();
        characterJobDao = CharacterJobDao.getInstance();
        attributeDao = AttributeDao.getInstance();
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

            // create character
            Character newCharacter = new Character(null, firstname, lastname, player, weapon);
            characterDao.create(newCharacter);

            String jobName = request.getParameter("jobName");
            // validate job
            Job job = jobDao.getJobByName(jobName);
            if (job == null) {
                messages.put("error", "Job '" + jobName + "' does not exist.");
                request.getRequestDispatcher("/CharacterCreate.jsp").forward(request, response);
                return;
            }
            // create character job
            CharacterJob characterJob = new CharacterJob(newCharacter, job);
            characterJobDao.create(characterJob);
            
            // read attribute data from request
            int strength = Integer.parseInt(request.getParameter("strength"));
            int dexterity = Integer.parseInt(request.getParameter("dexterity"));
            int vitality = Integer.parseInt(request.getParameter("vitality"));
            int intelligence = Integer.parseInt(request.getParameter("intelligence"));
            int mind = Integer.parseInt(request.getParameter("mind"));
            int criticalHit = Integer.parseInt(request.getParameter("critical_hit"));
            int determination = Integer.parseInt(request.getParameter("determination"));
            int directHitRate = Integer.parseInt(request.getParameter("direct_hit_rate"));
            int defense = Integer.parseInt(request.getParameter("defense"));
            int magicalDefense = Integer.parseInt(request.getParameter("magical_defense"));
            int attackPower = Integer.parseInt(request.getParameter("attack_power"));
            int skillSpeed = Integer.parseInt(request.getParameter("skill_speed"));
            int attackMagicPotency = Integer.parseInt(request.getParameter("attack_magic_potency"));
            int healingMagicPotency = Integer.parseInt(request.getParameter("healing_magic_potency"));
            int spellSpeed = Integer.parseInt(request.getParameter("spell_speed"));
            int averageItemLevel = Integer.parseInt(request.getParameter("average_item_level"));
            int tenacity = Integer.parseInt(request.getParameter("tenacity"));
            int piety = Integer.parseInt(request.getParameter("piety"));

            Attribute attribute = new Attribute(
                    strength,
                    dexterity,
                    vitality,
                    intelligence,
                    mind,
                    criticalHit,
                    determination,
                    directHitRate,
                    defense,
                    magicalDefense,
                    attackPower,
                    skillSpeed,
                    attackMagicPotency,
                    healingMagicPotency,
                    spellSpeed,
                    averageItemLevel,
                    tenacity,
                    piety,
                    newCharacter
                );
            // create character attribute
            attributeDao.create(attribute);

                
        } catch (NumberFormatException e) {
            messages.put("error", "Invalid number format.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        request.getRequestDispatcher("/CharacterCreate.jsp").forward(request, response);
    }
}
