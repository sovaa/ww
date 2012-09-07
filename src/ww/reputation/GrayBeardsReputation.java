package ww.reputation;

public class GrayBeardsReputation extends Reputation {
    private static final long serialVersionUID = -4635701670985169690L;
    
    public static String ID;

    public GrayBeardsReputation(Identity identity, Reputation parent) {
        super(identity, parent);
        GrayBeardsReputation.ID = identity.ID;
    }

    @Override
    public String getId() {
        return GrayBeardsReputation.ID;
    }
}
