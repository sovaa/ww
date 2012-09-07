package ww.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.lantern.terminal.Terminal.Color;

import ww.character.Character;
import ww.location.Area;
import ww.location.Place;
import ww.player.Player;

public class Helper extends Common {
    private static final long serialVersionUID = -2743663362424836793L;

    public static Map<String, Character> getAliasCharacterAliasMap(Map<String, Character> characters) {
        Map<String, Character> aliases = new HashMap<String, Character>();
        
        for (Entry<String, Character> entry : characters.entrySet()) {
            if (!aliases.containsKey(entry.getKey())) {
                aliases.put(entry.getKey(), entry.getValue());
            }
            
            Set<String> characterAliases = entry.getValue().getAliases();
            
            for (String alias : characterAliases) {
                if (!aliases.containsKey(alias)) {
                    aliases.put(alias, entry.getValue());
                }
            }
        }
        
        return aliases;
    }
    
    public static void knownPlaces(Player player) {
        Area area = player.getArea();
        Place place = player.getPlace();
        
        if (place.isExit()) {
            Set<String> knownPlacesKeys = place.getKnownConnectingPlacesKeys();
            
            if (knownPlacesKeys == null || knownPlacesKeys.size() == 0) {
                return;
            }
            
            println("You know of a few connecting places here...");
            println();
            
            for (String key : knownPlacesKeys) {
                Place knownPlace = place.getConnectingPlace(key);
                
                println(" * " + knownPlace.getName(), Color.GREEN);
            }
            
            println();
            
            return;
        }
        
        Set<String> knownPlacesKeys = area.getKnownPlacesKeys();
        
        if (knownPlacesKeys != null && knownPlacesKeys.size() > 0) {
            println("You know of a few places here...");
            println();
            
            for (String key : knownPlacesKeys) {
                Place knownPlace = area.getPlace(key);
                
                println(" * " + knownPlace.getName(), Color.GREEN);
            }
            
            println();
        }
    }
    
    public static void lookDescription(Player player) {
        /*println("   " + player.getArea().getName().toUpperCase());
        println();
        println("   " + player.getPlace().getName());
        println();*/
        printbig(getFormat(player.getPlace().getDescription()));
        //refresh();
    }
    
    public static void knownCharacters(Player player) {
        Place place = player.getPlace();
        Map<String, Character> discoveredCharacters = place.getDiscoveredCharacters();
        Map<String, Character> keys = new HashMap<String, Character>();
        
        for (Entry<String, Character> entry : discoveredCharacters.entrySet()) {
            if (entry.getValue().isDead()) {
                continue;
            }
            
            keys.put(entry.getKey(), entry.getValue());
        }
        
        if (keys == null || keys.size() == 0) {
            return;
        }
        
        println("There are a few people you can talk to here: ");
        println();
            
        for (String key : keys.keySet()) {
            println(" * " + keys.get(key).getName(), Color.YELLOW);
        }
            
        println();
        
        return;
    }
}
