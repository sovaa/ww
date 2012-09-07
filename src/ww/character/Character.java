package ww.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ww.location.Place;
import ww.player.Inventory;
import ww.player.Player;
import ww.util.Common;

public abstract class Character extends Common implements Serializable {
    private static final long serialVersionUID = 7513450146568655565L;

    protected List<Conversation> intros = new ArrayList<Conversation>();
    protected Place place;
    
    protected String id;
    protected String name;
    protected String description;
    protected String updateInventory;
    
    protected Set<String> aliases = new HashSet<String>();
    protected Inventory inventory = new Inventory();
    
    protected boolean hostile = false;
    protected boolean discovered = false;
    protected boolean dead = false;
    protected boolean tradable = false;
    protected boolean gamble = false;
	
    public abstract void fight(Player player);
    public abstract void setPlace(Place place);
	
	public final void beginConversation(Player player) {
		if (!isHostile()) {
		    for (Conversation conversation : getConversations()) {
		        if (conversation.condition(this, player)) {
		            conversation.begin(this, player);
		            break;
		        }
		    }
			
			return;
		}
		
		fight(player);
	}
	
	protected final void setPlace(Place place, String id) {
        if (this.place != null) {
            this.place.removeCharacter(id);
        }
        
        this.place = place;
        
        place.addCharacter(id, this);
	}
    
    public List<Conversation> getConversations() {
        return intros;
    }
    
    public boolean isDiscovered() {
        return discovered;
    }
    
    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }
    
    public boolean isDead() {
        return dead;
    }
    
    public boolean isGamble() {
        return gamble;
    }
    
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    public Place getPlace() {
        return place;
    }
    
    public Set<String> getAliases() {
        return aliases;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isHostile() {
        return hostile;
    }
	
	public boolean isTradable() {
	    return tradable;
	}
	
    public void setAliases(Set<String> aliases) {
        this.aliases = aliases;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setUpdateInventory(String updateInventory) {
        this.updateInventory = updateInventory;
    }
    
    public String getUpdateInventory() {
        return updateInventory;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
