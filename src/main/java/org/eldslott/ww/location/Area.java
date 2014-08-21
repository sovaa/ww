package org.eldslott.ww.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eldslott.ww.Monster;
import org.eldslott.ww.character.Character;
import org.eldslott.ww.location.bwlv.place.BustyMaidInn;
import org.eldslott.ww.location.bwlv.place.BustyMaidInnBasement;
import org.eldslott.ww.location.bwlv.place.Jail;
import org.eldslott.ww.location.bwlv.place.JailAlley;
import org.eldslott.ww.location.bwlv.place.JailDungeon;
import org.eldslott.ww.location.bwlv.place.MarekDraveksHouse;
import org.eldslott.ww.location.bwlv.place.MareksCell;
import org.eldslott.ww.location.bwlv.place.Outskirts;
import org.eldslott.ww.location.bwlv.place.TownSquare;
import org.eldslott.ww.player.Player;

public class Area implements Serializable {
    private static final long serialVersionUID = -7732257720797817805L;

    public static final String THE_BLACK_WATER_LODGE_VILLAGE = "The Black Water Lodge Village";
    
    public static Map<String, Character> allCharacters = new HashMap<String, Character>();
    public static Map<String, Place> allPlaces = new HashMap<String, Place>();
    
    private Map<String, Area> adjacentAreas;
    private Map<String, Area> adjacentAreaKeys;
    
    private Map<String, Place> places;
    private Map<String, Place> placeKeys;
    
    private List<Monster> monsters;
    private Set<String> keys;
    
    private Player player;
    
    private Place place; // current place
    
    private String name;
    private String description;
    private String lookDescription;
    private String mainKey;

    private int monsterFrequency;
    private int characterFrequency;
    private int discoveryFrequency;
    
    private boolean discovered;
    
    public Area() {
    	adjacentAreas = new HashMap<String, Area>();
    	adjacentAreaKeys = new HashMap<String, Area>();
    	
    	places = new HashMap<String, Place>();
    	placeKeys = new HashMap<String, Place>();
    	
    	monsters = new ArrayList<Monster>();
    	keys = new HashSet<String>();

    	monsterFrequency = 100;
    	characterFrequency = 100;
    	discoveryFrequency = 100;
    	
    	discovered = false;
    	
    	lookDescription = "You are ";
    }
    
    public static Monster monster(Player player) {
    	Area area = player.getArea();
    	List<Monster> areaMonsters = area.getMonsters();
    	
    	if (areaMonsters.size() == 0) {
    		return null;
    	}
    	
    	if (Math.random() * 100 > area.getMonsterFrequency()) {
    		return null;
    	}
    	
    	int index = (int)(Math.random() * areaMonsters.size());
    	
    	return areaMonsters.get(index);
    }
    
    public static Character character(Player player) {
    	Area area = player.getArea();
    	
    	if (Math.random() * 100 > area.getCharacterFrequency()) {
    		return null;
    	}
    	
    	return null;
    }
    
    public static Area area(Player player) {
    	Area area = player.getArea();

    	for (Area adjacentArea : area.getAdjecantAreas().values()) {
    		if (adjacentArea.isDiscovered()) {
    			continue;
    		}

    		if (Math.random() * 100 < adjacentArea.getDiscoveryFrequency()) {
    			return adjacentArea;
    		}
    	}
    	
    	return null;
    }
    
    public static Place place(Player player) {
    	Area area = player.getArea();

    	for (Place place : area.getPlaces().values()) {
    		if (place.isDiscovered()) {
    			continue;
    		}

    		if (Math.random() * 100 < place.getDiscoveryFrequency()) {
    			return place;
    		}
    	}
    	
    	return null;
    }
    
    public static Place getGlobalPlace(String key) {
    	return Area.allPlaces.get(key);
    }
    
    public static Character getGlobalCharacter(String key) {
    	return Area.allCharacters.get(key);
    }
    
