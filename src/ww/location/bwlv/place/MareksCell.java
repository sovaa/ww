package ww.location.bwlv.place;

import ww.character.CharacterNames;
import ww.location.Area;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.location.PlaceNames;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class MareksCell extends Place {
    private static final long serialVersionUID = 4179326619124568174L;

    public MareksCell(Area area, Player player) {
        String keys[] = new String[] {
                "cell", "marek's cell", "mareks cell", "marek"
        };
        
        setMainKey(PlaceKeys.BlackWater.MAREKS_CELL);
        setKeys(keys);
        addConnectingPlace(PlaceKeys.BlackWater.THE_JAIL_ALLEY);
        
        setLookDescription("in " + PlaceNames.BlackWater.MAREKS_CELL + " in " + area.getName() + ".");

        setName(PlaceNames.BlackWater.MAREKS_CELL);
        setDescription("Behind the Jail a narrow alley runs. No windows face the alley, " +
                "and the high buildings surrounding the it prevents most light from reaching " +
                "the ground, covering the alley in darkness. A shady characters perfect condition.");
        
        setDiscoveryFrequency(0);
        setExit(true);
        setRestable(false);
        setOutdoors(false);
        setLocked(true);
        setEnterable(false);
        setEntranceKickable(true);
        setDiscovered(false);
        
        Area.allPlaces.put(PlaceKeys.BlackWater.MAREKS_CELL, this);
    }
    
    @Override
    public void kick(Player player, Place currentPlace) {
        if (!isLocked()) {
            clear();
            printbig("  You have already kicked open the gratings.");
            return;
        }
        
        setLocked(false);
        setDiscovered(true);
        
        String mareks = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
        String str = String.format("You kick at the gratings to %s's cell. After a " +
            	"couple of tries they break, leaving a fair-sized hole through " +
            	"which a person could squeeze through.", mareks, mareks);
        
        clear();
        notice(str);
        
        Quest quest = player.getQuest(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
        
        if (quest == null) {
            return;
        }
        
        quest.setState(BeatUpMarekDravis.States.KICKED_GRATES_TO_JAIL_CELL);
    }
}
