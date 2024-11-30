package game.model;

public class JobWeapon {
    private Weapon weapon;
    private Job job;

    public JobWeapon(Weapon weapon, Job job) {
        this.weapon = weapon;
        this.job = job;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
