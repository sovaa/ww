package org.eldslott.ww.action;

import java.io.IOException;

import org.eldslott.ww.Encounter;
import org.eldslott.ww.Monster;
import org.eldslott.ww.character.Character;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public class ExploreAction extends Common implements Action {
    private static final long serialVersionUID = 9186152057591913322L;

    public static boolean action(Player player) throws IOException {
        clear();
        
        if (player.getPlace().isExit()) {
            info("You need to exit from " + player.getPlace().getName() + " before you can explore.");
            return true;
        }
        
        Monster monster = Area.monster(player);
        
        if (monster != null) {
            Encounter.monster(player, monster);
            return true;
        }
        
        Place place = Area.place(player);

        if (place != null) {
            Encounter.place(player, place);
            return true;
        }
        
        Area area = Area.area(player);

        if (area != null) {
            Encounter.area(player, area);
            return true;
        }
        
        Character character = Area.character(player);
        
        if (character != null) {
            Encounter.character(player, character);
            return true;
        }
        
        println("You encounter nothing.");
        return true;
    }
}
