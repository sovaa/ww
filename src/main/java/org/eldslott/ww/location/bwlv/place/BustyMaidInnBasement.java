package org.eldslott.ww.location.bwlv.place;

import org.eldslott.ww.character.bwlv.RatPack;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.location.PlaceNames;
import org.eldslott.ww.player.Player;

public class BustyMaidInnBasement extends Place {
    private static final long serialVersionUID = -5382392516429782790L;

    public BustyMaidInnBasement(Area area, Player player) {
        String keys[] = new String[]{
                "basement", "the busty inn basement", "busty basement",
                "inn basement", "maid basement", "the busty maid inn basement",
                "busty maid inn basement"
        };
        
        setMainKey(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN_BASEMENT);
        setKeys(keys);
        setLookDescription("in The Busty Maid Basement in " + area.getName() + ".");
        addConnectingPlace(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN);

        setName(PlaceNames.BlackWater.THE_BUSTY_MAID_INN_BASEMENT);
        setDescription("The dark and damp basement of The Busty Maid Inn.");
        setDiscovered(false);
        setDiscoveryFrequency(0);
        setExit(true);
        setRestable(false);
        setOutdoors(false);

        Area.allPlaces.put(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN_BASEMENT, this);
        
        // characters
        {
            new RatPack(this);
        }
    }
}
