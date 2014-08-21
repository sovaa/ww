package org.eldslott.ww.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eldslott.ww.util.Color;
import org.eldslott.ww.attribute.Attribute;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public abstract class Item extends Common {
    private static final long serialVersionUID = -7893293921607067046L;
    
    protected Map<String, Attribute> bonuses = new HashMap<String, Attribute>();
    protected List<Item> combines = new ArrayList<Item>();
    
    private String id;
    private Slot slot;
    protected String name;
    
    private int amount = 0; // number of stackable items
    private int value = 0;
    private int weight = 0;
    private boolean stackable = false;
    private boolean carry = true;
    private boolean openable = false;
    private boolean locked = false;
    private Color color = Color.DEFAULT;
    
    public static enum Slot implements Serializable {
        none("None"), // non-equipable
        shoulders("Shoulders"),
        head("Head"),
        back("Back"),
        feet("Feet"),
        hands("Hands"),
        finger("Finger"),
        neck("Neck"),
        belt("Belt"),
        chest("Chest"),
        legs("Legs"),
        mainHand("Main-hand"),
        offHand("Off-hand");
        
        private String name;
        
        private Slot(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    };
    
    public static enum Use implements Serializable {
        none("None"),
        use("Use"),
        equip("Equip"),
        drop("Drop"),
        combine("Combine"),
        trade("Trade");
        
        private String name;
        
        private Use(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    };
    
    public Item() {
        this.id = UUID.randomUUID().toString();
    }
    
    public Item(String name, Slot slot) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.slot = slot;
    }
    
    public Item(String name, Slot slot, boolean generateId) {
        if (generateId) {
            this.id = UUID.randomUUID().toString();
        }
        
        this.name = name;
        this.slot = slot;
    }
    
    public List<Attribute> getBonuses() {
        return new ArrayList<Attribute>(bonuses.values());
    }
    
    public void addCombine(Item item) {
        combines.add(item);
    }
    
    public List<Item> getCombines() {
        return combines;
    }

    public void setCombines(List<Item> combines) {
        this.combines = combines;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getNameWithBonus() {
        if (bonuses.size() == 0) {
            return name;
        }
        
        String bonusName = name + " (";
        
        Iterator<Attribute> iterator = bonuses.values().iterator();
        
        while (iterator.hasNext()) {
            Attribute attribute = iterator.next();
            
            bonusName += attribute.getName() + ": " + attribute.getValue();
            
            if (iterator.hasNext()) {
                bonusName += ", ";
            }
            else {
                bonusName += ")";
            }
        }
        
        return bonusName;
    }
    
    public List<Use> getUses() {
        List<Use> uses = new ArrayList<Item.Use>();
        
        uses.add(Use.drop);
        uses.add(Use.trade);
        uses.add(Use.combine);
        
        return uses;
    }
    
    public Slot getSlot() {
        return slot;
    }
    
    public void setSlot(Slot slot) {
        this.slot = slot;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    
    public String getDescription() {
        return getName();
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isStackable() {
        return stackable;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }
    
    public void stack(Item item) {
        return;
    }
    
    public boolean use(Player player) {
        return false;
    }
    
    public boolean useOn(Player player, Item item) {
        clear();
        printbig("You cannot use that on this item!");
        pause();
        
        return false;
    }
    
    public boolean isUsable() {
        return false;
    }
    
    public void open(Player player) {
        clear();
        printbig("  You open " + name + ".");
        pause();
    }
    
    public boolean isOpenable() {
        return openable;
    }

    public void setOpenable(boolean openable) {
        this.openable = openable;
    }
    
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isCarry() {
        return carry;
    }

    public void setCarry(boolean carry) {
        this.carry = carry;
    }

    public void addBonus(Attribute bonus) {
        if (bonuses.containsKey(bonus.getId())) {
            Attribute currentBonus = bonuses.get(bonus.getId());
            currentBonus.addValue(bonus.getValue());
            return;
        }
        
        bonuses.put(bonus.getId(), bonus);
    }
}
