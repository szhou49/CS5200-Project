package game.model;

public class EquippedSlot {
    private SlotEnum slot;
    private Character character;
    private Gear gear;
    
    public EquippedSlot(SlotEnum bodySlot, Character character, Gear gear) {
        this.slot = bodySlot;
        this.character = character;
        this.gear = gear;
    }
    
    // Getters and setters
    public SlotEnum getSlot() { 
        return slot; 
    }
    public void setSlot(SlotEnum slot) { 
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

    public enum SlotEnum {
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