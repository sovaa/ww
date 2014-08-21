package org.eldslott.ww.quests.bwlv;

import java.io.Serializable;

import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.PlaceNames;
import org.eldslott.ww.quests.Quest;

public class RatInfestation extends Quest {
    private static final long serialVersionUID = 5265212948774370435L;

    public static class States implements Serializable {
        private static final long serialVersionUID = 5836398939954622632L;
        
        public static final String ACCEPTED = "accepted";
    };
    
    public RatInfestation() {
        setState(States.ACCEPTED);
        
        setId(RAT_INFESTATION);
        setName("Rat infestation");
        
        setDescription("Mona, the waitress of " + PlaceNames.BlackWater.THE_BUSTY_MAID_INN + " in " +
                Area.THE_BLACK_WATER_LODGE_VILLAGE + " wants you to take care of a rodent problem " +
                "in the tavern basement.");
    }
}