    public static Area createAreas(Player player) {
    	Area village = village(player);
    	Area woods = woods(player);
    	Area fields = fields(player);
    	Area hills = hills(player);
    	
    	String[] woodsKeys = new String[]{
    			"woods", "w", "the dark woods", 
    			"darkwoods", "dark woods", "dw"
    	};
    	
    	String[] fieldsKeys = new String[]{
    			"fields", "f", "the fields of the dead",
    			"fields of the dead", "field"
    	};
    	
    	String[] hillsKeys = new String[]{
    			"hills", "h", "the stony hills", "hill",
    			"stony hills", "stonyhills", "stony"
    	};
    	
    	String[] villageKeys = new String[]{
    			"village", "v", "the village", 
    			"the black water lodge village", "bw", 
    			"b", "black water", "black", "lodge", 
    			"the black water", "the black water lodge"
    	};
    	
    	village.setKeys(villageKeys);
    	
    	village.setAdjacentAreaKeysFor(fields, fieldsKeys);
    	fields.setAdjacentAreaKeysFor(woods, woodsKeys);
    	fields.setAdjacentAreaKeysFor(village, villageKeys);
    	woods.setAdjacentAreaKeysFor(fields, fieldsKeys);
    	woods.setAdjacentAreaKeysFor(hills, hillsKeys);
    	
    	return village;
    }
    
    private static Area village(Player player) {
    	Area area = new Area();
    	
    	area.setPlayer(player);
    	area.setName(Area.THE_BLACK_WATER_LODGE_VILLAGE);
    	area.setDescription(Area.THE_BLACK_WATER_LODGE_VILLAGE + " is a small lodging village just off the " +
    			"old trading route used by the early human settlers of the north. Daily life " +
    			"is harsh here, since the short summers restrict the harvest to a minumum, and " +
    			"nights are cold and long. \n\n" +
    			"The community is suspicious to outsiders, owning to the rarity of visitors and " +
    			"hostility of the land. Because of this they tend to be hard to get to know, and " +
    			"even harder to trust.");

    	area.setMonsterFrequency(5);
    	area.setCharacterFrequency(30);
    	area.setDiscovered(true);
    	
    	// places
    	{
    	    new Outskirts(area, player);
    		new TownSquare(area, player);
    		new BustyMaidInn(area, player);
    		new BustyMaidInnBasement(area, player);
    			
    		// mareks house, discovered by quest
    		new MarekDraveksHouse(area, player);
    		
    		// the jail house, discovered by quest
    		new Jail(area, player);
    		new JailDungeon(area, player);
    		new JailAlley(area, player);
    		new MareksCell(area, player);
    	}
    	
    	// monsters
    	{
    		Monster rat = new Monster();
    		
    		rat.createRat();
    		
    		area.addMonster(rat);
    	}
    	
    	return area;
    }
    
    private static Area fields(Player player) {
    	Area area = new Area();
    	
    	area.setPlayer(player);
    	area.setName("The Fields of the Dead");
    	area.setDescription(
    			"The Fields of the Dead is so called because of the many great battles fought here " +
    			"during the ancient times. The place is said to be cursed and haunted because of the " +
    			"unimaginable amount of people who lost their lives here. Old wives tales, really.\n\n " +
    			"Many have seeked this place out in hopes of finding the old heroes' mythical items, " +
    			"but quite few actually return with anything of value, or return at all...");

    	area.setMonsterFrequency(50);
    	area.setCharacterFrequency(10);
    	area.setDiscoveryFrequency(40);
    	area.setDiscovered(false);
    	
    	// places
    	{
    		// outskirts
    		{
    			Place place = new Place();
    			
    			String keys[] = new String[]{
    			        "road", "r", "the road", "main road", "the main road", "mr"
    			};
    			
    			place.setMainKey("road");
    			place.setKeys(keys);
    			place.setLookDescription("on the Main Road leading through " + area.getName() + ".");
    			place.setDiscovered(true, false);
    			
    			place.setName("The Main Road");
    			place.setDescription(
    					"The road that traverses through the " + area.getName() + " is perilous and " +
    					"unknown, and not used by many since except for brave explorers and " +
    					"foolish self-titled heroes.");
    			
    			area.addPlace(place);
    			area.setPlace(place); // start place in this area
    		}
    	}
    	
    	// monster
    	{
    		Monster skeleton = new Monster();
    		Monster zombie = new Monster();
    		Monster zombieRat = new Monster();
    		Monster scavenger = new Monster();
    		
    		skeleton.createSkeleton();
    		zombie.createZombie();
    		zombieRat.createZombieRat();
    		scavenger.createHumanScavenger();
    		
    		area.addMonster(skeleton);
    		area.addMonster(zombie);
    		area.addMonster(zombieRat);
    		area.addMonster(scavenger);
    	}
    	
    	return area;
    }
    
