package org.eldslott.ww.location.bwlv.place;

import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.bwlv.MarekDravis;
import org.eldslott.ww.item.Container;
import org.eldslott.ww.item.Rope;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.location.PlaceNames;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class MarekDraveksHouse extends Place {
    private static final long serialVersionUID = 2116691255295067708L;

    public MarekDraveksHouse(Area area, Player player) {
        String keys[] = new String[]{
                "marek", "mareks house", "marek's house", "marek dravis",
                "marek dravis house", "house of marek", "marek dravis' house"
        };
        
        setMainKey(PlaceKeys.BlackWater.MAREK_DRAVIS_HOUSE);
        setKeys(keys);
        setLookDescription("in Marek Dravis' house in " + area.getName() + ".");

        setName(PlaceNames.BlackWater.MAREK_DRAVIS_HOUSE);
        setDescription("A small and run down house in need of a female's touch.");
        setDiscovered(false);
        setDiscoveryFrequency(0);
        setExit(true);
        setRestable(false);
        setOutdoors(false);
        
        addObject(new Container(new Rope(10)));
        
        Area.allPlaces.put(PlaceKeys.BlackWater.MAREK_DRAVIS_HOUSE, this);

        // characters
        {
            new MarekDravis(player, this);
        }
        
        area.addPlace(this);
    }
    
    @Override
    public void visit(Player player) {
        Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
        
        if (quest == null) {
            return;
        }
        
        if (!quest.getState().equals(BeatUpMarekDravis.States.JOHN_SENT_INVESTIGATOR)) {
            return;
        }
        
        String marek = CharacterNames.BlackWater.MAREK_DRAVIS;
        String mareks = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
        String john = CharacterNames.BlackWater.CAPTAIN_JOHN;
        String johns = CharacterNames.BlackWater.CAPTAIN_JOHN_SHORT;
        String gordon = CharacterNames.BlackWater.GUARD_GORDON;
        String gordons = CharacterNames.BlackWater.GUARD_GORDON_SHORT;
        String buck = CharacterNames.BlackWater.BUCK;
        
        notice(String.format("%s lies dead on the floor, and %s is nowhere to be seen.",
                gordon, marek));
        
        quest.update(String.format("I went to %s' house after %s sent his man %s to investigate " + 
                "%ss quarrel with %s, but when I got there, %s was gone and %s lay " +
                "dead on the floor. Either %s is just a drunken idiot who accidentally " +
                "killed %s, or there's something more to this than just a quarrel. " +
                "Otherwise %s is a bigger fool than I thought by killing a city guard... " +
                "I'd better go tell %s what has happened as quickly as possible.",
                marek, john, gordon, buck, mareks, mareks, gordons, mareks, gordons, mareks, johns));
        
        quest.setState(BeatUpMarekDravis.States.FOUND_GORDON_DEAD);
    }
}
















