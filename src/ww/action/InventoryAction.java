package ww.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ww.item.Item;
import ww.item.Item.Slot;
import ww.player.Inventory;
import ww.player.Player;
import ww.util.Common;

public class InventoryAction extends Common implements Action {
    private static final long serialVersionUID = -867403632271459870L;

    public static boolean action(Player player) {
        boolean run = true;
        
        while (run) {
            clear();
            
            Inventory inventory = player.getInventory();
            
            List<Item> items = inventory.getEquiped();
            List<Item> back = inventory.getBackpack();
            
            if ((items == null || items.size() == 0) && (back == null || back.size() == 0)) {
                printbig("You currently don't have any items equiped.");
                return true;
            }
            
            if (items != null && items.size() > 0) {
                printbig("  Equiped items:");
                showItems2(items);
                println();
            }
            
            if (back != null && back.size() > 0) {
                printbig("  Items in backpack:");
                
                run = showItems(player, back);
                
                println();
            }
        }
        
        clear();
        return false;
    }
    
    public static boolean showItems(Player player, List<Item> items) {
        Map<Integer, Item> mapped = new HashMap<Integer, Item>();

        int i = 0;
        for (Item item : items) {
            mapped.put(i++, item);
        }
        
        return Inventory.menuItems(player, mapped, false);
    }
    
    private static void showItems2(List<Item> items) {
        int width = 15;
        
        for (Item item : items) {
            if (item == null) {
                continue;
            }
            
            String slot = item.getSlot().toString();

            int spaces = 0;
            
            if (slot != null && !slot.equals(Slot.none.toString())) {
                print(" " + slot);
                spaces = width - slot.length() - 1;
            }
            else {
                spaces = 1;
            }
            
            for (int i = 0; i < spaces; i++) {
                print(" ");
            }
            
            if (item.getAmount() > 0) {
                print(item.getNameWithBonus(), item.getColor());
                println(" (" + item.getAmount() + ")");
            }
            else {
                println(item.getNameWithBonus(), item.getColor());
            }
        }
    }
}
