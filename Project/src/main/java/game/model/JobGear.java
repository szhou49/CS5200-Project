package game.model;

public class JobGear {
    private Gear gear;
    private Job job;

    // Constructor
    public JobGear(Gear gear, Job job) {
        this.gear = gear;
        this.job = job;
    }

    // Getters and Setters
    public Gear getGear() {
        return gear;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
