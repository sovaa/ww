package ww.item;

import java.util.List;

public class GenericHead extends Item {
    private static final long serialVersionUID = -6214570195100446513L;

    public GenericHead(String name) {
        super(name, Item.Slot.head);
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = super.getUses();
        
        uses.add(Use.equip);
        
        return uses;
    }
}
