package ww;

import java.io.Serializable;

import ww.util.Common;

public class Skill extends Common implements Serializable {
    private static final long serialVersionUID = 4635655775467877512L;
    
    // combat
	public static final String COMBAT_REFLEXES = "combat-reflexes";
	public static final String ONE_HANDED = "one-handed";
	public static final String TWO_HANDED = "two-handed";
	public static final String DODGE = "dodge";
	public static final String EVADE = "evade";
	public static final String BLOCK = "block";
	public static final String PARRY = "parry";
	
	// magic
	public static final String DESTRUCTION = "desctruction";
	public static final String RESTORATION = "restoration";
	public static final String MAGIC_SENSE = "magic-sense";
	public static final String ENCHANTING = "enchanting";
	public static final String SPELL_CRAFTING = "spell-crafting";
	
	// stealth
	public static final String SNEAK = "sneak";
	public static final String HIDE = "hide";
	public static final String LISTEN = "listen";
	public static final String DISGUISE = "disguise";
	public static final String SPOT = "spot";
	public static final String PICKPOCKET = "pickpocket";
	public static final String LOCKPICK = "lockpick";
	public static final String FINESS = "finess";
	
	// social
	public static final String SPEECH = "speech";
	public static final String BARTER = "barter";
	public static final String HAGGLE = "haggle";
	public static final String DIPLOMACY = "diplomacy";
	public static final String LEADERSHIP = "leadership";
	public static final String WIT = "wit";
	
	// knowledge
	public static final String ANCIENT_LANGUAGES = "ancient-languages";
	public static final String ALCHEMY = "alchemy";
	public static final String HISTORY = "history";
	public static final String SEARCH = "search";
	
	// survival
	public static final String HUNT = "hunt";
	public static final String FORAGE = "forage";
	public static final String TRACK = "track";
	public static final String SLEEP = "sleep";
	public static final String COOKING = "cooking";
	public static final String SWIM = "swim";
	public static final String CLIMB = "climb";
	public static final String RIDING = "riding";
	public static final String JUMP = "jump";
	public static final String WILDERNESS = "wilderness-survival";
	public static final String CAMP_MAKING = "camp-making";
	
	// miscellaneous
	public static final String TREASURE_FINDING = "treasure-finding";
	public static final String ARCHEOLOGY = "archeology";
	public static final String ASTRONOMY = "astronomy";
	public static final String CONCEAL = "conceal";
	public static final String KICK = "kick";
	public static final String THROW = "throw";

	private String id;
	private String name;
	private int value;
	private String description;
	
	public Skill(String id, String name, String description) {
	    this.id = id;
		this.name = name;
		this.description = description;
		this.value = 1;
	}
	
	public void increase() {
		value++;
		info("You have gained a skill point in " + this.name + "!");
	}
	
	public String getId() {
	    return id;
	}

	public String getName() {
		return name;
	}
	
	public void setValue(int value) {
	    this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
}
