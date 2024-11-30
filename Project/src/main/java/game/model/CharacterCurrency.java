package game.model;

public class CharacterCurrency {
    private int weeklyCap;
    private int amount;
    private Character character;
    private Currency currency;
    
    public CharacterCurrency(int weeklyCap, int amount, Character character, Currency currency) {
        this.weeklyCap = weeklyCap;
        this.amount = amount;
        this.character = character;
        this.currency = currency;
    }

    // Getters and setters
    public int getWeeklyCap() {
        return weeklyCap;
    }

    public void setWeeklyCap(int weeklyCap) {
        this.weeklyCap = weeklyCap;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
} 