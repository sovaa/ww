package ww.character.bwlv.buck;

import java.util.List;

import ww.character.Character;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class BuckLetOut {
    public static final void create(List<Conversation> intros) {
        Conversation letout = new Conversation() {
            private static final long serialVersionUID = -5571604815668735094L;

            @Override
            public boolean condition(Character character, Player player) {
                String questKey = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                
                String[] states = {
                        BeatUpMarekDravis.States.TOLD_BUCK_YOU_WOULD_MAKE_JOHN_LET_HIM_GO,
                        BeatUpMarekDravis.States.INFO_ARTIFACT_TOLD_BUCK_LET_HIM_OUT,
                        BeatUpMarekDravis.States.LET_HIM_GO_TO_GET_INFORMATION_ABOUT_MAREK,
                };
                
                return eligible(player, questKey, states);
            }
        };
        
        String john = CharacterNames.BlackWater.CAPTAIN_JOHN;
        
        letout.greet("Well? Have you talked to " + john + " yet? When can I get out of this hell hole?!");
        
        intros.add(letout);
    }
}
