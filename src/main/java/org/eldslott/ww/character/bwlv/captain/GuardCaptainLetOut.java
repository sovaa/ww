package org.eldslott.ww.character.bwlv.captain;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterKeys;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class GuardCaptainLetOut {
    public static final void create(Conversation intro) {
        Conversation letout = new Conversation() {
            private static final long serialVersionUID = -4929677391775217586L;

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
            
            @Override
            public void action(Character character, Player player) {
                String marek = CharacterNames.BlackWater.MAREK_DRAVIS;
                String john = CharacterNames.BlackWater.CAPTAIN_JOHN;
                String buck = CharacterNames.BlackWater.BUCK;
                
                Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);

                quest.complete();
                quest.setState(BeatUpMarekDravis.States.MADE_JOHN_LET_BUCK_GO);
                quest.update("I talked to " + john + " and explained to him that " + buck + " " +
                        "never tried to get " + marek + " killed. He told me he'd let " + buck + " out.");
                
                Character buckCharacter = Area.getGlobalCharacter(CharacterKeys.BlackWater.BUCK);
                
                buckCharacter.setPlace(Area.getGlobalPlace(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN));
            }
        };

        String marek = CharacterNames.BlackWater.MAREK_DRAVIS;
        String buck = CharacterNames.BlackWater.BUCK;
        
        letout.setReply(buck + " is innocent. He never wanted " + marek + " killed... He " +
                "just asked me to talk to him about his tab at the bar. " + buck + " was " +
                "just trying to get him to pay it.");
        
        letout.end("*Sigh* Well why did you tell me he tried to have him killed?! Nevermind... " +
                "I'll let him out. I never suspected him anyway, he's not that sort of fellow.");
        
        intro.addReply(letout);
    }
}
