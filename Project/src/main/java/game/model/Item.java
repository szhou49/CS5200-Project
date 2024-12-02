package game.model;

public class Item {
    private int itemId;
    private String itemName;
    private int stackSize;
    private int vendorPrice;
    private int itemLevel;
    
    public Item(Integer itemId, String itemName, int stackSize, int vendorPrice, int itemLevel) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.stackSize = stackSize;
        this.vendorPrice = vendorPrice;
        this.itemLevel = itemLevel;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getStackSize() {
        return stackSize;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public int getVendorPrice() {
        return vendorPrice;
    }

    public void setVendorPrice(int vendorPrice) {
        this.vendorPrice = vendorPrice;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }
} 