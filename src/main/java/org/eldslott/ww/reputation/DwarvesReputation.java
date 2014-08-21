package org.eldslott.ww.reputation;

public class DwarvesReputation extends Reputation {
    private static final long serialVersionUID = 2966354158413261663L;
    
    public static String ID;

    public DwarvesReputation(Identity identity) {
        super(identity);
        DwarvesReputation.ID = identity.ID;
    }

    @Override
    public String getId() {
        return DwarvesReputation.ID;
    }
}
