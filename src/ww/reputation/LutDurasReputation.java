package ww.reputation;

public class LutDurasReputation extends Reputation {
    private static final long serialVersionUID = -5294141977380492966L;
    
    public static String ID;

    public LutDurasReputation(Identity identity, Reputation parent) {
        super(identity, parent);
        LutDurasReputation.ID = identity.ID;
    }

    @Override
    public String getId() {
        return LutDurasReputation.ID;
    }
}
