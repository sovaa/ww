package org.eldslott.ww.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eldslott.ww.util.Color;
import org.eldslott.ww.character.Character;
import org.eldslott.ww.item.Item;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Inventory;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;
import org.eldslott.ww.util.Helper;

public class LootAction extends Common implements Action {
    private static final long serialVersionUID = 6352330214700778221L;

    public static boolean action(Player player, String what) {
        String lootKey = null;
        
        if (what.split(" ").length > 1) {
            // remove "loot " from "loot <item>", so we only get "<item>" in "what"
            lootKey = what.substring(what.split(" ")[0].length()).trim();
        }
        
        if (lootKey == null || lootKey.trim().length() == 0) {
            lootableObjects(player);
            return true;
        }
        
        Place place = player.getPlace();
        
        Map<String, Character> deadCharacters = getLootableCharacters(place);
        
        if (deadCharacters == null || deadCharacters.size() == 0) {
            clear();
            printbig("There is nothing to loot here.");
            return true;
        }
        
        Map<String, Character> aliases = Helper.getAliasCharacterAliasMap(deadCharacters);
        
        if (aliases == null || aliases.size() == 0) {
            clear();
            printbig("There is nothing to loot here.");
            return true;
        }
        
        if (!aliases.containsKey(lootKey)) {
            clear();
            printbig("There is nothing to loot here with key '" + lootKey + "'!");
            return true;
        }
        
        Character character = aliases.get(lootKey);
        
        Inventory inventory = character.getInventory();
        
        List<Item> items = inventory.getItems();
        Map<Integer, Item> mappedItems = new HashMap<Integer, Item>();
        Map<Integer, Object> choices = new HashMap<Integer, Object>();
        
        int i = 0;
        for (Item item : items) {
            String name = item.getName();
            
            if (item.isStackable()) {
                name += " (" + item.getAmount() + ")";
            }
            
            choices.put(i, name);
            mappedItems.put(i, item);
            i++;
        }

        clear();
        printbig("  Looting " + character.getName() + ".");
        Map<Integer, Boolean> checked = menuChecked(choices);
        clear();
        
        for (Entry<Integer, Boolean> entry : checked.entrySet()) {
            if (!entry.getValue()) {
                continue;
            }
            
            Item item = mappedItems.get(entry.getKey());
            
            player.getInventory().addItem(item);
            inventory.removeItem(item);
        }
        
        return false;
    }
    
    public static void lootableObjects(Player player) {
        Place place = player.getPlace();
        
        Map<String, Character> deadCharacters = getLootableCharacters(place);
        
        if (deadCharacters.size() == 0) {
            return;
        }
        
        println("There are some corpses here:");
        println();
        
        for (Character character : deadCharacters.values()) {
            println(" * " + character.getName(), Color.MAGENTA);
        }
        
        println();
    }
    
    private static Map<String, Character> getLootableCharacters(Place place) {
        Map<String, Character> deadCharacters = new HashMap<String, Character>();
        Map<String, Character> characters = place.getCharacters();
        
        if (characters == null || characters.size() == 0) {
            return deadCharacters;
        }
        
        for (Entry<String, Character> entry : characters.entrySet()) {
            if (!entry.getValue().isDead()) {
                continue;
            }
            
            deadCharacters.put(entry.getKey(), entry.getValue());
        }
        
        return deadCharacters;
    }
}
