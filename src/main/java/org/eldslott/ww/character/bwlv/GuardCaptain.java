package org.eldslott.ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterKeys;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.character.bwlv.captain.GuardCaptainGordonDead;
import org.eldslott.ww.character.bwlv.captain.GuardCaptainGossip;
import org.eldslott.ww.character.bwlv.captain.GuardCaptainJail;
import org.eldslott.ww.character.bwlv.captain.GuardCaptainLetOut;
import org.eldslott.ww.character.bwlv.captain.GuardCaptainMarek;
import org.eldslott.ww.character.bwlv.captain.GuardCaptainMeet;
import org.eldslott.ww.character.bwlv.captain.GuardCaptainToldBuck;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class GuardCaptain extends Character {
    private static final long serialVersionUID = -1129697316153557565L;
    
    public GuardCaptain(Place place) {
        setPlace(place);
        
        id = CharacterKeys.BlackWater.CAPTAIN_JOHN;
        name = CharacterNames.BlackWater.CAPTAIN_JOHN;
        description = "A man yo real good yeh";
        
        discovered = true;
        
        aliases = new HashSet<String>(Arrays.asList(new String[]{
                 "guard captain",
                 "captain",
                 "captain john",
                 "guard captain john",
                 "john"
        }));
        
        Conversation intro = new Conversation() {
            private static final long serialVersionUID = 4294158191151059334L;

            @Override
            public boolean condition(Character character, Player player) {
                String[] states = {
                        BeatUpMarekDravis.States.MEET_JOHN_AT_INN,
                        BeatUpMarekDravis.States.TOLD_BUCK_GORDON_DEAD
                };
                
                return !eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, states);
            }
        };
        
        intro.greet("Hello Civilian! Anything to report?");
        intros.add(intro);
        
        // create the conversations; in separate classes to avoid this class being cluttered
        GuardCaptainMeet.create(intros);
        GuardCaptainLetOut.create(intro);
        GuardCaptainJail.create(intro);
        GuardCaptainMarek.create(intro);
        GuardCaptainGossip.create(intro);
        GuardCaptainGordonDead.create(intro);
        GuardCaptainToldBuck.create(intros);
         
        Conversation bye = intro.reply("Bye");
        bye.end("Take care, now.");
    }

    @Override
    public void fight(Player player) {
    }

    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.CAPTAIN_JOHN);
    }
}
