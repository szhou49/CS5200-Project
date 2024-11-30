package game.model;

public class Consumable extends Item{
    private String itemDescription;

    public Consumable(int itemId, String itemName, int stackSize, int vendorPrice, int itemLevel, 
                      String itemDescription) {
        super(itemId, itemName, stackSize, vendorPrice, itemLevel);
        this.itemDescription = itemDescription;
    }

    public Consumable(Item item, String itemDescription) {
        super(item.getItemId(), item.getItemName(), item.getStackSize(), item.getVendorPrice(), item.getItemLevel());
        this.itemDescription = itemDescription;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
