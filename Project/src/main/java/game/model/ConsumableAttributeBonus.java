package game.model;

public class ConsumableAttributeBonus {
    private Consumable consumable;
    private String attribute;
    private double percentage_value;
    private int maximumCap;

    public ConsumableAttributeBonus(Consumable consumable, String attribute, double percentage_value, int maximumCap) {
        this.consumable = consumable;
        this.attribute = attribute;
        this.percentage_value = percentage_value;
        this.maximumCap = maximumCap;
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

    public double getPercentage_value() {
        return percentage_value;
    }

    public void setPercentage_value(double percentage_value) {
        this.percentage_value = percentage_value;
    }

    public int getMaximumCap() {
        return maximumCap;
    }

    public void setMaximumCap(int maximumCap) {
        this.maximumCap = maximumCap;
    }
}
