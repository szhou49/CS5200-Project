package game.model;

public class GearAttributeBonus {
    private Gear gear;
    private String attribute;
    private int bonusValue;

    public GearAttributeBonus(Gear gear, String attribute, int bonusValue) {
        this.gear = gear;
        this.attribute = attribute;
        this.bonusValue = bonusValue;
    }

    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
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
