package game.tools;

import java.sql.SQLException;
import java.util.List;

import game.dal.AttributeDao;
import game.dal.CharacterCurrencyDao;
import game.dal.CharacterDao;
import game.dal.CharacterJobDao;
import game.dal.CurrencyDao;
import game.dal.ItemDao;
import game.dal.JobDao;
import game.dal.PlayerDao;
import game.dal.WeaponDao;
import game.model.Item;
import game.model.Job;
import game.model.Player;
import game.model.Weapon;
import game.model.Attribute;
import game.model.Character;
import game.model.CharacterCurrency;
import game.model.CharacterJob;
import game.model.Currency;

public class Driver {
    public static void main(String[] args) {
        /* 
        try {
            // Initialize DAOs
            
            PlayerDao playerDao = PlayerDao.getInstance();
            ItemDao itemDao = ItemDao.getInstance();
            WeaponDao weaponDao = WeaponDao.getInstance();
            CharacterDao characterDao = CharacterDao.getInstance();
            AttributeDao attributeDao = AttributeDao.getInstance();
            JobDao jobDao = JobDao.getInstance();
            CharacterJobDao characterJobDao = CharacterJobDao.getInstance();
            CurrencyDao currencyDao = CurrencyDao.getInstance();
            CharacterCurrencyDao characterCurrencyDao = CharacterCurrencyDao.getInstance();
            
            // Create a player
            Player player = new Player("test@example.com", "TestPlayer");
            player = playerDao.create(player);
            System.out.println("Created player: " + player.getPlayerName());
            
            // Create an item and weapon for main hand
            Item weaponItem = new Item(1, "Test Sword", 1, 1000, 50);
            weaponItem = itemDao.create(weaponItem);
            Weapon weapon = new Weapon(weaponItem.getItemId(), 50, 100, 2.5, 2.8, weaponItem);
            weapon = weaponDao.create(weapon);
            System.out.println("Created weapon: " + weapon.getItem().getItemName());
            
            // Create a character
            Character character = new Character(1, "Test", "Character", player, weapon);
            character = characterDao.create(character);
            System.out.println("Created character: " + character.getFirstName() + " " + 
                             character.getLastName());
            
            // Create attributes for the character
            Attribute attribute = new Attribute(
                character.getCharacterId(), 100, 90, 80, 70, 60, 50,
                40, 30, 100, 90, 80, 70, 60, 50, 40, 30, 20, 10
            );
            attribute = attributeDao.create(attribute);
            System.out.println("Created attributes for character");
            
            // Create a job
            Job job = new Job("Warrior");
            job = jobDao.create(job);
            System.out.println("Created job: " + job.getJobName());
            
            // Assign job to character
            CharacterJob characterJob = new CharacterJob(1, 0, 1000, character, job);
            characterJob = characterJobDao.create(characterJob);
            System.out.println("Assigned job to character");
            
            // Create a currency
            Currency currency = new Currency("Gil", 999999999, true);
            currency = currencyDao.create(currency);
            System.out.println("Created currency: " + currency.getCurrencyName());
            
            // Assign currency to character
            CharacterCurrency characterCurrency = new CharacterCurrency(0, 1000, character, currency);
            characterCurrency = characterCurrencyDao.create(characterCurrency);
            System.out.println("Assigned currency to character");
            
            // Test search functionality
            List<Character> playerCharacters = characterDao.getCharactersByPlayerEmail(player.getEmailAddress());
            System.out.println("Found " + playerCharacters.size() + " characters for player");
            
            List<CharacterJob> characterJobs = characterJobDao.getJobsByCharacter(character.getCharacterId());
            System.out.println("Found " + characterJobs.size() + " jobs for character");
            
            // Test update functionality
            characterCurrency = characterCurrencyDao.updateAmount(characterCurrency, 2000);
            System.out.println("Updated character's currency amount to: " + characterCurrency.getAmount());
            
            characterJob = characterJobDao.updateLevelAndExp(characterJob, 2, 0, 2000);
            System.out.println("Updated character's job level to: " + characterJob.getJobLevel());
            
            attribute = attributeDao.updateStrength(attribute, 110);
            System.out.println("Updated character's strength to: " + attribute.getStrength());
            
            // Clean up (delete in reverse order of creation due to foreign key constraints)
            characterCurrencyDao.delete(characterCurrency);
            currencyDao.delete(currency);
            characterJobDao.delete(characterJob);
            jobDao.delete(job);
            attributeDao.delete(attribute);
            characterDao.delete(character);
            weaponDao.delete(weapon);
            itemDao.delete(weaponItem);
            playerDao.delete(player);
            System.out.println("Cleanup complete");
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }
} 