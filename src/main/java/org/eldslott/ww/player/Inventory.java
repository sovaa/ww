package org.eldslott.ww.player;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.googlecode.lanterna.input.Key;
import org.eldslott.ww.item.Item;
import org.eldslott.ww.item.Item.Slot;
import org.eldslott.ww.item.Item.Use;
import org.eldslott.ww.util.Common;

public class Inventory extends Common {
    private static final long serialVersionUID = 6958921554469570359L;
    
    private static final EnumSet<Item.Use> inventoryUses = EnumSet.of(Use.drop, Use.equip, Use.use);
    
    private Map<Item.Slot, Item> equiped = new HashMap<Item.Slot, Item>();
    private Map<String, Item> backpack = new HashMap<String, Item>();
    
    public void equip(Item ... items) {
        for (Item item : items) {
            if (item.getSlot() == null || item.getSlot().equals(Slot.none)) {
                continue;
            }
            
            if (equiped.containsKey(item.getSlot())) {
                Item equip = equiped.get(item.getSlot());
                
                backpack.put(equip.getId(), equip);
            }
            
            equiped.put(item.getSlot(), item);
        }
    }
    
    public Item getItem(String id) {
        return backpack.get(id);
    }
    
    public List<Item> getItems() {
        List<Item> items = new ArrayList<Item>();

        items.addAll(equiped.values());
        items.addAll(backpack.values());
        
        return items;
    }
    
    public void removeFromBackpack(String id) {
        backpack.remove(id);
    }
    
    public List<Item> getEquiped() {
        return new ArrayList<Item>(equiped.values());
    }
    
    public void removeItem(Item item) {
        if (backpack.containsKey(item.getId())) {
            backpack.remove(item.getId());
            return;
        }
        
        Item.Slot slot = null;
        for (Item equip : equiped.values()) {
            if (equip.getId().equalsIgnoreCase(item.getId())) {
                slot = equip.getSlot();
                break;
            }
        }
        
        if (slot != null) {
            equiped.remove(slot);
        }
    }

    public List<Item> getBackpack() {
        return new ArrayList<Item>(backpack.values());
    }

    public void setBackpack(Map<String, Item> backpack) {
        this.backpack = backpack;
    }
    
    public void addItem(Item item) {
        if (this.backpack.containsKey(item.getId())) {
            Item back = this.backpack.get(item.getId());
            
            if (back.isStackable()) {
                back.stack(item);
            }
        }
        else {
            this.backpack.put(item.getId(), item);
        }
    }
    
    private static final Map<Integer, Item> addBack(Map<Integer, Item> originalObjects) {
        Map<Integer, Item> objects = new HashMap<Integer, Item>();
        
        Item back = new Item() {
            private static final long serialVersionUID = -1963952526367200561L;

            @Override
            public String getName() {
                return "<Back>";
            }
            
            @Override
            public String getNameWithBonus() {
                return "<Back>";
            }
        };
        
        objects.put(0, back);
        
        for (Entry<Integer, Item> entry : originalObjects.entrySet()) {
            objects.put(entry.getKey() + 1, entry.getValue());
        }
        
        return objects;
    }
    
    public static final boolean menuItems(Player player, Map<Integer, Item> originalObjects, boolean center) {
        Key choice;
        int current = 0;
        
        Map<Integer, Item> objects = addBack(originalObjects);
        Map<Integer, Integer> clearCache = new HashMap<Integer, Integer>();
        
        int col = getCol();
        int row = getRow();
        
        Map<Integer, Integer> centerSpaces = new HashMap<Integer, Integer>();
        
        for (Entry<Integer, Item> entry : objects.entrySet()) {
            centerSpaces.put(entry.getKey(), entry.getValue().getNameWithBonus().length());
        }
        
        while (true) {
            setCursorPosition(col, row);
            
            for (int j = 0; j < objects.size(); j++) {
                if (!clearCache.containsKey(j)) {
                    increaseRow();
                    continue;
                }
                
                int clears = clearCache.get(j);
                
                int centerSpace = 0;
                
                if (center) {
                    centerSpace += getMaxPrintableCols() / 2;
                    centerSpace -= centerSpaces.get(j) / 2;
                }
                
                for (int i = 0; i < clears + centerSpace; i++) {
                    print(" ");
                }
                
                println();
            }
            
            setCursorPosition(col, row);
            
            int currentRow = 0;
            int currentColumn = 0;
            
            for (int j = 0; j < objects.size(); j++) {
                if (!clearCache.containsKey(j)) {
                    int clears = 2 + objects.get(j).getNameWithBonus().length();
                    clearCache.put(j, clears);
                }
                
                int centerSpace = 0;
                
                if (center) {
                    centerSpace += getMaxPrintableCols() / 2;
                    centerSpace -= centerSpaces.get(j) / 2;
                }
                
                Item item = objects.get(j);
                String name = item.getNameWithBonus();
                
                if (item.getAmount() > 1) {
                    name += " (" + item.getAmount() + ")";
                }
                
                if (j == current) {
                    currentRow = getRow();
                    currentColumn = getCol() + centerSpace + 1;
                    
                    print("  ");
                    
                    for (int i = 0; i < centerSpace; i++) {
                        print(" ");
                    }
                    
                    printlnInverse(name, item.getColor());
                }
                else {
                    print("  ");
                    
                    for (int i = 0; i < centerSpace; i++) {
                        print(" ");
                    }
                    
                    println(name, item.getColor());
                }
            }
            
            println();
            setCursorPosition(currentColumn, currentRow);
            refresh();
            
            while (true) {
                choice = getKey();
                
                if (choice == null) {
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.Enter)) {
                    // back
                    if (current == 0) {
                        return false;
                    }
                    
                    List<Use> uses = objects.get(current).getUses();
                    
                    Map<Integer, String> useObjects = new HashMap<Integer, String>();
                    Map<Integer, Use> useMap = new HashMap<Integer, Item.Use>();
                    useObjects.put(0, "<Back>");
                    
                    int j = 0;
                    for (int i = 0; i < uses.size(); i++) {
                        if (!inventoryUses.contains(uses.get(i))) {
                            continue;
                        }
                        
                        useObjects.put(++j, uses.get(i).toString());
                        useMap.put(j, uses.get(i));
                    }
                    
                    int useChoice = boxMenu(useObjects);
                    
                    if (useChoice == 0) {
                        break;
                    }
                    
                    Use use = useMap.get(useChoice);
                    Item item = objects.get(current);
                    
                    if (use.equals(Use.drop)) {
                        player.drop(item);
                        return true;
                    }
                    else if (use.equals(Use.equip)) {
                        player.equip(item);
                        return true;
                    }
                    else if (use.equals(Use.use)) {
                        if (player.use(item)) {
                            return false;
                        }
                        
                        return true;
                    }
                    else if (use.equals(Use.combine)) {
                        clear();
                        printbig("To be implemented");
                        pause();
                    }
                }
                
                if (choice.getKind().equals(Key.Kind.ArrowUp) && current > 0) {
                    current--;
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.ArrowDown) && current < objects.size() - 1) {
                    current++;
                    break;
                }
            }
        }
    }
}
