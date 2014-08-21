package org.eldslott.ww.attribute;

public class Intellect extends Attribute {
    private static final long serialVersionUID = 5763808698969345134L;
    private static final String id = "intellect";
    
    public Intellect(int value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return "Intellect";
    }

    @Override
    public String getId() {
        return id;
    }
}
