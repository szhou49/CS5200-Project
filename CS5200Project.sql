-- Drop the schema if it exists
DROP DATABASE IF EXISTS CS5200Project;

-- Create the schema
CREATE DATABASE CS5200Project;
USE CS5200Project;

CREATE TABLE Player (
    email_address VARCHAR(255) PRIMARY KEY,
    player_name VARCHAR(255) NOT NULL
);

CREATE TABLE Item (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(100) NOT NULL,
    stack_size INT NOT NULL,
    vendor_price INT DEFAULT -1,
    item_level INT NOT NULL
);

CREATE TABLE Job (
	job_name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Currency (
    currency_name VARCHAR(50) PRIMARY KEY,
    cap INT NOT NULL,
    isContinued BOOLEAN DEFAULT TRUE
);

CREATE TABLE Gear (
    item_id INT PRIMARY KEY,
    equipped_slot ENUM('head', 'body', 'hands', 'legs', 'feet', 'off-hand', 'earring', 'wrist', 'ring'),
    required_level INT NOT NULL,
    defense_rating INT NOT NULL,
    magic_defense_rating INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE JobGear (
	item_id INT,
    job_name VARCHAR(50),
    PRIMARY KEY (item_id, job_name),
    FOREIGN KEY (item_id) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (job_name) REFERENCES Job(job_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Weapon (
    item_id INT PRIMARY KEY,
    required_level INT NOT NULL,
    damage INT NOT NULL,
    auto_attack DECIMAL(5,2) NOT NULL,
    attack_delay DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE JobWeapon (
	item_id INT,
    job_name VARCHAR(50),
    PRIMARY KEY (item_id, job_name),
    FOREIGN KEY (item_id) REFERENCES Weapon(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (job_name) REFERENCES Job(job_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Consumable (
    item_id INT PRIMARY KEY,
    item_description TEXT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE WeaponAttributeBonus (
    item_id INT,
    attribute VARCHAR(50),
    bonus_value INT NOT NULL,
    PRIMARY KEY(item_id, attribute),
    FOREIGN KEY (item_id) REFERENCES Weapon(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE GearAttributeBonus (
    item_id INT,
    attribute VARCHAR(50),
    bonus_value INT NOT NULL,
    PRIMARY KEY(item_id, attribute),
    FOREIGN KEY (item_id) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ConsumableAttributeBonus (
    item_id INT NOT NULL,
    attribute VARCHAR(50) NOT NULL,
    percentage_value DECIMAL(5,2) NOT NULL,
    maximum_cap INT NOT NULL,
    PRIMARY KEY(item_id, attribute),
    FOREIGN KEY (item_id) REFERENCES Consumable(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `Character` (
    character_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    main_hand INT NOT NULL,
    FOREIGN KEY (email_address) REFERENCES Player(email_address) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (main_hand) REFERENCES Weapon(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (first_name, last_name)
);

CREATE TABLE Attribute (
    character_id INT,
    strength INT NOT NULL,
    dexterity INT NOT NULL,
    vitality INT NOT NULL,
    intelligence INT NOT NULL,
    mind INT NOT NULL,
    critical_hit INT NOT NULL,
    determination INT NOT NULL,
    direct_hit_rate INT NOT NULL,
    defense INT NOT NULL,
    magical_defense INT NOT NULL,
    attack_power INT NOT NULL,
    skill_speed INT NOT NULL,
    attack_magic_potency INT NOT NULL,
    healing_magic_potency INT NOT NULL,
    spell_speed INT NOT NULL,
    average_item_level INT NOT NULL,
    tenacity INT NOT NULL,
    piety INT NOT NULL,
    PRIMARY KEY (character_id),
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CharacterCurrency (
    character_id INT,
    currency_id VARCHAR(50),
    weekly_cap INT NOT NULL,
    amount INT NOT NULL,
    PRIMARY KEY (character_id, currency_id),
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (currency_id) REFERENCES Currency(currency_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CharacterJob (
	character_id INT,
    job_name VARCHAR(50),
    job_level INT DEFAULT 0,
    current_exp INT NOT NULL,
    threshold INT NOT NULL,
    PRIMARY KEY (job_name, character_id),
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (job_name) REFERENCES Job(job_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE EquippedSlot (
    character_id INT,
    item_id INT,
    slot ENUM( 'head', 'body', 'hands', 'legs', 'feet', 'off-hand', 'earring', 'wrist', 'ring'),
    PRIMARY KEY (character_id, slot),
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (item_id) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Slot (
	character_id INT,
    slot_index INT,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (character_id, slot_index),
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);