package org.eldslott.ww.player;

import org.eldslott.ww.Skill;

public class ProfessionHandler {
    public static void setBonuses(Player player, Profession profession) {
        if (Profession.TEACHER.equals(profession)) {
            teacher(player);
        }
        else if (Profession.WARRIOR.equals(profession)) {
            teacher(player);
        }
        else if (Profession.MONK.equals(profession)) {
            teacher(player);
        }
        else if (Profession.ARTISIAN.equals(profession)) {
            teacher(player);
        }
        else if (Profession.TRAVELLER.equals(profession)) {
            teacher(player);
        }
        else if (Profession.ASSASSIN.equals(profession)) {
            assassin(player);
        }
        else if (Profession.THIEF.equals(profession)) {
            teacher(player);
        }
        else if (Profession.HUNTER.equals(profession)) {
            teacher(player);
        }
        else if (Profession.ALCHEMIST.equals(profession)) {
            teacher(player);
        }
    }
    
    private static void assassin(Player player) {
        player.getSkill(Skill.SPEECH).setValue(5);
    }
    
    private static void teacher(Player player) {
        
    }
}