    private static Area hills(Player player) {
    	Area area = new Area();
    	
    	area.setPlayer(player);
    	area.setName("The Stony Hills");
    	area.setDescription(
    			"The Stony Hills are a feared area by the villagers, rumoured to house the " +
    			"hideous Troll clans and their long thought-to-be enslavers, the merciless Orcs.\n\n" +
    			"While not the most intelligent of the known lesser races, the Trolls have " +
    			"near super-human strength, so take care not to get too close to them if " +
    			"you can avoid it!");

    	area.setMonsterFrequency(30);
    	area.setCharacterFrequency(5);
    	area.setDiscoveryFrequency(20);
    	area.setDiscovered(false);
    	
    	// places
    	{
    		// outskirts
    		{
    			Place place = new Place();
    			
    			String keys[] = new String[]{
    			        "road", "r", "the road", "main road", "the main road", "mr"
    			};
    			
    			place.setMainKey("road");
    			place.setKeys(keys);
    			place.setLookDescription("on the Main Road leading through " + area.getName() + ".");
    			place.setDiscovered(true, false);
    			
    			place.setName("The Main Road");
    			place.setDescription(
    					"The road that traverses through the " + area.getName() + " is perilous and " +
    					"unknown, and not used by many since except for brave explorers and " +
    					"foolish self-titled heroes.");
    			
    			area.addPlace(place);
    			area.setPlace(place); // start place in this area
    		}
    	}

    	// monster
    	{
    		Monster bandit = new Monster();
    		Monster orc = new Monster();
    		Monster troll = new Monster();
    		Monster golem = new Monster();
    		Monster rock = new Monster();
    		
    		bandit.createBandit();
    		orc.createOrc();
    		troll.createTroll();
    		golem.createGolem();
    		rock.createRock();
    		
    		area.addMonster(bandit);
    		area.addMonster(orc);
    		area.addMonster(troll);
    		area.addMonster(golem);
    		area.addMonster(rock);
    	}
    	
    	return area;
    }
    
    private static Area woods(Player player) {
    	Area area = new Area();
    	
    	area.setPlayer(player);
    	area.setName("The Dark Woods");
    	area.setDescription(
    			"A mysteriously dark and dense forest, where many have ventured " +
    			"in search of the fabled Elven realms of old and never to be seen again.\n\n" +
    			"Novice adventurers are wise to think twice before trying their luck against " +
    			"the lurking creates inhabiting this place.");

    	area.setMonsterFrequency(70);
    	area.setCharacterFrequency(5);
    	area.setDiscoveryFrequency(15);
    	area.setDiscovered(false);
    	
    	// places
    	{
    		// outskirts
    		{
    			Place place = new Place();
    			
    			String keys[] = new String[]{
    			        "road", "r", "the road", "main road", "the main road", "mr"
    			};
    			
    			place.setMainKey("road");
    			place.setKeys(keys);
    			place.setLookDescription("on the Main Road leading through " + area.getName() + ".");
    			place.setDiscovered(true, false);
    			
    			place.setName("The Main Road");
    			place.setDescription(
    					"The road that traverses through the " + area.getName() + " is perilous and " +
    					"unknown, and not used by many since except for brave explorers and " +
    					"foolish self-titled heroes.");
    			
    			area.addPlace(place);
    			area.setPlace(place); // start place in this area
    		}
    	}

    	// monster
    	{
    		Monster bear = new Monster();
    		Monster giantSpider = new Monster();
    		Monster rat = new Monster();
    		Monster direWolf = new Monster();
    		
    		bear.createBear();
    		giantSpider.createGiantSpider();
    		rat.createRat();
    		direWolf.createDireWolf();
    		
    		area.addMonster(bear);
    		area.addMonster(giantSpider);
    		area.addMonster(rat);
    		area.addMonster(direWolf);
    	}
    	
    	return area;
    }
    
    public Place getPlaceForKey(String key) {
    	Place place = placeKeys.get(key);
    	
    	if (place == null) {
    		return null;
    	}
    	
    	if (place.isDiscovered()) {
    		return place;
    	}
    	
    	return null;
    }
    
