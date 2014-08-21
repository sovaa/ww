package org.eldslott.ww.util;

import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import org.eldslott.ww.Game;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Output implements Serializable {
    private static final long serialVersionUID = -3601898163633070734L;
    
    private static final Logger LOG = new Logger(Output.class);

    public static EnumSet<ScreenCharacterStyle> set;

    public static Screen screen;
    public static Terminal terminal;
    public static GUIScreen guiScreen;
    
    private static final int TOP_ROW = 4;
    private static final int TOP_COLUMN = 0;
    
    public static boolean clearMutex = false;
    
    public static final class Clear {
        private int col;
        private int row;
        
        public Clear(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }
    }
    
    public static final void init() {
        try {
            Common.guiScreen = TerminalFacade.createGUIScreen();
            Common.guiScreen.getScreen().startScreen();
            //Common.lanternTerminal = new LanternTerminal(new CustomTerminalFactory());

            Common.set = EnumSet.noneOf(ScreenCharacterStyle.class);
                    /*EnumSet.allOf(ScreenCharacterStyle.class);
            set.remove(ScreenCharacterStyle.Underline);
            set.remove(ScreenCharacterStyle.Blinking);
            set.remove(ScreenCharacterStyle.Reverse);
            set.remove(ScreenCharacterStyle.Bold);*/

            Common.screen = Common.guiScreen.getScreen();
            Common.terminal = Common.guiScreen.getScreen().getTerminal();

        }
        catch (LanternaException e) {
            e.printStackTrace();
        }
    }
    
    public static final String join(Collection<String> strings) {
        return join(strings, ", ");
    }
    
    public static final String join(Collection<String> strings, String delimiter) {
        Iterator<String> iter = strings.iterator();
        StringBuilder joined = new StringBuilder();
        
        while (iter.hasNext()) {
            joined.append(iter.next());
            
            if (iter.hasNext()) {
                joined.append(delimiter);
            }
        }
        
        return joined.toString();
    }
    
    public static final void setCursorPosition(int col, int row) {
        screen.setCursorPosition(col, row);
    }
    
    public static final int getCol() {
        return screen.getCursorPosition().getColumn();
    }
    
    public static final int getRow() {
        return screen.getCursorPosition().getRow();
    }
    
    public static final int getTopRow() {
        return TOP_ROW;
    }
    
    public static final int getTopColumn() {
        return TOP_COLUMN;
    }
    
    public static final int getMaxPrintableRows() {
        return screen.getTerminalSize().getRows() - TOP_ROW - 1;
    }
    
    public static final int getMaxPrintableCols() {
        return screen.getTerminalSize().getColumns() - 2;
    }
    
    public static final void decreaseRow(int rows) {
        screen.setCursorPosition(getCol(), getRow() - rows);
    }
    
    public static final void increaseRow() {
        screen.setCursorPosition(getCol(), getRow() + 1);
    }
    
    public static final void decreaseColumn() {
        int col = getCol();
        
        if (col > 0) {
            col--;
        }
        
        screen.setCursorPosition(col, getRow());
    }
    
    public static final void increaseColumn() {
        increaseColumn(1);
    }
    
    public static final void increaseColumn(int i) {
        screen.setCursorPosition(getCol() + i, getRow());
    }
    
    public static final void setColumn(int i) {
        setCursorPosition(i, getRow());
    }
    
    public static final void printlnInverse(String s) {
        println(s, Color.BLACK, Color.WHITE);
    }
    
    public static final void printlnInverse(String s, Color foreground) {
        if (foreground == null || Color.WHITE.equals(foreground) || Color.DEFAULT.equals(foreground)) {
            println(s, Color.BLACK, Color.WHITE);
        }
        else {
            println(s, foreground, Color.WHITE);
        }
    }
    
    public static final void println(String s) {
        println(s, Color.WHITE, Color.DEFAULT);
    }
    
    public static final void println(String s, Color foreground) {
        println(s, foreground, Color.DEFAULT);
    }
    
    public static final void print(char c) {
        print(String.valueOf(c));
    }
    
    public static final void print(char c, Color foreground, Color background) {
        print(String.valueOf(c), foreground, background);
    }
    
    public static final void print(String s) {
        if (getRow() >= TOP_ROW - 1) {
            screen.putString(getCol(), getRow(), s, Color.WHITE.toTerminalColor(), Color.DEFAULT.toTerminalColor(), Common.set);
        }
        
        screen.setCursorPosition(getCol() + s.length(), getRow());
    }
    
    public static final void print(int col, int row, String s) {
        print(col, row, s, null, null);
    }
    
    public static final void print(int col, int row, String s, Color foreground, Color background) {
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        
        if (background == null) {
            background = Color.DEFAULT;
        }
        
        screen.putString(col, row, s, foreground.toTerminalColor(), background.toTerminalColor(), Common.set);
    }
    
    public static final void print(List<Clear> clears, String s) {
        print(clears, s, null, null);
    }
    
    public static final void print(List<Clear> clears, char c) {
        print(clears, String.valueOf(c), null, null);
    }
    
    public static final void print(List<Clear> clears, char c, Color foreground, Color background) {
        print(clears, String.valueOf(c), foreground, background);
    }

    public static final void print(List<Clear> clears, String s, Color foreground, Color background) {
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        
        if (background == null) {
            background = Color.DEFAULT;
        }
        
        int row = getRow();
        int col = getCol();
        
        for (int i = 0; i < s.length(); i++) {
            clears.add(new Clear(col + i, row));
        }

        if (getRow() >= TOP_ROW - 1) {
            screen.putString(getCol(), getRow(), s, foreground.toTerminalColor(), background.toTerminalColor(), Common.set);
        }
        
        screen.setCursorPosition(getCol() + s.length(), getRow());
    }
    
    public static final void print(String s, Color foreground) {
        print(s, foreground, null);
    }

    public static final void print(String s, Color foreground, Color background) {
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        
        if (background == null) {
            background = Color.DEFAULT;
        }

        if (getRow() >= TOP_ROW - 1) {
            screen.putString(getCol(), getRow(), s, foreground.toTerminalColor(), background.toTerminalColor(), Common.set);
        }
        
        screen.setCursorPosition(getCol() + s.length(), getRow());
    }

    public static final void println(List<Clear> clears, String s) {
        println(clears, s, null, null);
    }

    public static final void println(List<Clear> clears, String s, Color foreground, Color background) {
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        
        if (background == null) {
            background = Color.DEFAULT;
        }
        
        for (char c : s.toCharArray()) {
            if (c == '\n') {
                println();
                continue;
            }
            
            clears.add(new Clear(getCol(), getRow()));

            if (getRow() >= TOP_ROW - 1) {
                screen.putString(getCol(), getRow(), String.valueOf(c), foreground.toTerminalColor(), background.toTerminalColor(), Common.set);
            }
            
            increaseColumn();
        }
        
        println();
    }

    public static final void println(String s, Color foreground, Color background) {
        if (foreground == null) {
            foreground = Color.WHITE;
        }
        
        if (background == null) {
            background = Color.DEFAULT;
        }
        
        for (char c : s.toCharArray()) {
            if (c == '\n') {
                println();
                continue;
            }
            
            if (getRow() >= TOP_ROW - 1) {
                screen.putString(getCol(), getRow(), String.valueOf(c), foreground.toTerminalColor(), background.toTerminalColor(), Common.set);
            }
            
            increaseColumn();
        }
        
        println();
    }
    
    public static final void println() {
        int row = screen.getCursorPosition().getRow();
        
        screen.setCursorPosition(TOP_COLUMN, row + 1);
    }

    public static final void printbig(String s) {
        printbig(s, Color.WHITE);
    }
    
    public static final void printbig(String s, Color color) {
        println();
        println(s, color);
        println();
    }
    
    public static final void refresh() {
        refresh(false);
    }

    public static final void clearLine(int line) {
        int cols = screen.getTerminalSize().getColumns() - 1;
        while (--cols >= 0) {
            screen.putString(cols, line, " ", Color.WHITE.toTerminalColor(), Color.DEFAULT.toTerminalColor(), set);
        }
    }
    
    public static final void refresh(boolean mutex) {
        try {
            if (mutex) {
                while (Common.clearMutex) {
                    Thread.sleep(10);
                }
            }
            screen.refresh();
        }
        catch (LanternaException | InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    
    public static final void printsl(String s) {
        char[] str = s.toCharArray();
        
        int sleep = 20;
        
        for (int i = 0; i < str.length; i++) {
           try {
               Thread.sleep(sleep);
               print(String.valueOf(str[i]));
           } catch (InterruptedException e) {
               LOG.error(e.getMessage(), e);
           }
        }
        
        println();
    }
    
    public static final String getFormat(String str) {
        String output = " ";
        String[] words = str.split(" ");
        
        int limit = 60;
        int cur = 0;
        
        for (String word : words) {
            int newlines = 0;
            
            if (word.contains("\n")) {
                for (char c : word.toCharArray()) {
                    if (c == '\n') {
                        newlines++;
                    }
                }
                
                word = word.replace("\n", "");
            }
            
            int len = word.length() + 1;
            if (cur + len > limit) {
                output += "\n ";
                cur = 0;
            }
            
            if (word.contains("\n")) {
                output += "\n ";
                cur = 0;
            }
            
            output += word + " ";
            cur += len;
            
            if (newlines > 0) {
                do {
                    int spaces = limit - cur - 1;
                    
                    for (int i = 0; i < spaces; i++) {
                        output += " ";
                    }
                    
                    output += "\n ";
                    cur = 2;
                } while (--newlines > 0);
            }
        }

        output += "\n";
        
        return output;
    }

    public static final void format(String str) {
        format(str, Color.DEFAULT);
    }
    
    public static final void format(String str, Color color) {
        String[] words = str.split(" ");
        
        int limit = 60;
        int cur = 0;
        
        for (String word : words) {
            int len = word.length() + 1;
            if (cur + len > limit) {
                println();
                cur = 0;
            }
            
            if (word.contains("\n")) {
                cur = 0;
            }
            
            print(word + " ", color);
            cur += len;
        }

        println();
    }
    
    public static final void formatAnswer(String str, int pos) {
        String[] words = str.split(" ");
        
        int limit = 60;
        int cur = 0;
        
        for (String word : words) {
            int len = word.length() + 1;
            if (cur + len > limit) {
                println();
                
                if (pos > 9) {
                    print("     ");
                    cur += 5;
                }
                else {
                    print("    ");
                    cur += 4;
                }
                
                cur = 0;
            }
            
            if (word.contains("\n")) {
                cur = 0;
            }
            
            print(word + " ");
            cur += len;
        }

        println();
    }

    public static void prettyPrint(String str, int width) {
        prettyPrint(str, width, Color.DEFAULT);
    }
    
    public static void prettyPrint(String str, int width, Color color) {
        String[] words = str.split(" ");
        
        print(" | ");
        int cur = 2;
        
        for (String word : words) {
            int newlines = 0;
            
            if (word.contains("\n")) {
                for (char c : word.toCharArray()) {
                    if (c == '\n') {
                        newlines++;
                    }
                }
                
                word = word.replace("\n", "");
            }
            
            if (cur + word.length() + 2 > width) {
                int spaces = width - cur - 1;
                
                for (int i = 0; i < spaces; i++) {
                    print(" ");
                }
                
                print("|");
                println();
                cur = word.length() + 3;
                
                print(" | ");
                print(word, color);
                print(" ");
            }
            else {
                print(word, color);
                print(" ");
                cur += word.length() + 1;
            }
            
            if (newlines > 0) {
                do {
                    int spaces = width - cur - 1;
                    
                    for (int i = 0; i < spaces; i++) {
                        print(" ");
                    }
                    
                    print("|");
                    println();
                    print(" | ");
                    cur = 2;
                } while (--newlines > 0);
            }
        }

        int spaces = width - cur - 1;
        
        for (int i = 0; i < spaces; i++) {
            print(" ");
        }
        
        print("|");
        println();
    }
    
    public static final void notice(String s) {
        info(s, Color.YELLOW);
    }

    public static final void info(String s) {
        info(s, Color.DEFAULT);
    }
    
    public static final void info(String s, Color color) {
        int width = s.length() + 4;
        int limit = 50;
        
        if (width > limit) {
            width = limit;
        }
        
        print(" ,");
        for (int i = 0; i < width - 2; i++) {
            print("-");
        }
        print(".");
        
        println();
        
        prettyPrint(s, width, color);

        print(" `");
        for (int i = 0; i < width - 2; i++) {
            print("-");
        }
        print("´");
        
        println();
    }

    public static void clear() {
        StringBuilder builder = new StringBuilder();

        TerminalSize terminalSize = screen.getTerminalSize();
        
        for (int x = 0; x < terminalSize.getColumns(); x++) {
            builder.append(" ");
        }
        
        String eraser = builder.toString();
        
        for (int y = 0; y < terminalSize.getRows(); y++) {
            screen.putString(0, y, eraser, Color.WHITE.toTerminalColor(), Color.DEFAULT.toTerminalColor(), Common.set);
        }

        screen.setCursorPosition(TOP_COLUMN, TOP_ROW);
        
        if (Game.player != null && Game.player.getStatusBar() != null) {
            Game.player.getStatusBar().update();
        }
    }
}
