package ww.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ww.player.Player;


public class Container extends Item {
    private static final long serialVersionUID = -5166754673508058041L;
    
    private Map<String, Item> items;
    
    public Container() {
        super("Container", Item.Slot.none, true);
        setCarry(false);
        setOpenable(true);
        
        items = new HashMap<String, Item>();
    }
    
    public Container(Item ... includes) {
        super("Container", Item.Slot.none, true);
        setCarry(false);
        setOpenable(true);
        
        items = new HashMap<String, Item>();
        
        for (Item item : includes) {
            if (items.containsKey(item.getId())) {
                Item dupe = items.get(item.getId());
                
                if (dupe.isStackable()) {
                    dupe.stack(item);
                    continue;
                }
                
                continue;
            }
            
            items.put(item.getId(), item);
        }
    }
    
    public boolean addItem(Item item) {
        if (items.containsKey(item.getId())) {
            Item dupe = items.get(item.getId());
            
            if (dupe.isStackable()) {
                dupe.stack(item);
                return true;
            }
            
            return false;
        }
        
        items.put(item.getId(), item);
        
        return true;
    }
    
    @Override
    public void open(Player player) {
        Map<Integer, Object> menu = new HashMap<Integer, Object>();
        Map<Integer, Item> mapped = new HashMap<Integer, Item>();
        Map<Integer, String> mappedKey = new HashMap<Integer, String>();
        
        Map<String, Item> left = new HashMap<String, Item>();
        
        int i = 0;
        
        for (Entry<String, Item> entry : items.entrySet()) {
            Item item = entry.getValue();
            String key = entry.getKey();
            
            if (!item.isCarry()) {
                left.put(key, item);
                continue;
            }
            
            menu.put(i, item.getName());
            mapped.put(i, item);
            mappedKey.put(i, key);
            i++;
        }
        
        clear();
        Map<Integer, Boolean> checked = menuChecked(menu);
        
        for (Entry<Integer, Boolean> entry : checked.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            
            if (entry.getKey() == null) {
                continue;
            }
            
            if (mapped.get(entry.getKey()) == null) {
                continue;
            }
            
            if (entry.getValue() == false) {
                left.put(mappedKey.get(entry.getKey()), mapped.get(entry.getKey()));
                continue;
            }
            
            player.getInventory().addItem(mapped.get(entry.getKey()));
        }
        
        items.clear();
        items.putAll(left);
        
        clear();
    }
}
