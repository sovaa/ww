package ww.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ww.item.Item;
import ww.location.Place;
import ww.player.Player;
import ww.util.Common;

public class OpenAction extends Common implements Action {
    private static final long serialVersionUID = -2799450898989634389L;

    public static final boolean action(Player player, String what) {
        Place place = player.getPlace();
        List<Item> objects = place.getObjects();
        
        Map<Integer, Object> menu = new HashMap<Integer, Object>();
        Map<Integer, Item> mapped = new HashMap<Integer, Item>();
        
        int i = 1;
        menu.put(0, "<Back>");
        
        for (Item item : objects) {
            if (!item.isOpenable()) {
                continue;
            }
            
            menu.put(i, item.getName());
            mapped.put(i, item);
            i++;
        }
        
        if (menu.size() == 0) {
            clear();
            printbig("  There is nothing here that can be opened.");
            return true;
        }
        
        clear();
        int choice = menu(menu);
        
        if (choice == 0) {
            clear();
            return false;
        }
        
        if (mapped.get(choice) == null) {
            clear();
            printbig("  No such choice!");
            return true;
        }
        
        Item item = mapped.get(choice);
        
        item.open(player);
        
        return false;
    }
}
