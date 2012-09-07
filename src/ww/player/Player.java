package ww.player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ww.Monster;
import ww.Skill;
import ww.action.Trade;
import ww.attribute.Attribute;
import ww.attribute.Dexterity;
import ww.attribute.Intellect;
import ww.attribute.Spirit;
import ww.attribute.Strength;
import ww.character.Character;
import ww.generator.ItemGenerator;
import ww.item.GenericMainHand;
import ww.item.Gold;
import ww.item.HealthPotion;
import ww.item.Item;
import ww.location.Area;
import ww.location.Place;
import ww.quests.Quest;
import ww.reputation.Reputation;
import ww.util.CalendarDisplay;
import ww.util.Common;
import ww.util.Logger;
import ww.util.StatusBar;

public class Player extends Common implements Serializable {
    private static final long serialVersionUID = 4947812969081734866L;
    private static final Logger LOG = new Logger(Player.class);
    
    public long timePlayed = 0;

    public static String NAME;

    private double curHp;
    private int startHp;
    private int maxHp;
    
    private int level;
    private int kills;
    private int exp;
    
    private double curMana;
    private int startMana;
    private int maxMana;
    
    private int minutes;
    private String seconds;
    
    private int strength;
    private int dexterity;
    private int intellect;
    private int spirit;
    
    private Regen regen;
    private StatusBar statusBar;
    private CalendarDisplay calendarDisplay; // for serialization purposes (save/load)
    
    private String name = "";
    
    private Area area;
    private Place place;
    private Inventory inventory;
    private Profession profession;
    
    private Map<String, Reputation> reputations;
    private Map<String, Character> party;
    private Map<String, Skill> skills;
    private Map<String, Quest> quests;
    
    public Player() {
        new Thread(new Regen(this)).start();
        
        reputations = new HashMap<String, Reputation>();
    	skills = new HashMap<String, Skill>();
    	quests = new HashMap<String, Quest>();
    	inventory = new Inventory();
    	party = new HashMap<String, Character>();
    	
    	for (Reputation reputation : Reputation.allReputations.values()) {
    	    addReputation(reputation.getEnumIdentity());
    	}
    	
    	Item chest = ItemGenerator.generateChest(0);
    	Item legs = ItemGenerator.generateLegs(0);
    	Item hands = ItemGenerator.generateHands(0);
    	Item feet = ItemGenerator.generateFeet(0);
    	Item sword = new GenericMainHand("Iron Sword");
    	
    	inventory.equip(chest, legs, hands, feet, sword);
        inventory.addItem(new Gold(50000));
        inventory.addItem(new HealthPotion());
    	
    	skills.put(Skill.ONE_HANDED,  new Skill(Skill.ONE_HANDED,   "One Handed", "Skill at using one-handed weapons"));
    	skills.put(Skill.SNEAK,       new Skill(Skill.SNEAK,        "Sneak",      "Skill at sneaking"));
    	skills.put(Skill.COOKING,     new Skill(Skill.COOKING,      "Cook",       "Skill at cooking food"));
    	skills.put(Skill.DODGE,       new Skill(Skill.DODGE,        "Dodge",      "Skill at dodging"));
    	skills.put(Skill.WIT,         new Skill(Skill.WIT,          "Wit",        "The ability to make humorous remarks in times of distress"));
    	skills.put(Skill.SLEEP,       new Skill(Skill.SLEEP,        "Sleep",      "The ability to fall asleep fast and recovering strenght while sleeping"));
    	skills.put(Skill.WILDERNESS,  new Skill(Skill.WILDERNESS,   "Wilderness Survival", "Skill at knowing what you can eat and do outdoors."));
    	skills.put(Skill.CAMP_MAKING, new Skill(Skill.CAMP_MAKING,  "Camp Making", "Skill at making camps"));
    	skills.put(Skill.FINESS,      new Skill(Skill.FINESS,       "Finess",      "Ability to execute tasks with a sence of style."));
    	skills.put(Skill.SPEECH,      new Skill(Skill.SPEECH,       "Speech",      "Ability to talk sense into people and convincing them that everything you say is true."));
    	
    	strength = 10;
    	dexterity = 10;
    	intellect = 10;
    	spirit = 10;
    	startHp = 50;
    	maxHp = calcMaxHp();
    	curHp = maxHp;
    	startMana = 50;
    	maxMana = calcMaxMana();
    	curMana = maxMana;
    	level = 1;
    	exp = 0;
    	kills = 0;
    }
    
    public void drop(Item item) {
        if (item == null) {
            return;
        }
        
        int amount = item.getAmount();
        
        if (amount == 0) {
            place.addObject(item);
            inventory.removeItem(item);
            return;
        }
        
        amount--;
        
        if (amount == 0) {
            place.addObject(item);
            inventory.removeItem(item);
        }
        else {
            place.addObject(item); // TODO: will create pretty weird results; gold copying etc
            item.setAmount(amount);
        }
    }
    
