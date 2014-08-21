package org.eldslott.ww.item;

import java.util.List;

import org.eldslott.ww.player.Player;

public class Cheese extends Item {
    private static final long serialVersionUID = -2370214641720516975L;
    
    public static final String id = "cheese";
    
    private static final int VALUE = 2;
    
    private String description;
    private int amount;
    private int heals;
    
    public Cheese() {
        super("Cheese", Item.Slot.none);
        
        description = "Restores some health.";
        amount = 1;
        heals = 10;
    }
    
    @Override
    public List<Use> getUses() {
        List<Use> uses = super.getUses();
        
        uses.add(Use.use);
        
        return uses;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public boolean use(Player player) {
        if (amount == 0) {
            return false;
        }
        
        player.restoreHealth(heals);
        
        amount--;
        
        if (amount == 0) {
            player.getInventory().removeItem(this);
        }
        
        return false;
    }
    
    @Override
    public void stack(Item item) {
        if (!(item instanceof HealthPotion)) {
            return;
        }
        
        amount++;
    }

    @Override
    public boolean isStackable() {
        return true;
    }

    @Override
    public String getId() {
        return Cheese.id;
    }

    @Override
    public int getValue() {
        return VALUE * amount;
    }
    
    @Override
    public int getAmount() {
        return amount;
    }
}
