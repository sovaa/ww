package org.eldslott.ww.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.lantern.gui.dialog.DialogButtons;
import org.lantern.gui.dialog.DialogResult;
import org.lantern.terminal.Terminal.Color;

import org.eldslott.ww.Game;
import org.eldslott.ww.action.HelpAction;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.player.Regen;

public class MainMenu extends Common {
    private static final long serialVersionUID = -6757910191244954796L;
    
    private static final String EMPTY_SAVE = "<Empty Save Slot>";
    
    public static final boolean show(Player player) {
        Map<Integer, Object> choices = new HashMap<Integer, Object>();
        
        String[] cs = {
                "Return",
                "Save",
                "Load",
                "Options",
                "Help",
                "Exit"
        };
        
        for (int i = 0; i < cs.length; i++) {
            choices.put(i, cs[i]);
        }
        
        clear();
        
        int row = 0;
        
        row += getMaxPrintableRows() / 2;
        row -= getTopRow();
        row -= choices.size() / 2;
        
        for (int i = 0; i < row; i++) {
            println();
        }
        
        println();
        int choice = menu(choices, true);
        
        switch (choice) {
            case 0:
                return false;
            case 1:
                save(player);
                break;
            case 2:
                load(player);
                break;
            case 3:
                options();
                break;
            case 4:
                help(player);
                return true;
            case 5:
                exit();
                break;
        }
        
        clear();
        return false;
    }
    
    private static String chooseSaveSlot() {
        File dir = new File(".");

        String[] children = dir.list();
        if (children == null) {
            clear();
            printbig("  Could not get save directory! :(", Color.RED);
            pause();
            return null;
        }
        
        Map<Integer, Object> saveChoices = getSaveChoices(children);

        clear();
        
        int rows = (getMaxPrintableRows() - getTopRow()) / 2;
        rows -= saveChoices.size() / 2;
        
        for (int i = 0; i < rows; i++) {
            println();
        }
        
        int choice = menu(saveChoices, true);
        
        if (choice == 0) {
            return null;
        }
        
        return (String)saveChoices.get(choice);
    }
    
    private static void save(Player player) {
        String saveChoice = chooseSaveSlot();
        
        if (isBlank(saveChoice)) {
            return;
        }
        
        if (equals(saveChoice, EMPTY_SAVE)) {
            saveChoice = getNewSaveName();
            
            if (isBlank(saveChoice)) {
                clear();
                printbig("  Saving cancelled! :D", Color.GREEN);
                pause();
                return;
            }
            
            File file = new File(saveChoice);
            
            if (file.exists()) {
                clear();
                
                printbig("  Save with name '" + saveChoice + 
                        "' already exists, cancelling save!", Color.RED);
                
                pause();
                return;
            }
        }
        else {
            boolean overwrite = confirmOverwrite(saveChoice);
            
            if (!overwrite) {
                clear();
                printbig("  Overwriting save cancelled! :D", Color.GREEN);
                pause();
                return;
            }
        }
        
        try {
            FileOutputStream fout = new FileOutputStream(saveChoice);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            
            oos.writeObject(player);
            oos.close();
        }
        catch (Exception e) {
            clear();
            printbig("  Could not save! :(", Color.RED);
            println("This is why: " + e.getMessage());
            
            for (StackTraceElement element : e.getStackTrace()) {
                println("    " + element.toString());
            }
            
            pause();
            return;
        }
        
        File save = new File(saveChoice);
        
        String saveFile = save.getAbsolutePath();
        
        clear();
        printbig("  Save complete!", Color.GREEN);
        println("  Path of save: " + saveFile);
        pause();
    }
    
    private static String getNewSaveName() {
        clear();
        printbig("Enter save name (blank to cancel):");
        
        String file = input(true);
        
        if (isBlank(file)) {
            return null;
        }
        
        return file + ".wwsave";
    }
    
    private static boolean confirmLoad(String name) {
        String title = "Confirm load";
        String question = "Are you sure you want to load the save '" + name + "'?";
        
        DialogResult result = dialogue(title, question, DialogButtons.YES_NO);
        
        if (result == null) {
            return false;
        }
        
        if (result.equals(DialogResult.YES)) {
            return true;
        }
        
        return false;
    }
    
    private static boolean confirmOverwrite(String name) {
        String title = "Confirm save";
        String question = "Are you sure you want to overwrite '" + name + "'?";
        
        DialogResult result = dialogue(title, question, DialogButtons.YES_NO);
        
        if (result == null) {
            return false;
        }
        
        if (result.equals(DialogResult.YES)) {
            return true;
        }
        
        return false;
    }
    
    private static Map<Integer, Object> getSaveChoices(String[] children) {
        Map<Integer, Object> saveChoices = new HashMap<Integer, Object>();
        
        saveChoices.put(0, "<Return>");
        
        int i = 1;
        for (String child : children) {
            if (endsIn(child, ".wwsave")) {
                saveChoices.put(i++, child);
            }
        }
        
        for (int j = 1; j < 11; j++) {
            if (!saveChoices.containsKey(j)) {
                saveChoices.put(j, EMPTY_SAVE);
            }
        }
        
        return saveChoices;
    }
    
    private static void load(Player player) {
        String saveChoice = chooseSaveSlot();
        
        if (isBlank(saveChoice)) {
            return;
        }
        
        if (equals(saveChoice, EMPTY_SAVE)) {
            clear();
            printbig("  Cannot load empty save! :(", Color.RED);
            pause();
            return;
        }
        
        boolean load = confirmLoad(saveChoice);
        
        if (!load) {
            clear();
            printbig("  Loading aborted! :D", Color.GREEN);
            pause();
            return;
        }
        
        try {
            FileInputStream fin = new FileInputStream(saveChoice);
            ObjectInputStream ois = new ObjectInputStream(fin);
            player = (Player)ois.readObject();
            ois.close();
        }
        catch (Exception e) {
            clear();
            printbig("  Could not load! :(", Color.RED);
            println("This is why: " + e.getMessage());
            
            for (StackTraceElement element : e.getStackTrace()) {
                println("    " + element.toString());
            }
            
            pause();
            return;
        }

        Game.player.getStatusBar().replace();
        Game.player.getRegen().replace();
        
        Game.player = player;
        
        StatusBar.execute(Game.player);
        Regen.execute(Game.player);
        
        clear();
        printbig("  Load complete!", Color.GREEN);
        pause();
    }
    
    private static void options() {
        clear();
        printbig("  Not implemented yet.", Color.RED);
        pause();
    }
    
    private static void help(Player player) {
        HelpAction.action(player);
    }
    
    private static void exit() {
        System.exit(0);
    }
}
