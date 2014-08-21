package org.eldslott.ww.item;

import java.util.List;

public class GenericChest extends Item {
    private static final long serialVersionUID = -6214570195100446513L;
    
    public GenericChest(String name) {
        super(name, Item.Slot.chest);
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = super.getUses();
        
        uses.add(Use.equip);
        
        return uses;
    }
}
