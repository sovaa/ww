package ww.location.bwlv.place;

import java.util.UUID;

import ww.character.CharacterNames;
import ww.item.Item;
import ww.location.Area;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.location.PlaceNames;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class JailAlley extends Place {
    private static final long serialVersionUID = -771995073208467694L;
    
    private static final class Gratings extends Item {
        private static final long serialVersionUID = 2424340654017274914L;
        
        public Gratings() {
            setId(UUID.randomUUID().toString());
            setName("Gratings");
            setSlot(Slot.none);
            setCarry(false);
        }
        
        @Override
        public boolean useOn(Player player, Item item) {
            if (!equals(item.getName(), "Rope")) {
                clear();
                printbig("  You cannot use that on the gratings!");
                pause();
                return true;
            }
            
            if (!combines.isEmpty()) {
                clear();
                printbig("  You have already tied a rope to the gratings.");
                pause();
                return true;
            }
            
            addCombine(item);
            
            clear();
            printbig("  You tie the rope to the gratings.");
            pause();
            
            return true;
        }
    }
    
    public JailAlley(Area area, Player player) {
        String keys[] = new String[] {
                "alley", "jail alley", "the jail alley", "the alley",
        };
        
        String cell[] = {
                "gratings", "grating", "bars", "window"
        };
        
        setMainKey(PlaceKeys.BlackWater.THE_JAIL_ALLEY);
        setKeys(keys);
        addConnectingPlace(PlaceKeys.BlackWater.MAREKS_CELL);
        addConnectingPlace(PlaceKeys.BlackWater.THE_JAIL);
        addKickableEntrance(cell, PlaceKeys.BlackWater.MAREKS_CELL);
        
        addObject(new Gratings());
        
        setLookDescription("at the " + PlaceNames.BlackWater.THE_JAIL_ALLEY + " in " + area.getName() + ".");

        setName(PlaceNames.BlackWater.THE_JAIL_ALLEY);
        setDescription("Behind the Jail a narrow alley runs. No windows face the alley, " +
            	"and the high buildings surrounding it prevents most light from reaching " +
            	"the ground, covering the alley in darkness. A shady characters' perfect condition.");
        
        setDiscoveryFrequency(0);
        setExit(true);
        setRestable(false);
        setOutdoors(false);
        
        Area.allPlaces.put(PlaceKeys.BlackWater.THE_JAIL_ALLEY, this);
    }
    
    @Override
    public void visit(Player player) {
        Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
        String mareks = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
        
        if (quest == null) {
            return;
        }
        
        if (quest.getState().equals(BeatUpMarekDravis.States.KICKED_GRATES_TO_JAIL_CELL)) {
            notice(String.format("The gratings to %s's cell has been kicked loose. It's " +
                	"possible for a person to climb through it.", mareks));
            
            return;
        }
        
        if (quest.getState().equals(BeatUpMarekDravis.States.AGREED_TO_BUST_OUT_MAREK)) {
            notice(String.format("You see the gratings to %s's cell. The bars are rusty and the " +
                    "surrounding stone seems to be loose. It looks like it's possible to kick it in.",
                    mareks));
            
            return;
        }
    }
}
