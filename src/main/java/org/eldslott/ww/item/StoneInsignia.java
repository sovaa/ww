package org.eldslott.ww.item;

import java.util.ArrayList;
import java.util.List;

public class StoneInsignia extends Item {
    private static final long serialVersionUID = 1172020409945789512L;
    
    private String description;
    
    public StoneInsignia() {
        super("A worn stone insignia", Item.Slot.none);
        
        description = "It's a simple insignia made from stone, worn over the ages.";
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = new ArrayList<Item.Use>();
        
        uses.add(Use.none);
        
        return uses;
    }
    
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isStackable() {
        return false;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
