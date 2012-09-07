package ww.reputation;

public class HumanReputation extends Reputation {
    private static final long serialVersionUID = 3908805005148229222L;
    
    public static String ID;

    public HumanReputation(Reputation.Identity identity) {
        super(identity);
        HumanReputation.ID = identity.ID;
    }

    @Override
    public String getId() {
        return HumanReputation.ID;
    }
}
