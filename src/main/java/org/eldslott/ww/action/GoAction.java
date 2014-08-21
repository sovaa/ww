package org.eldslott.ww.action;

import java.io.IOException;
import java.util.Set;

import org.eldslott.ww.location.Area;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public class GoAction extends Common implements Action {
    private static final long serialVersionUID = 4053808192607826809L;

    public static boolean action(Player player, String what) throws IOException {
        clear();
        
        String areaKey = null;
        if (what.split(" ").length > 1) {
            // remove "go " from "go <place>", so we only get "<place>" in "what"
            areaKey = what.substring(what.split(" ")[0].length()).trim();
        }
        
        if (areaKey == null) {
            Area area = player.getArea();
            Set<String> keys = area.getAdjacentAreaKeys();
            
            if (keys == null || keys.size() == 0) {
                info("You are new to these lands and do not know your way " +
                        "around. You should explore some to find other areas " +
                        "and places of interest.");
                
                return true;
            }
            
            printbig("From here you can travel to: ");
            
            for (String key : keys) {
                println(" * " + area.getAdjecantAreaKey(key).getName());
            }
            
            println();
            println("Where do you want to go?");
            println();
            areaKey = input();
            
            if (areaKey.trim().length() == 0) {
                println("You choose to stay where you are. A wise choice in these dark times.");
                return true;
            }
        }
        
        Area area = player.getArea().getAdjecantArea(areaKey);
        
        if (area == null) {
            clear();
            printbig("There is no area called '" + areaKey + "'.");
            return true;
        }
        
        player.setArea(area);
        player.setPlace(area.getPlace());
        
        clear();
        print("    You travel to " + area.getName());
        
        for (int i = 0; i < 10; i++) {
            print(".");
            refresh();
            
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        println();
        println();
        pause();
        clear();
        
        info(area.getDescription());
        println();
        
        return true;
    }
}
