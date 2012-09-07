package ww.reputation;

public class EasternCivilizationsReputation extends Reputation {
    private static final long serialVersionUID = 3829069977486344515L;
    
    public static String ID;

    public EasternCivilizationsReputation(Identity identity, Reputation parent) {
        super(identity, parent);
        EasternCivilizationsReputation.ID = identity.ID;
    }

    @Override
    public String getId() {
        return EasternCivilizationsReputation.ID;
    }
}
