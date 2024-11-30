package game.model;

public class Currency {
    private String currencyName;
    private int cap;
    private boolean isContinued;
    
    public Currency(String currencyName, int cap, boolean isContinued) {
        this.currencyName = currencyName;
        this.cap = cap;
        this.isContinued = isContinued;
    }
    
    // Getters and setters
    public String getCurrencyName() { return currencyName; }
    public void setCurrencyName(String currencyName) { this.currencyName = currencyName; }
    
    public int getCap() { return cap; }
    public void setCap(int cap) { this.cap = cap; }
    
    public boolean getIsContinued() { return isContinued; }
    public void setIsContinued(boolean isContinued) { this.isContinued = isContinued; }
} 