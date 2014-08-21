package org.eldslott.ww.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.gui.dialog.DialogButtons;
import com.googlecode.lanterna.gui.dialog.DialogResult;
import com.googlecode.lanterna.gui.dialog.MessageBox;

public class Common extends Input {
    private static final long serialVersionUID = -856287230406025561L;
    
    private static final Logger LOG = new Logger(Common.class);
    
    /**
     * Remove "action " from "action <target>", so we only get "<target>" in "what".
     * 
     * @param what String to strip
     * @return the stripped String
     */
    public static final String stripWhat(String what) {
        String charKey = null;
        if (what.split(" ").length > 1) {
            charKey = what.substring(what.split(" ")[0].length()).trim();
            
            if (charKey.startsWith("to ")) {
                charKey = charKey.substring(charKey.split(" ")[0].length()).trim();
            }
        }
        
        return charKey;
    }
    
    public static final boolean isBlank(String string) {
        if (string == null) {
            return true;
        }
        
        if (string.length() == 0) {
            return true;
        }
        
        if (string.trim().length() == 0) {
            return true;
        }
        
        return false;
    }
    
    public static final int menu(Map<Integer, Object> objects) {
        return menu(objects, false);
    }

    public static final int menu(Map<Integer, Object> objects, boolean center) {
        Key choice;
        int current = 0;
        
        Map<Integer, Integer> clearCache = new HashMap<Integer, Integer>();
        
        int col = getCol();
        int row = getRow();
        
        Map<Integer, Integer> centerSpaces = new HashMap<Integer, Integer>();
        
        for (Entry<Integer, Object> entry : objects.entrySet()) {
            centerSpaces.put(entry.getKey(), entry.getValue().toString().length());
        }
        
        while (true) {
            setCursorPosition(col, row);
            
            for (int j = 0; j < objects.size(); j++) {
                if (!clearCache.containsKey(j)) {
                    increaseRow();
                    continue;
                }
                
                int clears = clearCache.get(j);
                
                int centerSpace = 0;
                
                if (center) {
                    centerSpace += getMaxPrintableCols() / 2;
                    centerSpace -= centerSpaces.get(j) / 2;
                }
                
                for (int i = 0; i < clears + centerSpace; i++) {
                    print(" ");
                }
                
                println();
            }
            
            setCursorPosition(col, row);
            
            int currentRow = 0;
            int currentColumn = 0;
            
            for (int j = 0; j < objects.size(); j++) {
                if (!clearCache.containsKey(j)) {
                    int clears = 2 + objects.get(j).toString().length();
                    clearCache.put(j, clears);
                }
                
                int centerSpace = 0;
                
                if (center) {
                    centerSpace += getMaxPrintableCols() / 2;
                    centerSpace -= centerSpaces.get(j) / 2;
                }
                
                if (j == current) {
                    currentRow = getRow();
                    currentColumn = getCol() + centerSpace + 1;
                    
                    print("  ");
                    
                    for (int i = 0; i < centerSpace; i++) {
                        print(" ");
                    }
                    
                    printlnInverse(objects.get(j).toString());
                }
                else {
                    print("  ");
                    
                    for (int i = 0; i < centerSpace; i++) {
                        print(" ");
                    }
                    
                    println(objects.get(j).toString());
                }
            }
            
            println();
            
            setCursorPosition(currentColumn, currentRow);
            refresh();
            
            while (true) {
                choice = getKey();
                
                if (choice == null) {
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.ArrowUp) && current > 0) {
                    current--;
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.ArrowDown) && current < objects.size() - 1) {
                    current++;
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.Enter)) {
                    return current;
                }
            }
        }
    }

    public static final int boxMenu(Map<Integer, String> objects) {
        List<Clear> clears = new ArrayList<Clear>();
        
        int choice = boxMenuInternal(clears, objects);
        
        for (Clear clear : clears) {
            print(clear.getCol(), clear.getRow(), " ");
        }
        
        return choice;
    }
    
    private static final int boxMenuInternal(List<Clear> clears, Map<Integer, String> objects) {
        long start = System.currentTimeMillis();
        
        int left = 15;
        int maxwidth = 0;
        int mincol = left + 5;
        int textcol = left + 8;
        
        Color bb = Color.MAGENTA;
        Color bf = Color.WHITE;
        
        char bh = '-';
        char bv = '|';
        char br = '\'';
        char bl = '\'';
        char bt = '-';
        char bu = '.';
        
        setCursorPosition(getTopColumn(), getRow());
        
        for (int i = 0; i < objects.size(); i++) {
            int length = objects.get(i).length();
            
            if (length > maxwidth) {
                maxwidth = length;
            }
        }
        
        maxwidth += 3;

        // top border
        {
            setColumn(mincol);
            
            print(clears, bu, bf, bb);
            
            for (int i = 0; i < maxwidth; i++) {
                print(clears, bh, bf, bb);
            }
            
            print(clears, bu, bf, bb);
            
            println();
            increaseColumn(mincol);
        }
        
        int row = getRow();
        int col = getCol() + 1;
        
        // border sides
        for (int i = 0; i < objects.size(); i++) {
            setCursorPosition(mincol, row + i);
            print(clears, bv, bf, bb);
            setCursorPosition(mincol + maxwidth + 1, row + i);
            print(clears, bv, bf, bb);
        }

        // bottom border
        {
            increaseRow();
            setColumn(mincol);
            print(clears, bl, bf, bb);
            
            for (int i = 0; i < maxwidth; i++) {
                print(clears, bt, bf, bb);
            }

            print(clears, br, bf, bb);
        }

        int current = 0;
        
        String clear = "";
        
        // calculate clear string
        {
            StringBuilder builder = new StringBuilder();
            
            for (int i = 0; i < maxwidth; i++) {
                builder.append(" ");
            }
            
            clear = builder.toString();
        }
        
        LOG.info("pre-configured box menu: " + (System.currentTimeMillis() - start) + "ms");
        
        while (true) {
            setCursorPosition(col, row);
            
            // clear
            for (int j = 0; j < objects.size(); j++) {
                println(clear);
                setColumn(col);
            }
            
            setCursorPosition(textcol, row);
            
            int cr = 0;
            int cc = 0;
            
            for (int i = 0; i < objects.size(); i++) {
                setColumn(textcol);
                
                if (i == current) {
                    cr = getRow();
                    cc = getCol() - 1;
                    println(clears, objects.get(i), Color.BLACK, Color.WHITE);
                }
                else {
                    println(clears, objects.get(i));
                }
            }
            
            setCursorPosition(cc, cr);
            refresh();
            
            Key choice = null;
            
            while (true) {
                choice = getKey();
                
                if (choice == null) {
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.Enter)) {
                    return current;
                }
                
                if (choice.getKind().equals(Key.Kind.ArrowUp) && current > 0) {
                    current--;
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.ArrowDown) && current < objects.size() - 1) {
                    current++;
                    break;
                }
            }
        }
    }

    public static final Map<Integer, Boolean> menuChecked(Map<Integer, Object> objects) {
        return menuChecked(objects, null);
    }
    
    public static final Map<Integer, Boolean> menuChecked(Map<Integer, Object> objects, Map<Integer, Boolean> checked) {
        if (checked == null) {
            checked = new HashMap<Integer, Boolean>();
            
            for (Entry<Integer, Object> entry : objects.entrySet()) {
                checked.put(entry.getKey(), false);
            }
        }

        objects.put(-2, "Return");
        objects.put(-1, "Take all");
        println();
        
        Key choice;
        int current = -2;
        
        Map<Integer, Integer> clearCache = new HashMap<Integer, Integer>();
        
        int col = getCol();
        int row = getRow();
        
        while (true) {
            setCursorPosition(col, row);
            
            for (int j = 0; j < objects.size(); j++) {
                if (!clearCache.containsKey(j)) {
                    increaseRow();
                    continue;
                }
                
                int clears = clearCache.get(j);
                
                for (int i = 0; i < clears; i++) {
                    print(" ");
                }
                
                println();
            }
            
            setCursorPosition(col, row);
            
            int currentRow = 0;
            int currentColumn = 0;
            
            for (int j = -2; j < objects.size() - 2; j++) {
                if (!clearCache.containsKey(j)) {
                    int clears = 8 + objects.get(j).toString().length();
                    clearCache.put(j, clears);
                }

                print("  ");
                
                if (j >= 0) {
                    if (checked.get(j)) {
                        print(" [X]  ");
                    }
                    else {
                        print(" [ ]  ");
                    }
                }
                else {
                    print("      ");
                }
                
                if (j == current) {
                    currentRow = getRow();
                    currentColumn = getCol() - 1;
                    printlnInverse(objects.get(j).toString());
                }
                else {
                    println(objects.get(j).toString());
                }
            }
            
            println();
            setCursorPosition(currentColumn, currentRow);
            refresh();
            
            while (true) {
                choice = getKey();
                
                if (choice == null) {
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.ArrowUp) && current > -2) {
                    current--;
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.ArrowDown) && current < objects.size() - 3) {
                    current++;
                    break;
                }
                
                if (choice.getKind().equals(Key.Kind.Enter)) {
                    if (current == -2) {
                        return checked;
                    }
                    
                    if (current == -1) {
                        for (int i = 0; i < checked.size(); i++) {
                            checked.put(i, true);
                        }
                        
                        return checked;
                    }
                    
                    checked.put(current, !checked.get(current));
                    break;
                }
            }
        }
    }

    public static final boolean equals(String e, String q) {
        boolean be = isBlank(e);
        boolean bq = isBlank(q);
        
        if (be && bq) {
            return true;
        }
        
        if (be || bq) {
            return false;
        }
        
        if (e.equals(q)) {
            return true;
        }
        
        return false;
    }
    
    public static final boolean endsIn(String what, String end) {
        if (what == null || what.trim().length() == 0) {
            return false;
        }
        
        if (end == null || end.trim().length() == 0) {
            return false;
        }
        
        return what.endsWith(end);
    }
    
    public static final DialogResult dialogue(String title, String question, DialogButtons type) {
        DialogResult box;
        
        try {
            box = MessageBox.showMessageBox(guiScreen, title, question, type);
            refresh();
        }
        catch (LanternaException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
        
        return box;
    }
}
