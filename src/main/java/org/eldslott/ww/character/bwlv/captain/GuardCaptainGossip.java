package org.eldslott.ww.character.bwlv.captain;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;

public class GuardCaptainGossip {
    public static final void create(Conversation intro) {
        Conversation gossip = new Conversation() {
            private static final long serialVersionUID = -2842084041874226004L;

            @Override
            public boolean condition(Character character, Player player) {
                return !eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS);                
            }
        };
         
        gossip.setReply("Gossip");
        gossip.end("Who do you think I am? Get lost!");
        intro.addReply(gossip);
    }
}
