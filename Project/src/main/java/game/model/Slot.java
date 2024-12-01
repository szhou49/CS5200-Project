package game.model;

public class Slot {
    private int slotIndex;
    private int quantity;
    private Character character;
    private Item item;

    public Slot(int slotIndex, int quantity, Character character, Item item) {
        this.slotIndex = slotIndex;
        this.quantity = quantity;
        this.character = character;
        this.item = item;
    }

    public int getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
} 