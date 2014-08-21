package org.eldslott.ww.location.bwlv.place;

import org.eldslott.ww.item.BlackWaterJailDungeonKey;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.location.PlaceNames;
import org.eldslott.ww.player.Player;

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
