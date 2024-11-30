package game.model;

public class CharacterJob {
    private int jobLevel;
    private int currentExp;
    private int threshold;
    private Character character;
    private Job job;
    
    public CharacterJob(int jobLevel, int currentExp, int threshold, Character character, Job job) {
        this.jobLevel = jobLevel;
        this.currentExp = currentExp;
        this.threshold = threshold;
        this.character = character;
        this.job = job;
    }

    public int getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(int jobLevel) {
        this.jobLevel = jobLevel;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
} 