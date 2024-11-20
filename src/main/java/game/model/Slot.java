package game.model;

public class Slot {
    private int characterId;
    private int slotIndex;
    private int itemId;
    private int quantity;
    
    // References to related objects
    private Character character;
    private Item item;
    
    public Slot(int characterId, int slotIndex, int itemId, int quantity) {
        this.characterId = characterId;
        this.slotIndex = slotIndex;
        this.itemId = itemId;
        this.quantity = quantity;
    }
    
    // Getters and setters
    public int getCharacterId() { return characterId; }
    public void setCharacterId(int characterId) { this.characterId = characterId; }
    
    public int getSlotIndex() { return slotIndex; }
    public void setSlotIndex(int slotIndex) { this.slotIndex = slotIndex; }
    
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public Character getCharacter() { return character; }
    public void setCharacter(Character character) { this.character = character; }
    
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
} 