    public void addReputation(Reputation.Identity identity) {
        if (reputations == null) {
            reputations = new HashMap<String, Reputation>();
        }
        else if (reputations.containsKey(identity.ID)) {
            return;
        }
        
        Reputation reputation = Reputation.allReputations.get(identity.ID);
        
        reputation.setDiscovered(true);
        reputations.put(identity.ID, reputation);
    }
    
    public Map<String, Reputation> getReputations() {
        return reputations;
    }

    public void setReputations(Map<String, Reputation> reputations) {
        this.reputations = reputations;
    }

    public void equip(Item item) {
        getInventory().equip(item);
        getInventory().removeFromBackpack(item.getId());
    }
    
    public boolean use(Item item) {
        return item.use(this);
    }
    
    public boolean trade(Character character) {
        Trade.trade(this, character);
        
        return false;
    }
    
    public void restoreHealth(int amount) {
        curHp += amount;
        
        if (curHp > maxHp) { 
            curHp = maxHp;
        }
    }
    
    public void die() {
        killed(timePlayed);
        pause();
        System.exit(0);
    }
    
    public Set<Skill> getSkills() {
    	return new HashSet<Skill>(skills.values());
    }
    
    public Skill getSkill(String name) {
    	return skills.get(name);
    }
    
    public void addQuest(Quest quest) {
    	if (quest == null) {
    		return;
    	}
    	
    	if (quests.containsKey(quest.getId())) {
    		printbig("You already have this quest.");
    		
    		return;
    	}
    	
    	info("You accept the quest '" + quest.getName() + "'!");
    	
    	quests.put(quest.getId(), quest);
    }
    
    public Map<String, Quest> getQuests() {
    	return quests;
    }
    
    public Quest getQuest(String key) {
        if (key == null || key.length() == 0) {
            return null;
        }
        
        if (quests == null || quests.size() == 0) {
            return null;
        }
        
        if (!quests.containsKey(key)) {
            return null;
        }
        
        return quests.get(key);
    }
    
    public void status() {
    	updateTime(getTimePlayed());
    	
    	println();
    	println(
    			"| Health: " + curHp + "/"  + maxHp + 
    			" | Mana: " + curMana + "/" + maxMana +
    			" | Level: " + level + 
    			" | Exp: " + exp + 
    			" | Kills: " + kills + 
    			" | " + minutes + ":" + seconds + " |");
    	println();
    }
    
    public void gainExp(int exp) {
    	this.exp += exp;
    	
    	if (this.exp > level * 100) {
    		this.exp -= level * 100;
    		
    		level++;
    		strength++;
    		dexterity++;
    		
    		calcMaxHp();
    		calcMaxMana();
    		
    		println("Level up!");
    		println();
    	}
    }
    
    public void increaseSkill(Skill skill) {
    	skill.increase();
    }
    
    public String getLookDescription() {
    	return area.getLookDescription() + place.getLookDescription();
    }
    
    public void addKills(int kills) {
    	this.kills += kills;
    }
    
    public void addKills() {
    	kills++;
    }
    
    public void action(String action, Monster monster) {
    	println("You: You do nothing...");
    	println();
    }
    
    int calcDmg(int dmg1, int dmg2) {
    	double randomDmg = dmg1 + Math.random() * dmg2;
    	double dmg = strength * randomDmg;
    	
    	double randomCrit = Math.random() * 100;
    	
    	if(randomCrit <= meleeCrit()) {
    		
    		// TODO: times crit mod when we have weapons
    		dmg *= 2;
    		println("You: You hit a vital part!");
    	}
    	
    	return (int)dmg;
    }
    
    int calcMaxHp() {
    	maxHp = startHp + strength * 5;
    	return maxHp;
    }

    int calcMaxMana() {
    	maxMana = startMana  + intellect * 5;
    	return maxMana;
    }

    int calcSpellDmg(int dmg1, int dmg2) {
    	double randomDmg = dmg1 + Math.random() * dmg2;
    	double dmg = intellect * randomDmg;
    	
    	double randomCrit = Math.random() * 100;
    	
    	if(randomCrit <= spellCrit()) {
    		
    		// TODO: times crit mod when we have weapons
    		dmg *= 2;
    		println("You: You hit a vital part!");
    	}
    	
    	return (int)dmg;
    }
    
    public int calcDodge() {
    	if(dexterity > 40) {
    		return 20;
    	}
    	
    	double skillMod = getSkill(Skill.DODGE).getValue() / 100;
    	
    	return (int)((dexterity + skillMod) / 2);
    }
    
    double meleeCrit() {
    	if(dexterity > 40) {
    		return 20;
    	}
    	return dexterity / 2;
    }
    
    int spellCrit() {
    	if(spirit > 40) {
    		return 20;
    	}
    	return spirit / 2;
    }
    
    public void killed(long startTime) {
    	updateTime(startTime);
    	printbig("You died!");
    	println("Time played: " + minutes +  ":" + seconds);
    	println("Monsters killed: " + kills);
    	println();
    }
    
