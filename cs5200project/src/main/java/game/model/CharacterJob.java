package game.model;

public class CharacterJob {
    private int characterId;
    private String jobName;
    private int jobLevel;
    private int currentExp;
    private int threshold;
    
    // References to related objects
    private Character character;
    private Job job;
    
    public CharacterJob(int characterId, String jobName, int jobLevel, 
                       int currentExp, int threshold) {
        this.characterId = characterId;
        this.jobName = jobName;
        this.jobLevel = jobLevel;
        this.currentExp = currentExp;
        this.threshold = threshold;
    }
    
    // Getters and setters
    public int getCharacterId() { return characterId; }
    public void setCharacterId(int characterId) { this.characterId = characterId; }
    
    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }
    
    public int getJobLevel() { return jobLevel; }
    public void setJobLevel(int jobLevel) { this.jobLevel = jobLevel; }
    
    public int getCurrentExp() { return currentExp; }
    public void setCurrentExp(int currentExp) { this.currentExp = currentExp; }
    
    public int getThreshold() { return threshold; }
    public void setThreshold(int threshold) { this.threshold = threshold; }
    
    public Character getCharacter() { return character; }
    public void setCharacter(Character character) { this.character = character; }
    
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
} 