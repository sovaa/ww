package org.eldslott.ww.item;

import java.util.List;

public class GenericFeet extends Item {
    private static final long serialVersionUID = 5650936507701147264L;
    
    public GenericFeet(String name) {
        super(name, Item.Slot.feet);
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = super.getUses();
        
        uses.add(Use.equip);
        
        return uses;
    }
}
