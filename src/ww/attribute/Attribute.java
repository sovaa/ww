package ww.attribute;

import java.io.Serializable;

public abstract class Attribute implements Serializable {
    private static final long serialVersionUID = 9182480487663063680L;
    
    protected Integer value;
    
    public abstract String getName();
    public abstract String getId();
    
    public Integer getValue() {
        return value;
    }
    
    public void setValue(Integer value) {
        this.value = value;
    }
    
    public void addValue(Integer value) {
        if (this.value == null) {
            this.value = 0;
        }
        
        this.value += value;
    }
}
