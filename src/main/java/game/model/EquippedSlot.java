package game.model;

public class EquippedSlot {
    private int characterId;
    private int itemId;
    private String slot;  // Using String for ENUM values
    
    // References to related objects
    private Character character;
    private Gear gear;
    
    public EquippedSlot(int characterId, int itemId, String slot) {
        this.characterId = characterId;
        this.itemId = itemId;
        this.slot = slot;
    }
    
    // Getters and setters
    public int getCharacterId() { return characterId; }
    public void setCharacterId(int characterId) { this.characterId = characterId; }
    
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    
    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }
    
    public Character getCharacter() { return character; }
    public void setCharacter(Character character) { this.character = character; }
    
    public Gear getGear() { return gear; }
    public void setGear(Gear gear) { this.gear = gear; }
} 