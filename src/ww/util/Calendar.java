package ww.util;

import java.io.Serializable;

public class Calendar implements Serializable {
    private static final long serialVersionUID = 2308597055482721451L;
    
    private int limit;
    private int value;
    
    public Calendar(int limit) {
    	this.limit = limit;
    	value = 0;
    }
    
    public Calendar copy() {
        Calendar cal = new Calendar(limit);
        cal.setValue(value);
        
        return cal;
    }
    
    public void increment() {
    	value = (value + 1) % limit;
    }
    
    public String getDisplayValue() {
    	
    	if(value < 10) {
    		return "0" + value;
    	}
    	else {
    		return "" + value;
    	}
    }
    
    public int getValue() {
    	return value;
    }
    
    public void setValue(int value) {
    	this.value = value;
    }

}
