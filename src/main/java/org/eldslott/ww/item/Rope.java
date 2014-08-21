package org.eldslott.ww.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Player;

public class Rope extends Item {
    private static final long serialVersionUID = 1762882199160408620L;
    
    private int length;
    
    public Rope(int length) {
        super("Rope", Item.Slot.none, false);
        this.length = length;
        
        int value = (int)(0.9 * length);
        
        if (value < 5) {
            value = 5;
        }
        
        setWeight((int)(0.4 * length));
        setStackable(false);
        setValue(value);
    }
    
    @Override
    public boolean use(Player player) {
        clear();
        printbig("  What do you want to use the rope on?");
        
        Place place = player.getPlace();
        List<Item> objects = place.getObjects();
        
        Map<Integer, Object> menu = new HashMap<Integer, Object>();
        Map<Integer, Item> mapped = new HashMap<Integer, Item>();
        
        int i = 1;
        menu.put(0, "<Back>");
        
        for (Item item : objects) {
            menu.put(i, item.getName());
            mapped.put(i, item);
            i++;
        }
        
        if (menu.size() == 0) {
            clear();
            printbig("  There is nothing here that you can use it on.");
            pause();
            return false;
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
            return false;
        }
        
        if (mapped.get(choice).useOn(player, this)) {
            return false;
        }
        
        pause();
        return true;
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = super.getUses();
        
        uses.add(Use.use);
        
        return uses;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
