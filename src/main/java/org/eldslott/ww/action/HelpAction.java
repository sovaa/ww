package org.eldslott.ww.action;

import java.util.List;

import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Commands;
import org.eldslott.ww.util.Common;

public class HelpAction extends Common implements Action {
    private static final long serialVersionUID = 5589865025610194721L;

    public static boolean action(Player player) {
        clear();
        
        info("Welcome " + player.getName() + " the " + player.getProfession().toString() + " to Warrior or Wizard! " +
                "In this epic game you will encounter monsters and stuff!");
        
        println();
        
        List<String> commands = Commands.allCommands;
        
        println("Available commands:");
        println();
        
        for (String command : commands) {
            println(" " + command);
        }
        
        println();
        
        return true;
    }
}
