package ww.location.bwlv.place;

import ww.item.BlackWaterJailDungeonKey;
import ww.location.Area;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.location.PlaceNames;
import ww.player.Player;

public class JailDungeon extends Place {
    private static final long serialVersionUID = 3563044294126477901L;
    
    public JailDungeon(Area area, Player player) {
        String keys[] = new String[] {
                "jail dungeon", "the jail dungeon", "dungeon", "the dungeon",
        };
        
        setMainKey(PlaceKeys.BlackWater.THE_JAIL_DUNGEON);
        setKeys(keys);
        addConnectingPlace(PlaceKeys.BlackWater.THE_JAIL);
        
        setLookDescription("in " + PlaceNames.BlackWater.THE_JAIL_DUNGEON + " in " + area.getName() + ".");

        setName(PlaceNames.BlackWater.THE_JAIL_DUNGEON);
        setDescription("A few tiny cells are cramped together in the dark. It smells vaguely of piss.");
        setDiscoveryFrequency(0);
        setExit(true);
        setRestable(false);
        setOutdoors(false);
        setLocked(true, BlackWaterJailDungeonKey.id);
        setDiscovered(true, false);
        
        Area.allPlaces.put(PlaceKeys.BlackWater.THE_JAIL_DUNGEON, this);
    }
}
