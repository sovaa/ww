package org.eldslott.ww.item;

import java.util.List;

public class GenericHands extends Item {
    private static final long serialVersionUID = 7553167142435091715L;
    
    public GenericHands(String name) {
        super(name, Item.Slot.hands);
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = super.getUses();
        
        uses.add(Use.equip);
        
        return uses;
    }
}
