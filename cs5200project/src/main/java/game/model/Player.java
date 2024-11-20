package game.model;

public class Player {
    private String emailAddress;
    private String playerName;
    
    public Player(String emailAddress, String playerName) {
        this.emailAddress = emailAddress;
        this.playerName = playerName;
    }
    
    // Getters and setters
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
} 