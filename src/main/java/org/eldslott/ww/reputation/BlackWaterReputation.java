package org.eldslott.ww.reputation;

public class BlackWaterReputation extends Reputation {
    private static final long serialVersionUID = 4464478612245631801L;
    
    public static String ID;

    public BlackWaterReputation(Reputation.Identity identity, Reputation parent) {
        super(identity, parent);
        BlackWaterReputation.ID = identity.ID;
    }

    @Override
    public String getId() {
        return BlackWaterReputation.ID;
    }
}
