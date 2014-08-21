package org.eldslott.ww;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.lanterna.LanternaException;
import org.eldslott.ww.action.ActionHandler;
import org.eldslott.ww.action.LootAction;
import org.eldslott.ww.character.CharacterKeys;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.player.Profession;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;
import org.eldslott.ww.reputation.Reputation;
import org.eldslott.ww.util.Commands;
import org.eldslott.ww.util.Helper;
import org.eldslott.ww.util.Logger;
import org.eldslott.ww.util.StatusBar;

/**
 * Wizard and Warrior
 * 
 * @author David Karmi <davidkarmi@hotmail.com>
 * @author Oscar Eriksson <oscar.eriks@gmail.com>
 */
public class Game extends ActionHandler {
    private static final long serialVersionUID = -4515722993203332559L;
    
    private static final Logger LOG = new Logger(Game.class);
    
    public static final String NAME = "Wizard and Warrior";
    public static final String VERSION = "0.0.1";
    
    public static Player player;
    
    public static void main (String[] arg) throws IOException {
        try {
            new Game().main();
            screen.stopScreen();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    /**
     * Main game loop.
     * @throws LanternaException
     */
    private void main() throws IOException, LanternaException {
        gameInit();
        clear();

    	while (true) {
    	    look();
    	    places();
            characters();
            loot();

            refresh();
            action(player);
    	}
    }
    
    private void look() {
        Helper.lookDescription(player);
    }
    
    private void places() {
        Helper.knownPlaces(player);
    }
    
    private void loot() {
        LootAction.lootableObjects(player);
    }
    
    private void characters() {
        Helper.knownCharacters(player);
    }
    
    private void gameInit() throws IOException {
        init();
        Commands.init();
        CharacterNames.init();
        Reputation.init();
        player = initPlayer();
    }
    
    private Player initPlayer() throws IOException {
        Player player = new Player();
        StatusBar.execute(player);
        
        player.setName(getName());
        player.setProfession(getProfession());
        
        Area startArea = Area.createAreas(player);
        
        player.setArea(startArea);
        player.setPlace(startArea.getPlace());

        /*String questName = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
        Quest quest = Quest.getQuest(questName);
        quest.setState(BeatUpMarekDravis.States.AGREED_TO_BUST_OUT_MAREK);
        Area.allPlaces.get(PlaceKeys.BlackWater.THE_JAIL).setDiscovered(true, false);
        Area.allPlaces.get(PlaceKeys.BlackWater.MAREK_DRAVIS_HOUSE).setDiscovered(true, false);
        Area.allPlaces.get(PlaceKeys.BlackWater.MAREKS_CELL).setDiscovered(true, false);
        Area.allPlaces.get(PlaceKeys.BlackWater.THE_JAIL_ALLEY).setDiscovered(true, false);
        player.addQuest(quest);
        Area.allPlaces.get(PlaceKeys.BlackWater.THE_JAIL_DUNGEON).setLocked(false);
        Area.allCharacters.get(CharacterKeys.BlackWater.MAREK_DRAVIS).setPlace(
                Area.allPlaces.get(PlaceKeys.BlackWater.THE_JAIL_DUNGEON));*/
        
        clear();
        
        return player;
    }
    
    private Profession getProfession() throws IOException {
        Map<Integer, Object> professions = new HashMap<Integer, Object>();
        
        int i = 0;
        for (Profession profession : Profession.values()) {
            professions.put(i, profession);
            i++;
        }
        
        while (true) {
            clear();
            printbig(" What's your profession?");
            
            int choice = menu(professions);

            if (!professions.containsKey(choice)) {
                clear();
                printbig(" There is no profession with key '" + choice + "'!");
                pause();
                continue;
            }
            
            return (Profession)professions.get(choice);
        }
    }
    
    private String getName() throws IOException {
        clear();
        printbig(" What's your name?");
        
        return input();
    }
}
