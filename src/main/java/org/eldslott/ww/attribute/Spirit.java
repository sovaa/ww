package org.eldslott.ww.attribute;

public class Spirit extends Attribute {
    private static final long serialVersionUID = 5292895107999718548L;
    private static final String id = "spirit";
    
    public Spirit(int value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return "Spirit";
    }

    @Override
    public String getId() {
        return id;
    }
}
