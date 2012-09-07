package ww.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lantern.terminal.Terminal.Color;

import ww.item.Item;
import ww.location.Area;
import ww.location.Place;
import ww.player.Player;
import ww.util.Common;

public class VisitAction extends Common implements Action {
    private static final long serialVersionUID = -7920440188191767613L;

    public static boolean action(Player player, String what) {
        String placeKey = null;
        if (what.split(" ").length > 1) {
            // remove "visit " from "visit <place>", so we only get "<place>" in "what"
            placeKey = what.substring(what.split(" ")[0].length()).trim();
        }
        
        Set<String> names = new HashSet<String>();
        Map<String, Place> placeMap = new HashMap<String, Place>();
        
        if (player.getPlace().isExit()) {
            Place place = player.getPlace();
            Set<String> keys = place.getKnownConnectingPlacesKeys();
            
            for (String key : keys) {
                Place connectingPlace = place.getConnectingPlace(key);
                
                names.add(connectingPlace.getName());
                
                for (String alias : connectingPlace.getKeys()){
                    placeMap.put(alias, connectingPlace);
                }
            }
        }
        else {
            Area area = player.getArea();
            Set<String> keys = area.getKnownPlacesKeys();
            
            for (String key : keys) {
                Place place = area.getPlace(key);

                names.add(place.getName());
                
                for (String alias : place.getKeys()){
                    placeMap.put(alias, place);
                }
            }
        }
        
        if (names == null || names.size() == 0) {
            Place place = player.getPlace();
            
            clear();
            
            if (place.isExit()) {
                println();
                print("  You need to exit ");
                print(place.getName(), Color.GREEN);
                println(" before you can visit other places.");
                println();
            }
            else {
                printbig("  You can't visit any other place from here.");
            }
            
            return true;
        }
        
        if (placeKey == null) {
            clear();
            printbig("From here you can visit: ");
            
            for (String name : names) {
                println(" * " + name, Color.GREEN);
            }
            
            println();
            
            return true;
        }
        
        Place place = placeMap.get(placeKey);
        
        if (place == null) {
            clear();
            printbig("  There is no place called '" + placeKey + "'.");
            return true;
        }
        
        if (place.isLocked()) {
            List<Item> backpack = player.getInventory().getBackpack();
            
            for (Item item : backpack) {
                if (place.keyFits(item)) {
                    place.setLocked(false);
                    break;
                }
            }
        }
        
        if (place.isLocked()) {
            clear();
            printbig("  You don't seem to have a key to unlock " + place.getName() + "!");
            return true;
        }
        
        if (!place.isEnterable()) {
            clear();
            printbig("  You can't enter " + place.getName() + "!");
            return true;
        }
        
        player.setPlace(place);
        
        clear();
        info("You visit " + place.getName() + ".");
        place.visit(player);
        println();
        refresh();
        
        return false;
    }
}
