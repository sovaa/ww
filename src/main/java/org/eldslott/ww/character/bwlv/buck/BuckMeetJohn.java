package org.eldslott.ww.character.bwlv.buck;

import java.util.List;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class BuckMeetJohn {
    public static final void create(List<Conversation> intros) {
        Conversation introMeetJohn = new Conversation() {
            private static final long serialVersionUID = -5571604815668735094L;

            @Override
            public boolean condition(Character character, Player player) {
                return eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, BeatUpMarekDravis.States.MEET_JOHN_AT_INN);
            }
        };
        
        introMeetJohn.greet("Why is the captain of the guard here? Maybe you should talk to him.");
        
        intros.add(introMeetJohn);
    }
}
