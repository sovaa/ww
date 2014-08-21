package org.eldslott.ww.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.item.Item;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;
import org.eldslott.ww.util.Logger;

public class Place extends Common implements Serializable {
    private static final long serialVersionUID = 6641774078836589909L;
    
    private static final Logger LOG = new Logger(Place.class);
    
    private String name;
    private String description;
    private String mainKey;
    
    private Set<String> keys;
    private Set<String> connectingPlaces;
    private Map<String, Character> characters;
    private Map<String, String> kickableEntrances;
    private List<Item> objects;
    
    private String lookDescription;
    
    // chance of discovery when exploring
    private int discoveryFrequency;
    
    // key needed to unlock door
    private String keyId;
    
    private boolean discovered;
    private boolean exit;
    private boolean locked;
    private boolean entranceKickable;
    private boolean enterable;
    private boolean safeRest;
    private boolean restable;
    private boolean outdoors;
    private Integer restCost;
    
    public Place() {
    	keys = new HashSet<String>();
    	characters = new HashMap<String, Character>();
    	connectingPlaces = new HashSet<String>();
    	kickableEntrances = new HashMap<String, String>();
    	objects = new ArrayList<Item>();
    	
    	exit = false;
    	discovered = false;
    	discoveryFrequency = 100;
    	safeRest = false;
    	restable = true;
    	restCost = 0;
    	outdoors = true;
    	locked = false;
    	entranceKickable = false;
    	enterable = true;
    }
    
    public void visit(Player player) {
        // to be overwritten
    }
    
    public void addCharacter(String key, Character character) {
    	if (!characters.containsKey(key)) {
    	    characters.put(key, character);
    	}
        
    	if (!Area.hasGlobalCharacter(key)) {
    	    Area.addGlobalCharacter(key, character);
    	}
    }
    
    public Character getCharacter(String key) {
    	return characters.get(key);
    }
    
    public boolean isDiscovered() {
    	return discovered;
    }

    public void setDiscovered(boolean discovered) {
        setDiscovered(discovered, true);
    }
    
    public void setDiscovered(boolean discovered, boolean print) {
        this.discovered = discovered;
        
        if (discovered == true && print) {
            info("You have discovered a new place in this in this area called " + name + "!");
        }
    }
    
    public int getDiscoveryFrequency() {
    	return discoveryFrequency;
    }

    public void setDiscoveryFrequency(int discoveryFrequency) {
    	this.discoveryFrequency = discoveryFrequency;
    }
    
    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public String getLookDescription() {
    	return lookDescription;
    }

    public void setLookDescription(String lookDescription) {
    	this.lookDescription = lookDescription;
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

    public Map<String, Character> getCharacters() {
    	return characters;
    }
    
    public Set<String> getKnownConnectingPlacesKeys() {
        Set<String> keys = new HashSet<String>();
        
        for (String key : connectingPlaces) {
            Place place = Area.getGlobalPlace(key);
            
            if (place == null) {
                LOG.warn("there is no global place with key: " + key);
                continue;
            }
            
            if (!place.isDiscovered()) {
                continue;
            }
            
            keys.add(place.getMainKey());
        }
        
        return keys;
    }
    
    public Map<String, Character> getDiscoveredCharacters() {
    	if (characters == null || characters.size() == 0) {
    		return new HashMap<String, Character>();
    	}
    	
    	Map<String, Character> discovered = new HashMap<String, Character>();
    	
    	for (Entry<String, Character> entry : characters.entrySet()) {
    		if (entry.getValue().isDiscovered()) {
    			discovered.put(entry.getKey(), entry.getValue());
    		}
    	}
    	
    	return discovered;
    }

    public void setCharacters(Map<String, Character> characters) {
    	this.characters = characters;
    }

    public String getMainKey() {
    	return mainKey;
    }

    public void setMainKey(String mainKey) {
    	this.mainKey = mainKey;
    }
    
    public void setKeys(String[] keys) {
    	this.keys = new HashSet<String>(Arrays.asList(keys));
    }
    
    public Set<String> getKeys() {
    	return keys;
    }

    public boolean isSafeRest() {
        return safeRest;
    }

    public void setSafeRest(boolean safeRest) {
        this.safeRest = safeRest;
    }

    public boolean isRestable() {
        return restable;
    }

    public void setRestable(boolean restable) {
        this.restable = restable;
    }

    public Integer getRestCost() {
        return restCost;
    }

    public void setRestCost(Integer restCost) {
        this.restCost = restCost;
    }

    public boolean isOutdoors() {
        return outdoors;
    }

    public void setOutdoors(boolean outdoors) {
        this.outdoors = outdoors;
    }
    
    public void removeCharacter(String key) {
        this.characters.remove(key);
    }

    public Map<String, Place> getConnectingPlaces() {
        Map<String, Place> connectMap = new HashMap<String, Place>();
        
        for (String key : connectingPlaces) {
            connectMap.put(key, Area.getGlobalPlace(key));
        }
        
        return connectMap;
    }

    public void setConnectingPlaces(Set<String> connectingPlaces) {
        this.connectingPlaces = connectingPlaces;
    }
    
    public void addConnectingPlace(String key) {
        connectingPlaces.add(key);
    }
    
    public Place getConnectingPlace(String key) {
        if (!connectingPlaces.contains(key)) {
            return null;
        }
        
        return Area.getGlobalPlace(key);
    }
    
    public List<Item> getObjects() {
        return objects;
    }

    public void setObjects(List<Item> objects) {
        this.objects = objects;
    }
    
    public void addObject(Item item) {
        this.objects.add(item);
    }
    
    public boolean keyFits(Item item) {
        if (keyId == null) {
            return false;
        }
        
        if (item == null) {
            return false;
        }
        
        if (keyId.equals(item.getId())) {
            return true;
        }
        
        return false;
    }
    
    public void kick(Player player, Place currentPlace) {
        if (!isEntranceKickable()) {
            clear();
            printbig("  You kick at " + name + ", but nothing happens.");
            return;
        }
        
        if (isLocked()) {
            setLocked(false);
        }
        
        clear();
        printbig("  You kick open the entrance to " + getName() + "!");
    }
    
    public void addKickableEntrance(String[] keys, String place) {
        for (String key : keys) {
            if (kickableEntrances.containsKey(key)) {
                LOG.error("duplicate key '%s' in kickableEntrances");
                continue;
            }
            
            kickableEntrances.put(key, place);
        }
    }
    
    public Map<String, String> getKickableEntrances() {
        return kickableEntrances;
    }

    public void setKickableEntrances(Map<String, String> kickableEntrances) {
        this.kickableEntrances = kickableEntrances;
    }

    public void setEntranceKickable(boolean kickable) {
        entranceKickable = kickable;
    }
    
    public boolean isEntranceKickable() {
        return entranceKickable;
    }
    
    public void setEnterable(boolean enterable) {
        this.enterable = enterable;
    }
    
    public boolean isEnterable() {
        return enterable;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setLocked(boolean locked, String keyId) {
        this.keyId = keyId;
        this.locked = locked;
    }
    
    public String getKeyId() {
        return keyId;
    }
}
