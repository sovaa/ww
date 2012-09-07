package ww.attribute;

public class Strength extends Attribute {
    private static final long serialVersionUID = -1093849903006303888L;
    private static final String id = "strength";
    
    public Strength(int value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return "Stength";
    }

    @Override
    public String getId() {
        return id;
    }
}
