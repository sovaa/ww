package org.eldslott.ww.reputation;

public class LastOutpostReputation extends Reputation {
    private static final long serialVersionUID = -7819247720526065491L;
    
    public static String ID;

    public LastOutpostReputation(Identity identity, Reputation parent) {
        super(identity, parent);
        LastOutpostReputation.ID = identity.ID;
    }

    @Override
    public String getId() {
        return LastOutpostReputation.ID;
    }
}
