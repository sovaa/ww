package ww.reputation;

public class NorthernHumanSettlementsReputation extends Reputation {
    private static final long serialVersionUID = -6362495426454194785L;
    
    public static String ID;

    public NorthernHumanSettlementsReputation(Reputation.Identity identity, Reputation parent) {
        super(identity, parent);
        NorthernHumanSettlementsReputation.ID = identity.ID;
    }

    @Override
    public String getId() {
        return NorthernHumanSettlementsReputation.ID;
    }
}
