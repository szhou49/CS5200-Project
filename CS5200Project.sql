-- Drop the schema if it exists
DROP DATABASE IF EXISTS CS5200Project;

-- Create the schema
CREATE DATABASE CS5200Project;
USE CS5200Project;

CREATE TABLE Player (
    email_address VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE `Character` (
    character_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    FOREIGN KEY (email_address) REFERENCES Player(email_address) ON DELETE CASCADE ON UPDATE CASCADE,
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

CREATE TABLE Currency (
    currency_name VARCHAR(50) PRIMARY KEY,
    cap INT NOT NULL,
    weekly_cap INT NOT NULL,
    isContinued BOOLEAN DEFAULT TRUE
);

CREATE TABLE CharacterCurrency (
    character_id INT,
    currency_id VARCHAR(50),
    amount INT NOT NULL,
    PRIMARY KEY (character_id, currency_id),
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (currency_id) REFERENCES Currency(currency_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Job (
	job_name VARCHAR(50) PRIMARY KEY
);

CREATE TABLE CharacterJob (
    job_name VARCHAR(50),
    character_id INT,
    level INT NOT NULL,
    current_exp INT NOT NULL,
    threshold INT NOT NULL,
    PRIMARY KEY (job_name, character_id),
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (job_name) REFERENCES Job(job_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Item (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    stack_size INT NOT NULL,
    vendor_price INT NOT NULL,
    item_level INT NOT NULL
);

CREATE TABLE Gear (
    item_id INT PRIMARY KEY,
    equipped_slot VARCHAR(50) NOT NULL,
    allowed_jobs VARCHAR(50) NOT NULL,
    required_level INT NOT NULL,
    defense_rating INT NOT NULL,
    magic_defense_rating INT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (allowed_jobs) REFERENCES Job(job_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Weapon (
    item_id INT PRIMARY KEY,
    equipped_slot VARCHAR(50) NOT NULL,
    allowed_jobs VARCHAR(50) NOT NULL,
    required_level INT NOT NULL,
    damage INT NOT NULL,
    auto_attack DECIMAL(5,2) NOT NULL,
    attack_delay DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (allowed_jobs) REFERENCES Job(job_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Consumable (
    item_id INT PRIMARY KEY,
    description TEXT NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE EquippedSlot (
    character_id INT,
    main_hand INT NOT NULL,
    head INT,
    body INT,
    hands INT,
    legs INT,
    feet INT,
    off_hand INT,
    earring INT,
    wrist INT,
    ring INT,
    PRIMARY KEY (character_id),
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (main_hand) REFERENCES Weapon(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (head) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (body) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (hands) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (legs) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (feet) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (off_hand) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (earring) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (wrist) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ring) REFERENCES Gear(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CharacterInventory (
    inventory_id INT PRIMARY KEY AUTO_INCREMENT,
    character_id INT NOT NULL,
    capacity INT NOT NULL,
    FOREIGN KEY (character_id) REFERENCES `Character`(character_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Slot (
    slot_index INT,
    inventory_id INT,
    item_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (slot_index, inventory_id),
    FOREIGN KEY (inventory_id) REFERENCES CharacterInventory(inventory_id) ON DELETE CASCADE ON UPDATE CASCADE,
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

CREATE TABLE ConsumableBonus (
    item_id INT NOT NULL,
    attribute VARCHAR(50) NOT NULL,
    percentage_value DECIMAL(5,2) NOT NULL,
    maximum_cap INT NOT NULL,
    PRIMARY KEY(item_id, attribute),
    FOREIGN KEY (item_id) REFERENCES Consumable(item_id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Player (email_address, name) VALUES
('player1@example.com', 'John Doe'),
('player2@example.com', 'Jane Smith'),
('player3@example.com', 'Alex Brown'),
('player4@example.com', 'Chris Green'),
('player5@example.com', 'Taylor White');

INSERT INTO `Character` (first_name, last_name, email_address) VALUES
('Hero', 'One', 'player1@example.com'),
('Mage', 'Two', 'player2@example.com'),
('Warrior', 'Three', 'player3@example.com'),
('Rogue', 'Four', 'player4@example.com'),
('Healer', 'Five', 'player5@example.com');

INSERT INTO Attribute (
    character_id, strength, dexterity, vitality, intelligence, mind,
    critical_hit, determination, direct_hit_rate, defense, magical_defense,
    attack_power, skill_speed, attack_magic_potency, healing_magic_potency,
    spell_speed, average_item_level, tenacity, piety
) VALUES
(1, 50, 40, 60, 30, 20, 15, 20, 10, 100, 80, 70, 60, 55, 50, 40, 45, 25, 30),
(2, 45, 55, 50, 70, 80, 25, 30, 15, 90, 85, 75, 65, 70, 60, 50, 50, 20, 40),
(3, 60, 45, 70, 40, 35, 30, 25, 20, 110, 75, 80, 55, 50, 45, 55, 55, 30, 35),
(4, 35, 60, 40, 80, 85, 20, 35, 18, 85, 95, 60, 70, 65, 75, 65, 60, 40, 45),
(5, 55, 50, 65, 60, 55, 35, 40, 22, 105, 70, 85, 75, 80, 50, 60, 65, 35, 50);

INSERT INTO Currency (currency_name, cap, weekly_cap, isContinued) VALUES
('Gold', 1000000, 50000, TRUE),
('Silver', 500000, 20000, TRUE),
('Bronze', 100000, 10000, TRUE),
('Platinum', 2000000, 80000, TRUE),
('Diamond', 3000000, 100000, TRUE);

INSERT INTO CharacterCurrency (character_id, currency_id, amount) VALUES
(1, 'Gold', 50000),
(2, 'Silver', 30000),
(3, 'Bronze', 10000),
(4, 'Platinum', 20000),
(5, 'Diamond', 40000);

INSERT INTO Job (job_name) VALUES
('Warrior'),
('Mage'),
('Rogue'),
('Healer'),
('Paladin');

INSERT INTO CharacterJob (job_name, character_id, level, current_exp, threshold) VALUES
('Warrior', 1, 50, 1000, 2000),
('Mage', 2, 40, 800, 1600),
('Rogue', 3, 35, 700, 1400),
('Healer', 4, 45, 900, 1800),
('Paladin', 5, 55, 1100, 2200);

INSERT INTO Item (name, stack_size, vendor_price, item_level) VALUES
('Warrior Armor', 1, 100, 10),
('Warrior Shield', 1, 120, 12),
('Archer Helmet', 1, 50, 8),
('Mage Robe', 1, 200, 15),
('Thief Boots', 1, 75, 5), 
('Warrior Axe', 1, 150, 10),
('Mage Staff', 1, 180, 12),
('Archer Bow', 1, 160, 8),
('Thief Dagger', 1, 90, 5),
('Healer Rod', 1, 130, 10),
('Health Potion', 10, 30, 1),
('Mana Potion', 10, 40, 1),
('Speed Elixir', 5, 50, 1),
('Defense Tonic', 5, 70, 1),
('Crit Boost', 5, 60, 1);   

INSERT INTO Gear (
    item_id, equipped_slot, allowed_jobs, required_level, defense_rating, magic_defense_rating
) VALUES
(1, 'Body', 'Warrior', 10, 25, 5),
(2, 'Off Hand', 'Mage', 12, 18, 8),
(3, 'Head', 'Rogue', 8, 12, 4),
(4, 'Body', 'Healer', 15, 22, 20),
(5, 'Feet', 'Paladin', 5, 8, 3);

INSERT INTO Weapon (
    item_id, equipped_slot, allowed_jobs, required_level, damage, auto_attack, attack_delay
) VALUES
(6, 'Main Hand', 'Warrior', 10, 30, 1.25, 2.50),
(7, 'Main Hand', 'Mage', 12, 20, 1.50, 3.00),
(8, 'Main Hand', 'Rogue', 8, 25, 1.75, 2.25),
(9, 'Main Hand', 'Healer', 5, 15, 1.10, 2.00),
(10, 'Main Hand', 'Paladin', 10, 18, 1.20, 2.75);

INSERT INTO Consumable (item_id, description) VALUES
(11, 'Heals 50 HP over 5 seconds.'),
(12, 'Restores 30 MP instantly.'),
(13, 'Increases movement speed by 20% for 10 minutes.'),
(14, 'Boosts defense by 15% for 5 minutes.'),
(15, 'Increases critical hit rate by 10% for 30 minutes.');

INSERT INTO EquippedSlot (
    character_id, main_hand, head, body, hands, legs, feet, off_hand, earring, wrist, ring
) VALUES
(1, 6, 3, 4, NULL, NULL, 5, NULL, NULL, NULL, NULL),
(2, 7, NULL, 4, NULL, NULL, 5, NULL, NULL, NULL, NULL),
(3, 8, 3, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 9, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, 10, 3, 1, NULL, NULL, 5, NULL, NULL, NULL, NULL);

INSERT INTO CharacterInventory (character_id, capacity) VALUES
(1, 100),
(2, 120),
(3, 80),
(4, 150),
(5, 110);

INSERT INTO Slot (slot_index, inventory_id, item_id, quantity) VALUES
(1, 1, 1, 1),
(2, 2, 2, 1),
(3, 3, 3, 1),
(4, 4, 4, 5),
(5, 5, 5, 1);

INSERT INTO WeaponAttributeBonus (item_id, attribute, bonus_value) 
VALUES 
(6, 'Attack Power', 10),
(7, 'Critical Hit', 5),
(8, 'Skill Speed', 8),
(9, 'Direct Hit Rate', 6),
(10, 'Defense', 4);
    
INSERT INTO GearAttributeBonus (item_id, attribute, bonus_value) 
VALUES 
(1, 'Vitality', 12),
(2, 'Dexterity', 7),
(3, 'Intelligence', 15),
(4, 'Defense', 20),
(5, 'Magic Defense', 10);

INSERT INTO ConsumableBonus (item_id, attribute, percentage_value, maximum_cap) 
VALUES 
(11, 'Health Regen', 5.00, 100),
(12, 'Mana Regen', 3.50, 50),
(13, 'Movement Speed', 10.00, 20),
(14, 'Defense Boost', 7.25, 75),
(15, 'Critical Hit Rate', 12.00, 40);



