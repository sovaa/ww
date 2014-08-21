package org.eldslott.ww.character.bwlv.buck;

import java.util.List;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class BuckStay {
    public static final void create(List<Conversation> intros) {
        Conversation stay = new Conversation() {
            private static final long serialVersionUID = -5571604815668735094L;

            @Override
            public boolean condition(Character character, Player player) {
                String questKey = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                String state = BeatUpMarekDravis.States.INFO_ARTIFACT_TOLD_BUCK_STAY_IN_JAIL;
                
                return eligible(player, questKey, state);
            }
        };
        
        stay.greet("Get the hell out of my face you worm!");
        
        intros.add(stay);
    }
}
