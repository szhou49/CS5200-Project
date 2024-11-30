package game.model;

public class ConsumableAttributeBonus {
    private Consumable consumable;
    private String attribute;
    private int bonusValue;

    public ConsumableAttributeBonus(Consumable consumable, String attribute, int bonusValue) {
        this.consumable = consumable;
        this.attribute = attribute;
        this.bonusValue = bonusValue;
    }

    public Consumable getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumable consumable) {
        this.consumable = consumable;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(int bonusValue) {
        this.bonusValue = bonusValue;
    }
}
