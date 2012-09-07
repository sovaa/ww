package ww.action;

import ww.location.Place;
import ww.player.Player;
import ww.player.Rest;
import ww.util.Common;

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