    void updateTime(long startTime) {
    	long curTime = startTime / 1000;
    	
    	minutes = (int)(curTime / 60);
    	int sec = (int)curTime % 60;
    	
    	seconds = String.valueOf(sec);
    	if (sec < 10) {
    		seconds = "0" + String.valueOf(sec);
    	}
    }

    public String getName() {
    	return name;
    }

    public void setName(String name) {
        Player.NAME = name;
    	this.name = name;
    }

    public int getCurHp() {
        return (int)curHp;
    }

    public double getCurHpDouble() {
        return curHp;
    }

    public void setCurHp(double curHp) {
    	this.curHp = curHp;
    }

    public int getCurMana() {
        return (int)curMana;
    }

    public double getCurManaDouble() {
        return curMana;
    }

    public void setCurMana(double curMana) {
    	this.curMana = curMana;
    }

    public int getLevel() {
    	return level;
    }
    
    public int getStrength() {
        int str = strength;
        
        List<Item> items = getInventory().getEquiped();
        
        for (Item item : items) {
            List<Attribute> bonuses = item.getBonuses();
            
            for (Attribute bonus : bonuses) {
                if (bonus instanceof Strength) {
                    str += bonus.getValue();
                }
            }
        }
        
    	return str;
    }

    public void setStrength(int strength) {
    	this.strength = strength;
    }

    public int getDexterity() {
        int dxt = dexterity;
        
        List<Item> items = getInventory().getEquiped();
        
        for (Item item : items) {
            List<Attribute> bonuses = item.getBonuses();
            
            for (Attribute bonus : bonuses) {
                if (bonus instanceof Dexterity) {
                    dxt += bonus.getValue();
                }
            }
        }
        
    	return dxt;
    }

    public void setDexterity(int dexterity) {
    	this.dexterity = dexterity;
    }

    public int getIntellect() {
        int intl = intellect;
        
        List<Item> items = getInventory().getEquiped();
        
        for (Item item : items) {
            List<Attribute> bonuses = item.getBonuses();
            
            for (Attribute bonus : bonuses) {
                if (bonus instanceof Intellect) {
                    intl += bonus.getValue();
                }
            }
        }
        
    	return intl;
    }

    public void setIntellect(int intellect) {
    	this.intellect = intellect;
    }

    public int getSpirit() {
        int spr = spirit;
        
        List<Item> items = getInventory().getEquiped();
        
        for (Item item : items) {
            List<Attribute> bonuses = item.getBonuses();
            
            for (Attribute bonus : bonuses) {
                if (bonus instanceof Spirit) {
                    spr += bonus.getValue();
                }
            }
        }
        
    	return spr;
    }

    public void setSpirit(int spirit) {
    	this.spirit = spirit;
    }

    public Area getArea() {
    	return area;
    }

    public void setArea(Area area) {
    	this.area = area;
    }

    public Place getPlace() {
    	return place;
    }

    public void setPlace(Place place) {
    	this.place = place;
    }

    public Profession getProfession() {
    	return profession;
    }

    public void setProfession(Profession profession) {
        ProfessionHandler.setBonuses(this, profession);
    	this.profession = profession;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getMaxHp() {
        int hp = maxHp;
        
        List<Item> items = getInventory().getEquiped();
        
        for (Item item : items) {
            List<Attribute> bonuses = item.getBonuses();
            
            for (Attribute bonus : bonuses) {
                if (bonus instanceof Strength) {
                    hp += bonus.getValue() * 5;
                }
            }
        }
        
        return hp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMana() {
        int mana = maxMana;
        
        List<Item> items = getInventory().getEquiped();
        
        for (Item item : items) {
            List<Attribute> bonuses = item.getBonuses();
            
            for (Attribute bonus : bonuses) {
                if (bonus instanceof Intellect) {
                    mana += bonus.getValue() * 5;
                }
            }
        }
        
        return mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }
    
    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }
    
    public StatusBar getStatusBar() {
        return statusBar;
    }

    public CalendarDisplay getCalendarDisplay() {
        return calendarDisplay;
    }

    public void setCalendarDisplay(CalendarDisplay calendarDisplay) {
        this.calendarDisplay = calendarDisplay;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }
    
    public void addTimePlayed(long diff) {
        this.timePlayed += diff;
    }

    public Regen getRegen() {
        return regen;
    }

    public void setRegen(Regen regen) {
        this.regen = regen;
    }

    public Map<String, Character> getParty() {
        return party;
    }

    public void setParty(Map<String, Character> party) {
        this.party = party;
    }
    
    public Character getPartyMember(String key) {
        if (party == null) {
            party = new HashMap<String, Character>();
        }
        
        return party.get(key);
    }
    
    public void addPartyMember(String key, Character character) {
        if (party == null) {
            party = new HashMap<String, Character>();
        }
        
        if (isBlank(key)) {
            LOG.warn("key was blank for character: " + character);
        }
        
        party.put(key, character);
    }
}
