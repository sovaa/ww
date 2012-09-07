package ww.character.bwlv.buck;

import java.util.List;

import ww.character.Character;
import ww.character.Conversation;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

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
