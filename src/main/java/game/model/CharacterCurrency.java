package game.model;

public class CharacterCurrency {
    private int characterId;
    private String currencyId;
    private int weeklyCap;
    private int amount;
    
    // References to related objects
    private Character character;
    private Currency currency;
    
    public CharacterCurrency(int characterId, String currencyId, int weeklyCap, int amount) {
        this.characterId = characterId;
        this.currencyId = currencyId;
        this.weeklyCap = weeklyCap;
        this.amount = amount;
    }
    
    // Getters and setters
    public int getCharacterId() { return characterId; }
    public void setCharacterId(int characterId) { this.characterId = characterId; }
    
    public String getCurrencyId() { return currencyId; }
    public void setCurrencyId(String currencyId) { this.currencyId = currencyId; }
    
    public int getWeeklyCap() { return weeklyCap; }
    public void setWeeklyCap(int weeklyCap) { this.weeklyCap = weeklyCap; }
    
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    
    public Character getCharacter() { return character; }
    public void setCharacter(Character character) { this.character = character; }
    
    public Currency getCurrency() { return currency; }
    public void setCurrency(Currency currency) { this.currency = currency; }
} 