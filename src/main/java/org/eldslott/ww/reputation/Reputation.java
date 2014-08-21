package org.eldslott.ww.reputation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.eldslott.ww.util.Logger;

public abstract class Reputation implements Serializable {
    private static final long serialVersionUID = 206202228482863616L;
    
    private static final Logger LOG = new Logger(Reputation.class);

    public static Map<String, Reputation> allReputations = new HashMap<String, Reputation>();
    public static Map<String, Reputation> rootReputations = new HashMap<String, Reputation>();
    
    private Identity enumIdentity;
    private String identity;
    private String name;
    private boolean discovered;
    private int value;
    
    private Reputation parent;
    private Map<String, Reputation> children;
    
    public abstract String getId();
    
    public static enum Identity {
        HUMAN("human", "Humans"),
        NORTHERN_HUMAN_SETTELMENTS("nothern-human-settlements", "Nothern Human Settlements"),
        LAST_OUTPOST("last-outpost", "Last Outpost"),
        BLACK_WATER("black-water-lodge-village", "Black Water Lodge Village"),
        EASTERN_CIVILIZATIONS("eastern-civilizations", "Eastern Civilizations"),
        LUT_DURAS("lut-duras", "Lut Duras"),
        DWARVES("dwarves", "Dwarves"),
        GRAY_BEARDS("gray-beards", "Gray Beards");
        
        public String ID;
        public String NAME;
        
        private Identity(String id, String name) {
            this.ID = id;
            this.NAME = name;
        }
    }
    
    @SuppressWarnings("unused")
    public static void init() {
        LOG.info("initing reputations");
        Reputation human = new HumanReputation(Identity.HUMAN);
        Reputation nothern = new NorthernHumanSettlementsReputation(Identity.NORTHERN_HUMAN_SETTELMENTS, human);
        Reputation outpost = new LastOutpostReputation(Identity.LAST_OUTPOST, nothern);
        Reputation blackWater = new BlackWaterReputation(Identity.BLACK_WATER, nothern);
        
        Reputation eastern = new EasternCivilizationsReputation(Identity.EASTERN_CIVILIZATIONS, human);
        Reputation lutduras = new LutDurasReputation(Identity.LUT_DURAS, eastern);
        
        Reputation dwarves = new DwarvesReputation(Identity.DWARVES);
        Reputation gray = new GrayBeardsReputation(Identity.GRAY_BEARDS, dwarves);
    }

    public Reputation(Identity enumIdentity) {
        this.identity = enumIdentity.ID;
        this.enumIdentity = enumIdentity;
        this.name = enumIdentity.NAME; 
        this.value = 0;
        this.parent = null;
        this.children = new HashMap<String, Reputation>();
        
        Reputation.addReputation(this);
    }
    
    public Reputation(Identity enumIdentity, Reputation parent) {
        this.identity = enumIdentity.ID;
        this.enumIdentity = enumIdentity;
        this.name = enumIdentity.NAME; 
        this.value = 0;
        this.parent = parent;
        this.children = new HashMap<String, Reputation>();
        
        parent.addChild(this);
        
        Reputation.addReputation(this);
    }
    
    public static void addReputation(Reputation reputation) {
        if (Reputation.allReputations == null) {
            Reputation.allReputations = new HashMap<String, Reputation>();
        }
        
        if (reputation.getParent() == null) {
            if (Reputation.rootReputations == null) {
                Reputation.rootReputations = new HashMap<String, Reputation>();
            }
            
            Reputation.rootReputations.put(reputation.getIdentity(), reputation);
        }
        
        Reputation.allReputations.put(reputation.getIdentity(), reputation);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((identity == null) ? 0 : identity.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reputation other = (Reputation) obj;
        if (identity == null) {
            if (other.identity != null)
                return false;
        }
        else if (!identity.equals(other.identity))
            return false;
        return true;
    }

    public void addChild(Reputation child) {
        if (this.children == null) {
            this.children = new HashMap<String, Reputation>();
        }
        
        this.children.put(child.getIdentity(), child);
    }
    
    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public String getIdentity() {
        return identity;
    }

    public void increase() {
        value++;
    }
    
    public void increase(int value) {
        this.value += value;
    }
    
    public Identity getEnumIdentity() {
        return enumIdentity;
    }

    public void setEnumIdentity(Identity enumIdentity) {
        this.enumIdentity = enumIdentity;
    }

    public void decrease() {
        value--;
    }
    
    public void decrease(int value) {
        this.value -= value;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Reputation getParent() {
        return parent;
    }
    
    public void setParent(Reputation parent) {
        this.parent = parent;
    }

    public Map<String, Reputation> getChildren() {
        return children;
    }

    public void setChildren(Map<String, Reputation> children) {
        this.children = children;
    }
}
