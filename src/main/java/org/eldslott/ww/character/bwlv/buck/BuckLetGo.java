package org.eldslott.ww.character.bwlv.buck;

import java.util.List;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class BuckLetGo {
    public static final void create(List<Conversation> intros) {
        Conversation letgo = new Conversation() {
            private static final long serialVersionUID = -3197838442548557712L;

            @Override
            public boolean condition(Character character, Player player) {
                return eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, BeatUpMarekDravis.States.MADE_JOHN_LET_BUCK_GO);
            }
            
            @Override
            public void action(Character character, Player player) {
                Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                String marek = CharacterNames.BlackWater.MAREK_DRAVIS;
                String buck = CharacterNames.BlackWater.BUCK;
                
                quest.complete();
                quest.setState(BeatUpMarekDravis.States.INFO_ARTIFACT_BUCK_IS_OUT);
                quest.update("I got some interesting information out of " + buck + " after he'd " +
                        "been released. Apparently he was trying to get some sort of artifact " +
                        "from " + marek + " that may have some value to it. Might be worth " +
                        "looking into.");
            }
        };
        
        String marek = CharacterNames.BlackWater.MAREK_DRAVIS;
        
        letgo.greet(Player.NAME + ", thanks for getting me out of that place... " +
                "Even though you put me there! Bah! Anyway... I told you I'd tell you " +
                "why I wanted you to talk to " + marek + ". You see... " + marek + " has " +
                "something. Some old piece of junk. An artifact you might say. I know a " +
                "buyer who would pay handsomly for it, but " + marek + " doesn't want to " +
                "sell it! Apparently he got it from some relative a long time ago. I was just trying " +
                "to make him see what was clearly the right thing to do! This has gotten " +
                "out of hand already... If you get " + marek + " to sell you the artifact then keep " +
                "it, I don't care! I don't want anything more to do with it...");
        
        intros.add(letgo);
    }
}
