package game.model;

public class EquippedSlot {
    private Slot slot;
    private Character character;
    private Gear gear;
    
    public EquippedSlot(Slot slot) {
        this.slot = slot;
    }
    
    // Getters and setters
    public Slot getSlot() { 
        return slot; 
    }
    public void setSlot(Slot slot) { 
        this.slot = slot; 
    }
    
    public Character getCharacter() { 
        return character; 
    }

    public void setCharacter(Character character) { 
        this.character = character; 
    }
    
    public Gear getGear() { 
        return gear; 
    }
    public void setGear(Gear gear) { 
        this.gear = gear; 
    }

    public enum Slot {
        HEAD,
        BODY,
        HANDS,
        LEGS,
        FEET,
        OFF_HAND,
        EARRING,
        WRIST,
        RING
    }
} 