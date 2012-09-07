package ww.location.bwlv.place;

import ww.character.bwlv.Buck;
import ww.character.bwlv.Mona;
import ww.location.Area;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.location.PlaceNames;
import ww.player.Player;

public class BustyMaidInn extends Place {
    private static final long serialVersionUID = 6521638800972769406L;

    public BustyMaidInn(Area area, Player player) {
        String keys[] = new String[]{
                "inn", "the busty inn", "busty", "maid", "busty maid",
                "busty inn", "the busty maid"
        };
        
        setMainKey(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN);
        setKeys(keys);
        addConnectingPlace(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN_BASEMENT);
        setLookDescription("in The Busty Maid in " + area.getName() + ".");

        setName(PlaceNames.BlackWater.THE_BUSTY_MAID_INN);
        setDescription("A small inn run by Buck.");
        setDiscoveryFrequency(50);
        setExit(true);
        setSafeRest(true);
        setRestCost(10);
        setOutdoors(false);

        Area.allPlaces.put(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN, this);
        
        // characters
        {
            new Buck(this);
            new Mona(this);
        }
        
        area.addPlace(this);
    }
}
