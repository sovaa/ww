package org.eldslott.ww.item;


public class Gold extends Item {
    private static final long serialVersionUID = -5341614073147717519L;
    
    public static final String id = "gold";
    
    public Gold(int value) {
        super("Gold", Item.Slot.none, false);
        setValue(value);
    }
    
    @Override
    public void stack(Item item) {
        if (!(item instanceof Gold)) {
            return;
        }
        
        Gold gold = (Gold)item;
        
        setValue(getValue() + gold.getValue());
    }

    @Override
    public String getId() {
        return Gold.id;
    }

    @Override
    public boolean isStackable() {
        return true;
    }

    @Override
    public int getAmount() {
        return getValue();
    }
    
    @Override
    public void setAmount(int amount) {
        setValue(amount);
    }
}
