package game.model;

public class Weapon extends Item{
    private int requiredLevel;
    private int damage;
    private double autoAttack;
    private double attackDelay;
    
    public Weapon(int itemId, String itemName, int stackSize, int vendorPrice, int itemLevel,
                 int requiredLevel, int damage, 
                 double autoAttack, double attackDelay) {
        super(itemId, itemName, stackSize, vendorPrice, itemLevel);
        this.requiredLevel = requiredLevel;
        this.damage = damage;
        this.autoAttack = autoAttack;
        this.attackDelay = attackDelay;
    }

    public Weapon(Item item, int requiredLevel, int damage, double autoAttack, double attackDelay) {
        super(item.getItemId(), item.getItemName(), item.getStackSize(), item.getVendorPrice(), item.getItemLevel());
        this.requiredLevel = requiredLevel;
        this.damage = damage;
        this.autoAttack = autoAttack;
        this.attackDelay = attackDelay;
    }
    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(double autoAttack) {
        this.autoAttack = autoAttack;
    }

    public double getAttackDelay() {
        return attackDelay;
    }

    public void setAttackDelay(double attackDelay) {
        this.attackDelay = attackDelay;
    }
} 