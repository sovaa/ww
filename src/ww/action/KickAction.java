package ww.action;

import java.util.Map;

import ww.location.Area;
import ww.location.Place;
import ww.player.Player;
import ww.util.Common;
import ww.util.Logger;

public class KickAction extends Common implements Action {
    private static final long serialVersionUID = -4812716296025104657L;
    
    private static final Logger LOG = new Logger(KickAction.class);
    
    public static boolean action(Player player, String what) {
        String object = stripWhat(what);
        
        if (isBlank(object)) {
            clear();
            printbig("  You kick at the empty air. A mighty *woosh* sound follows.");
            return true;
        }
        
        Place currentPlace = player.getPlace();
        
        Map<String, String> places = currentPlace.getKickableEntrances();
        
        if (!places.containsKey(object)) {
            clear();
            printbig("  There is no such thing to kick here.");
            return true;
        }
        
        Place place = Area.getGlobalPlace(places.get(object));
        
        if (place == null) {
            clear();
            printbig("  There is no such place here: " + places.get(object));
            LOG.error("no place in global places with key " + places.get(object));
            return true;
        }
        
        place.kick(player, currentPlace);
        
        return true;
    }
}
