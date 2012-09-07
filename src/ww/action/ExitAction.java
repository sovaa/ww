package ww.action;

import ww.player.Player;
import ww.util.Common;

public class ExitAction extends Common implements Action {
    private static final long serialVersionUID = 4308971541456243211L;

    public static boolean action(Player player) {
        if (!player.getPlace().isExit() || player.getPlace().equals(player.getArea().getPlace())) {
            clear();
            info("You cannot exit from " + player.getPlace().getName());
            return true;
        }
        
        clear();
        info("You exit from " + player.getPlace().getName() + ".");
        player.setPlace(player.getArea().getPlace());
        
        return false;
    }
}
