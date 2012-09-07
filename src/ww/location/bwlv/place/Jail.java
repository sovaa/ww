package ww.location.bwlv.place;

import ww.character.bwlv.JailerLarry;
import ww.location.Area;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.location.PlaceNames;
import ww.player.Player;

public class Jail extends Place {
    private static final long serialVersionUID = -22556605575683204L;

    public Jail(Area area, Player player) {
        String keys[] = new String[]{
                "jail", "the jail"
        };
        
        setMainKey(PlaceKeys.BlackWater.THE_JAIL);
        setKeys(keys);
        setLookDescription("in " + PlaceNames.BlackWater.THE_JAIL + " in " + area.getName() + ".");
        addConnectingPlace(PlaceKeys.BlackWater.THE_JAIL_DUNGEON);
        addConnectingPlace(PlaceKeys.BlackWater.THE_JAIL_ALLEY);

        setName(PlaceNames.BlackWater.THE_JAIL);
        setDescription("The towns' jail is quite small and not often used. It smells of rot and decay none-the-less.");
        setDiscoveryFrequency(0);
        setExit(true);
        setRestable(false);
        setOutdoors(false);

        Area.allPlaces.put(PlaceKeys.BlackWater.THE_JAIL, this);
        
        // characters
        {
            new JailerLarry(this);
        }
        
        area.addPlace(this);
    }
}
