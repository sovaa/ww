package ww.quests;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ww.quests.bwlv.BeatUpMarekDravis;
import ww.quests.bwlv.RatInfestation;

public abstract class Quest implements Serializable {
    private static final long serialVersionUID = 6231139406245885647L;

    private String state = "UNKNOWN";
    
    public static final String BUCK_BEAT_UP_MAREK_DRAVIS = "buck-beat-up-marek-dravis";
    public static final String RAT_INFESTATION = "rat-infestation";
    
    private static Map<String, Quest> quests;
    
    private boolean completed = false;
    private boolean rewarded = false;
    
    private String id = "";
    private String name = "";
    private String description = "";
    
    private List<String> updates = new ArrayList<String>();
    
    static {
    	quests = new HashMap<String, Quest>();

    	quests.put(Quest.BUCK_BEAT_UP_MAREK_DRAVIS, new BeatUpMarekDravis());
    	quests.put(Quest.RAT_INFESTATION, new RatInfestation());
    }
    
    public void complete() {
        // nothing
    }
    
    public void update(String update) {
        this.updates.add(update);
    }
    
    public static final Quest getQuest(String key) {
    	return quests.get(key);
    }
    
    public List<String> getUpdates() {
        return updates;
    }
    
    public boolean isCompleted() {
    	return completed;
    }
    
    public void setCompleted(boolean completed) {
    	this.completed = completed;
    }
    
    public String getId() {
    	return id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }

    public boolean isRewarded() {
        return rewarded;
    }

    public void setRewarded(boolean rewarded) {
        this.rewarded = rewarded;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
