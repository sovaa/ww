package ww.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Commands {
	public static final String[] EXPLORE    = {"explore", "e"};
	public static final String[] LOOK       = {"look", "l", "ls"};
	public static final String[] GO         = {"go", "g"};
	public static final String[] VISIT      = {"visit", "v", "cd"};
	public static final String[] REST       = {"rest", "r"};
	public static final String[] TALK       = {"talk", "t"};
	public static final String[] STATUS     = {"status", "s"};
	public static final String[] SKILLS     = {"skills", "k"};
    public static final String[] QUESTS     = {"quests", "q"};
    public static final String[] HELP       = {"help", "h"};
    public static final String[] INVENTORY  = {"inventory", "i"};
    public static final String[] EXIT       = {"exit", "x"};
    public static final String[] DIE        = {"die"};
    public static final String[] LOOT       = {"loot"};
    public static final String[] MAP        = {"map", "m"};
    public static final String[] TRADE      = {"trade"};
    public static final String[] CHARACTER  = {"character", "c"};
    public static final String[] WALK       = {"walk", "w"};
    public static final String[] REPUTATION = {"reputation", "p"};
    public static final String[] KICK       = {"kick"};
    public static final String[] TAKE       = {"take"};
    public static final String[] OPEN       = {"open", "o"};
    
    public static final List<String> allCommands = new ArrayList<String>();
    
    private static class LengthComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {  
          if (o1.length() > o2.length()) {
             return 1;
          } else if (o1.length() < o2.length()) {
             return -1;
          } else { 
             return o1.compareTo(o2);
          }
        }
    }
	
	static {
		Set<String> unique = new HashSet<String>();

		Class<?> commands = Commands.class;
		Object obj = null;
		
		try {
			obj = commands.newInstance();
		}
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		for (int i = 0; i < commands.getFields().length; i++) {  
			Field field = commands.getFields()[i];
			
			if (!field.getType().isArray()) {
				continue;
			}
			
			Object[] object = null;
				
			try {
				object = (Object[])field.get(obj);
			}
			catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			
			Set<String> printCommand = new HashSet<String>();
			for (int j = 0; j < object.length; j++) {
				Object value = object[j];
				
				if (value == null || !value.getClass().equals(String.class)) {
					break;
				}
				
				String s = object[j].toString(); 
				
				if (unique.contains(s)) {
					throw new RuntimeException("command '" + s + "' already exists. Check Commands class for duplicate commands");
				}
				
				unique.add(s);
				printCommand.add(s);
			}
			
			allCommands.add(join(printCommand));
		}  
	}
	
	private static final String join(Set<String> set) {
	    String[] array = set.toArray(new String[0]);
	    List<String> list = new ArrayList<String>(Arrays.asList(array));
	    Collections.sort(list, new LengthComparator());
	    
	    Iterator<String> iter = list.iterator();
	    String join = "";
	    
	    while (iter.hasNext()) {
	        join += iter.next();
	        
	        if (iter.hasNext()) {
	            join += ", ";
	        }
	    }
	    
	    return join;
	}
	
	public static final void init() {
		// do nothing, only triggers the static initialization
	}
    
    public static final boolean begins(String what, String[] commands) {
        if (what == null || what.length() == 0) {
            return false;
        }
        
        if (commands == null || commands.length == 0) {
            return false;
        }
        
        for (String command : commands) {
            if (command.equalsIgnoreCase(what)) {
                return true;
            }
            
            String[] splits = what.split(" ");
            
            if (splits != null && splits.length > 0 && command.equalsIgnoreCase(splits[0])) {
                return true;
            }
        }
        
        return false;
    }
	
	public static final boolean is(String what, String[] commands) {
		if (what == null || what.length() == 0) {
			return false;
		}
		
		if (commands == null || commands.length == 0) {
			return false;
		}
		
		for (String command : commands) {
			if (command.equalsIgnoreCase(what)) {
				return true;
			}
		}
		
		return false;
	}
}
