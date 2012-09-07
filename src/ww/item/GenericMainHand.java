package ww.item;

import java.util.List;

public class GenericMainHand extends Item {
    private static final long serialVersionUID = 6576330830404472081L;

    public GenericMainHand(String name) {
        super(name, Item.Slot.mainHand);
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = super.getUses();
        
        uses.add(Use.equip);
        
        return uses;
    }
}
