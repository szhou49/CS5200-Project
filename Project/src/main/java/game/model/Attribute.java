package game.model;


public class Attribute {
    private int strength;
    private int dexterity;
    private int vitality;
    private int intelligence;
    private int mind;
    private int criticalHit;
    private int determination;
    private int directHitRate;
    private int defense;
    private int magicalDefense;
    private int attackPower;
    private int skillSpeed;
    private int attackMagicPotency;
    private int healingMagicPotency;
    private int spellSpeed;
    private int averageItemLevel;
    private int tenacity;
    private int piety;
    private Character character;  // Reference to the parent Character

	public Attribute(int strength, int dexterity, int vitality, int intelligence, int mind,
			int criticalHit, int determination, int directHitRate, int defense, int magicalDefense, int attackPower,
			int skillSpeed, int attackMagicPotency, int healingMagicPotency, int spellSpeed, int averageItemLevel,
			int tenacity, int piety, Character character) {
		this.strength = strength;
		this.dexterity = dexterity;
		this.vitality = vitality;
		this.intelligence = intelligence;
		this.mind = mind;
		this.criticalHit = criticalHit;
		this.determination = determination;
		this.directHitRate = directHitRate;
		this.defense = defense;
		this.magicalDefense = magicalDefense;
		this.attackPower = attackPower;
		this.skillSpeed = skillSpeed;
		this.attackMagicPotency = attackMagicPotency;
		this.healingMagicPotency = healingMagicPotency;
		this.spellSpeed = spellSpeed;
		this.averageItemLevel = averageItemLevel;
		this.tenacity = tenacity;
		this.piety = piety;
		this.character = character;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public int getMind() {
		return mind;
	}

	public void setMind(int mind) {
		this.mind = mind;
	}

	public int getCriticalHit() {
		return criticalHit;
	}

	public void setCriticalHit(int criticalHit) {
		this.criticalHit = criticalHit;
	}

	public int getDetermination() {
		return determination;
	}

	public void setDetermination(int determination) {
		this.determination = determination;
	}

	public int getDirectHitRate() {
		return directHitRate;
	}

	public void setDirectHitRate(int directHitRate) {
		this.directHitRate = directHitRate;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getMagicalDefense() {
		return magicalDefense;
	}

	public void setMagicalDefense(int magicalDefense) {
		this.magicalDefense = magicalDefense;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public int getSkillSpeed() {
		return skillSpeed;
	}

	public void setSkillSpeed(int skillSpeed) {
		this.skillSpeed = skillSpeed;
	}

	public int getAttackMagicPotency() {
		return attackMagicPotency;
	}

	public void setAttackMagicPotency(int attackMagicPotency) {
		this.attackMagicPotency = attackMagicPotency;
	}

	public int getHealingMagicPotency() {
		return healingMagicPotency;
	}

	public void setHealingMagicPotency(int healingMagicPotency) {
		this.healingMagicPotency = healingMagicPotency;
	}

	public int getSpellSpeed() {
		return spellSpeed;
	}

	public void setSpellSpeed(int spellSpeed) {
		this.spellSpeed = spellSpeed;
	}

	public int getAverageItemLevel() {
		return averageItemLevel;
	}

	public void setAverageItemLevel(int averageItemLevel) {
		this.averageItemLevel = averageItemLevel;
	}

	public int getTenacity() {
		return tenacity;
	}

	public void setTenacity(int tenacity) {
		this.tenacity = tenacity;
	}

	public int getPiety() {
		return piety;
	}

	public void setPiety(int piety) {
		this.piety = piety;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}
} 