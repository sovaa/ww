package org.eldslott.ww.action;

import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.player.Rest;
import org.eldslott.ww.util.Common;

public class RestAction extends Common implements Action {
    private static final long serialVersionUID = 4062109150415452562L;

    public static boolean action(Player player) {
        Place place = player.getPlace();
        
        if (!place.isRestable()) {
            clear();
            printbig("You cannot rest here.");
            return true;
        }
        
        Rest.rest(player);
        
        return true;
    }
}
