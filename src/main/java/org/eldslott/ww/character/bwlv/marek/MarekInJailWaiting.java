package org.eldslott.ww.character.bwlv.marek;

import java.util.List;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class MarekInJailWaiting {
    public static final void create(List<Conversation> intros) {
        Conversation jail = new Conversation() {
            private static final long serialVersionUID = -2056349213255034658L;
            
            @Override
            public boolean condition(Character character, Player player) {
                String questName = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                
                return eligible(player, questName, BeatUpMarekDravis.States.AGREED_TO_BUST_OUT_MAREK);
            }
        };
        
        jail.greet("Go to the alley behind the jail and kick in the grates " +
            	"after dark. And don't forget the rope!");
        
        intros.add(jail);
    }
}
