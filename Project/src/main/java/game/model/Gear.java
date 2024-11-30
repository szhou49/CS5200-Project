package game.model;

public class Gear extends Item{
    private String equippedSlot;  // Using String for ENUM values
    private int requiredLevel;
    private int defenseRating;
    private int magicDefenseRating;
    
    public Gear(int itemId, String itemName, int stackSize, int vendorPrice, int itemLevel, String equippedSlot, int requiredLevel, 
                int defenseRating, int magicDefenseRating) {
        super(itemId, itemName, stackSize, vendorPrice, itemLevel);
        this.equippedSlot = equippedSlot;
        this.requiredLevel = requiredLevel;
        this.defenseRating = defenseRating;
        this.magicDefenseRating = magicDefenseRating;
    }

    public Gear(Item item, String equippedSlot, int requiredLevel, int defenseRating, int magicDefenseRating) {
        super(item.getItemId(), item.getItemName(), item.getStackSize(), item.getVendorPrice(), item.getItemLevel());
        this.equippedSlot = equippedSlot;
        this.requiredLevel = requiredLevel;
        this.defenseRating = defenseRating;
        this.magicDefenseRating = magicDefenseRating;
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
} 