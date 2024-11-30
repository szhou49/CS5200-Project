package game.model;

public class Character {
    private Integer characterId;
    private String firstName;
    private String lastName;
    private Player player;
    private Weapon mainHandWeapon;

    public Character(Integer characterId, String firstName, String lastName, Player player, Weapon mainHandWeapon) {
        this.characterId = characterId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.player = player;
        this.mainHandWeapon = mainHandWeapon;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Weapon getMainHandWeapon() {
        return mainHandWeapon;
    }

    public void setMainHandWeapon(Weapon mainHandWeapon) {
        this.mainHandWeapon = mainHandWeapon;
    }
} 