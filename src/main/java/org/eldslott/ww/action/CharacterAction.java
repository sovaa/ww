package org.eldslott.ww.action;

import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public class CharacterAction extends Common implements Action {
    private static final long serialVersionUID = -342786339167507149L;
    
    public static boolean action(Player player) {
        clear();
        
        println("  Name         " + player.getName());
        println("  Profession   " + player.getProfession().toString());
        println();
        println("  Health       " + player.getCurHp() + " (" + player.getMaxHp() + ")");
        println("  Mana         " + player.getCurMana() + " (" + player.getMaxMana() + ")");
        println();
        println("  Strength     " + player.getStrength());
        println("  Dexterity    " + player.getDexterity());
        println("  Spirit       " + player.getSpirit());
        println("  Intellect    " + player.getIntellect());
        println();
        println("  Carry weight TODO"); // TODO
        println();
        println();
        
        return true;
    }
}
