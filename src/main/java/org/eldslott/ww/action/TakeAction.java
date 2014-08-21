package org.eldslott.ww.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eldslott.ww.item.Item;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public class TakeAction extends Common implements Action {
    private static final long serialVersionUID = -4333577339459152083L;

    public static final boolean action(Player player, String what) {
        List<Item> objects = player.getPlace().getObjects();
        
        if (objects == null || objects.size() == 0) {
            clear();
            printbig("  There is nothing to take here.");
            return true;
        }
        
        Map<Integer, Object> menu = new HashMap<Integer, Object>();
        Map<Integer, Item> mapped = new HashMap<Integer, Item>();
        
        int i = 0;
        
        for (Item item : objects) {
            if (!item.isCarry()) {
                continue;
            }
            
            menu.put(i, item.getName());
            mapped.put(i, item);
            i++;
        }
        
        if (menu == null || menu.size() == 0) {
            clear();
            printbig("  There is nothing to take here.");
            return true;
        }
        
        clear();
        Map<Integer, Boolean> checked = menuChecked(menu);
        
        List<Item> objectsLeft = new ArrayList<Item>();

        for (Item item : objects) {
            if (!item.isCarry()) {
                objectsLeft.add(item);
            }
        }
        
        for (Entry<Integer, Boolean> entry : checked.entrySet()) {
            Item item = mapped.get(entry.getKey());
            
            if (entry.getValue() == null || entry.getValue() == false) {
                if (entry.getKey() != null) {
                    objectsLeft.add(item);
                }
                
                continue;
            }
            
            if (entry.getKey() == null) {
                continue;
            }
            
            if (item == null) {
                continue;
            }
            
            player.getInventory().addItem(item);
        }
        
        objects.clear();
        objects.addAll(objectsLeft);
        clear();
        
        return false;
    }
}
