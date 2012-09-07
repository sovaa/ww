package ww.character.bwlv.captain;

import ww.character.Character;
import ww.character.Conversation;
import ww.player.Player;
import ww.quests.Quest;

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
