package game.model;

public class WeaponAttributeBonus {
    private Weapon weapon;
    private String attribute;
    private int bonusValue;
    
    public WeaponAttributeBonus(Weapon weapon, String attribute, int bonusValue) {
        this.weapon = weapon;
        this.attribute = attribute;
        this.bonusValue = bonusValue;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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
