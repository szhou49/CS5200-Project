package game.model;

public class Consumable {
    private int itemId;
    private String itemDescription;
    private Item item;

    public Consumable(int itemId, String itemDescription, Item item) {
        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.item = item;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
