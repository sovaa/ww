package ww.item;

import java.util.List;

import ww.player.Player;

public class HealthPotion extends Item {
    private static final long serialVersionUID = -4255850434215076613L;
    
    public static final String id = "health-potion";
    
    private static final int VALUE = 50;
    
    private String description;
    private int amount;
    private int heals;
    
    public HealthPotion() {
        super("Health potion", Item.Slot.none);
        
        description = "Restores some health.";
        amount = 1;
        heals = 40;
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
        return HealthPotion.id;
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
