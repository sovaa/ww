package ww.attribute;

public class Dexterity extends Attribute {
    private static final long serialVersionUID = 6588382689522025875L;
    private static final String id = "dexterity";
    
    public Dexterity(int value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return "Dexterity";
    }

    @Override
    public String getId() {
        return id;
    }
}
