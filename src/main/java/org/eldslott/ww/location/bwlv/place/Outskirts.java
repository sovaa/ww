package org.eldslott.ww.location.bwlv.place;

import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.location.PlaceNames;
import org.eldslott.ww.player.Player;

public class Outskirts extends Place {
    private static final long serialVersionUID = -8123718078047328755L;
    
    public Outskirts(Area area, Player player) {
        String keys[] = new String[]{
                "outskirts", "o", "the outskirts", "outskirt", "the outskirt"
        };
        
        setMainKey(PlaceKeys.BlackWater.THE_OUTSKIRTS);
        setKeys(keys);
        setLookDescription("in " + PlaceNames.BlackWater.THE_OUTSKIRTS + " of " + area.getName() + ".");
        setDiscovered(true, false);
        
        setName(PlaceNames.BlackWater.THE_OUTSKIRTS);
        setDescription(
                "At the outskirts of the village a couple of small farm houses lie along a " +
                "well-worn road leading from the old trading route to the village center, the " +
                "Town Square.");
        
        Area.allPlaces.put(PlaceKeys.BlackWater.THE_OUTSKIRTS, this);
        
        area.addPlace(this);
        area.setPlace(this); // start place in this area
    }
}
