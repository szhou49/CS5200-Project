package game.model;

public class Gear {
    private int itemId;
    private String equippedSlot;  // Using String for ENUM values
    private int requiredLevel;
    private int defenseRating;
    private int magicDefenseRating;
    private Item item;  // Reference to the parent Item
    
    public Gear(int itemId, String equippedSlot, int requiredLevel, 
                int defenseRating, int magicDefenseRating, Item item) {
        this.itemId = itemId;
        this.equippedSlot = equippedSlot;
        this.requiredLevel = requiredLevel;
        this.defenseRating = defenseRating;
        this.magicDefenseRating = magicDefenseRating;
        this.item = item;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getEquippedSlot() {
        return equippedSlot;
    }

    public void setEquippedSlot(String equippedSlot) {
        this.equippedSlot = equippedSlot;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getDefenseRating() {
        return defenseRating;
    }

    public void setDefenseRating(int defenseRating) {
        this.defenseRating = defenseRating;
    }

    public int getMagicDefenseRating() {
        return magicDefenseRating;
    }

    public void setMagicDefenseRating(int magicDefenseRating) {
        this.magicDefenseRating = magicDefenseRating;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
} 