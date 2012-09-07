package ww.item;

import java.util.ArrayList;
import java.util.List;

public class BlackWaterJailDungeonKey extends Item {
    private static final long serialVersionUID = -2751807476045180172L;
    
    public static final String id = "black-water-jail-dungeon-key";
    
    public BlackWaterJailDungeonKey() {
        setName("Key to the Black Water Jail Dungeon");
    }
    
    @Override
    public List<Use> getUses() {
        return new ArrayList<Item.Use>();
    }
    
    @Override
    public String getId() {
        return id;
    }
}
