package org.eldslott.ww.location.bwlv.place;

import org.eldslott.ww.character.bwlv.Geed;
import org.eldslott.ww.character.bwlv.GuardCaptain;
import org.eldslott.ww.character.bwlv.GuardGordon;
import org.eldslott.ww.character.bwlv.Gunnar;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.location.PlaceNames;
import org.eldslott.ww.player.Player;

public class TownSquare extends Place {
    private static final long serialVersionUID = 7103757014416614751L;

    public TownSquare(Area area, Player player) {

        String keys[] = new String[]{
                "square", "ts", "the town square", "town square", "tts"
        };
        
        setMainKey(PlaceKeys.BlackWater.THE_TOWN_SQUARE);
        setKeys(keys);
        setLookDescription("at " + PlaceNames.BlackWater.THE_TOWN_SQUARE + " of " + area.getName() + ".");
        setDiscovered(true, false);
        setRestable(false);
        
        setName("The Town Square");
        setDescription(
                "At the center of the village, a small square lies where season festivals " +
                "usually take place. Sunday markets are held here where the town folks sell food and " +
                "vegetables, clothes, weapons, the occasional grimoire from visiting merchants--" +
                "though rare nowadays--and all kinds of mysterious and surely! magical " +
                "trinkets of all sizes and colours.");
        
        Area.allPlaces.put(PlaceKeys.BlackWater.THE_TOWN_SQUARE, this);
        
        // characters
        {
            new GuardCaptain(this);
            new Geed(player, this);
            new Gunnar(this);
            new GuardGordon(this);
        }
        
        area.addPlace(this);
    }
}
