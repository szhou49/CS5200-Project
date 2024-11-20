package game.model;

public class Weapon {
    private Integer itemId;
    private int requiredLevel;
    private int damage;
    private double autoAttack;
    private double attackDelay;
    private Item item;  // Reference to the parent Item
    
    public Weapon(Integer itemId, int requiredLevel, int damage, 
                 double autoAttack, double attackDelay, Item item) {
        this.itemId = itemId;
        this.requiredLevel = requiredLevel;
        this.damage = damage;
        this.autoAttack = autoAttack;
        this.attackDelay = attackDelay;
        this.item = item;
    }
    
    // Getters and setters
    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }
    
    public int getRequiredLevel() { return requiredLevel; }
    public void setRequiredLevel(int requiredLevel) { this.requiredLevel = requiredLevel; }
    
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    
    public double getAutoAttack() { return autoAttack; }
    public void setAutoAttack(double autoAttack) { this.autoAttack = autoAttack; }
    
    public double getAttackDelay() { return attackDelay; }
    public void setAttackDelay(double attackDelay) { this.attackDelay = attackDelay; }
    
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
} 