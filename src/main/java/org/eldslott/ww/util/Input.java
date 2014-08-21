package org.eldslott.ww.util;

import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;

public class Input extends Output {
    private static final long serialVersionUID = -6208825457332840812L;
    
    private static final Logger LOG = new Logger(Input.class);
    
    public static final String ESCAPE = "escape-button-pressed";
    
    public static Key getKey() {
        Common.clearMutex = false;
        
        Key key = null;
        
        while (key == null) {
            try {
                key = terminal.readInput();
            }
            catch (LanternaException e) {
                e.printStackTrace();
                break;
            }
            
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        
        Common.clearMutex = true;
        
        return key;
    }
    
    public static String input() {
        return input(false);
    }

    public static void pause() {
        print("  <Press enter to continue...>");
        
        Common.clearMutex = false;
        
        try {
            refresh();
            
            Key key = null;
            while (key == null) {
                key = terminal.readInput();
                
                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        catch (LanternaException e) {
            LOG.error(e.getMessage(), e);
        }
        
        Common.clearMutex = true;
    }
    
    public static String input(boolean acceptEmpty) {
        Common.clearMutex = false;
        
        print("> ");
        refresh();
        
        String input = "";
        
        try {
            while (true) {
                Key key = null;
                
                while (key == null) {
                    key = terminal.readInput();
                    
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                if (key.getKind().equals(Kind.Escape)) {
                    return ESCAPE;
                }
                
                if (key.getCharacter() == '\n') {
                    if (input.length() > 0 || acceptEmpty) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                
                if (key.getKind() != null && key.getKind().equals(Key.Kind.Backspace)) {
                    if (input.length() > 0) {
                        input = backspace(input);
                        refresh();
                    }
                    
                    continue;
                }
                
                print(String.valueOf(key.getCharacter()));
                refresh();
                
                input += String.valueOf(key.getCharacter());
            }
        }
        catch (LanternaException e) {
            e.printStackTrace();
        }
        finally {
            Common.clearMutex = true;
        }
        
        return input;
    }
    
    private static final String backspace(String s) {
        decreaseColumn();
        print(" ");
        decreaseColumn();
        
        if (s.length() < 2) {
            return "";
        }
        
        return s.substring(0, s.length() - 1);
    }    
}
