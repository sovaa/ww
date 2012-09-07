package ww.item;

import java.util.List;

public class GenericLegs extends Item {
    private static final long serialVersionUID = 3746128081876876941L;
    
    public GenericLegs(String name) {
        super(name, Item.Slot.legs);
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = super.getUses();
        
        uses.add(Use.equip);
        
        return uses;
    }
}
