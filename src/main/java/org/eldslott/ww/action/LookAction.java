package org.eldslott.ww.action;

import java.util.ArrayList;
import java.util.List;

import org.eldslott.ww.util.Color;
import org.eldslott.ww.item.Item;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;
import org.eldslott.ww.util.Helper;

public class LookAction extends Common implements Action {
    private static final long serialVersionUID = -5515772235282140527L;

    public static boolean action(Player player) {
        clear();
        format(player.getLookDescription());
        println();
        format(player.getArea().getDescription());
        println();
        format(player.getPlace().getDescription());
        
        println();
        Helper.knownPlaces(player);
        
        List<String> carryItems = new ArrayList<String>();
        List<String> openItems = new ArrayList<String>();
        
        for (Item item : player.getPlace().getObjects()) {
            if (item.isCarry()) {
                carryItems.add(item.getName());
            }
            else if (item.isOpenable()) {
                openItems.add(item.getName());
            }
        }
        
        if (carryItems.size() > 0) {
            println("There are some items you can take here: ");
            format("    " + join(carryItems) + ".", Color.GREEN);
            println();
        }
        
        if (openItems.size() > 0) {
            println("There are some items you can open here: ");
            format("    " + join(openItems) + ".", Color.GREEN);
            println();
        }
        
        return true;
    }
}