    public String getLookDescription() {
    	return lookDescription;
    }
    
    public Place getPlaceNow(String key) {
    	return places.get(key);
    }
    
    public Place getPlace(String key) {
    	Place place = places.get(key);
    	
    	if (!place.isDiscovered()) {
    		return null;
    	}
    	
    	return places.get(key);
    }
    
    public Set<String> getKnownPlacesKeys() {
        Set<String> keys = new HashSet<String>();
        
        for (String key : places.keySet()) {
            Place place = places.get(key);
            
            if (!place.isDiscovered()) {
                continue;
            }
            
            keys.add(place.getMainKey());
        }
        
        return keys;
    }
    
    public void addPlace(Place place) {
    	places.put(place.getMainKey(), place);
    	
    	for (String key : place.getKeys()) {
    		if (placeKeys.containsKey(key)) {
    			throw new IllegalStateException(
    					String.format("key '%s' for place already exists for place '%s'",
    						key, placeKeys.get(key).getName()));
    		}
    		
    		placeKeys.put(key, place);
    	}
    }
    
    public boolean isDiscovered() {
    	return discovered;
    }

    public void setDiscovered(boolean discovered) {
    	this.discovered = discovered;
    }

    private void addMonster(Monster monster) {
    	monsters.add(monster);
    }
    
    public Area getAdjecantArea(String key) {
    	return adjacentAreas.get(key);
    }
    
    public Area getAdjecantAreaKey(String key) {
    	return adjacentAreaKeys.get(key);
    }
    
    public void setAdjacentAreaKeysFor(Area area, String[] keys) {
    	area.setMainKey(keys[0]);
    	adjacentAreas.put(keys[0], area);
    	
    	for (String key : keys) {
    		if (adjacentAreaKeys.containsKey(key)) {
    			throw new IllegalStateException(
    					String.format("key '%s' for adjacent area already exists for area '%s'",
    						key, adjacentAreaKeys.get(key).getName()));
    		}
    		
    		adjacentAreaKeys.put(key, area);
    	}
    }
    
    public Set<String> getAdjacentAreaKeys() {
    	Set<String> keys = new HashSet<String>();
    	
    	for (Area area : adjacentAreas.values()) {
    		if (!area.isDiscovered()) {
    			continue;
    		}
    		
    		keys.add(area.getMainKey());
    	}
    	
    	return keys;
    }
    
    public static void addGlobalCharacter(String key, Character character) {
        allCharacters.put(key, character);
    }
    
    public static boolean hasGlobalCharacter(String key) {
        return allCharacters.containsKey(key);
    }
    
    public String getMainKey() {
    	return mainKey;
    }

    public void setMainKey(String mainKey) {
    	this.mainKey = mainKey;
    }

    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }

    public Map<String, Area> getAdjecantAreas() {
    	return adjacentAreas;
    }

    public void setAdjacentAreas(Map<String, Area> adjacentAreas) {
    	this.adjacentAreas = adjacentAreas;
    }

    public List<Monster> getMonsters() {
    	return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
    	this.monsters = monsters;
    }

    public int getMonsterFrequency() {
    	return monsterFrequency;
    }

    public void setMonsterFrequency(int monsterFrequency) {
    	this.monsterFrequency = monsterFrequency;
    }

    public int getCharacterFrequency() {
    	return characterFrequency;
    }

    public void setCharacterFrequency(int characterFrequency) {
    	this.characterFrequency = characterFrequency;
    }

    public Set<String> getKeys() {
    	return keys;
    }

    public void setKeys(String[] keys) {
    	this.keys = new HashSet<String>(Arrays.asList(keys));
    }

    public Place getPlace() {
    	return place;
    }

    public void setPlace(Place place) {
    	this.place = place;
    }

    public int getDiscoveryFrequency() {
    	return discoveryFrequency;
    }

    public void setDiscoveryFrequency(int discoveryFrequency) {
    	this.discoveryFrequency = discoveryFrequency;
    }

    public Map<String, Place> getPlaces() {
    	return places;
    }

    public void setPlaces(Map<String, Place> places) {
    	this.places = places;
    }

    public Player getPlayer() {
    	return player;
    }

    public void setPlayer(Player player) {
    	this.player = player;
    }
}
