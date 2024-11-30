package game.model;

public class Character {
    private Integer characterId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private int mainHand;  // References Weapon's item_id
    
    // References to related objects
    private Player player;
    private Weapon mainHandWeapon;
    
    public Character(Integer characterId, String firstName, String lastName, 
                    String emailAddress, int mainHand) {
        this.characterId = characterId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.mainHand = mainHand;
    }
    
    // Getters and setters
    public Integer getCharacterId() { return characterId; }
    public void setCharacterId(Integer characterId) { this.characterId = characterId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    
    public int getMainHand() { return mainHand; }
    public void setMainHand(int mainHand) { this.mainHand = mainHand; }
    
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    
    public Weapon getMainHandWeapon() { return mainHandWeapon; }
    public void setMainHandWeapon(Weapon mainHandWeapon) { this.mainHandWeapon = mainHandWeapon; }
} 