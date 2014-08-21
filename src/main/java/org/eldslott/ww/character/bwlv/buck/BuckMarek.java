package org.eldslott.ww.character.bwlv.buck;

import org.eldslott.ww.Skill;
import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterKeys;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class BuckMarek {
    public static final void create(Conversation intro) {
        Conversation marek = new Conversation() {
            private static final long serialVersionUID = -3197838442548557712L;

            @Override
            public boolean condition(Character character, Player player) {
                return eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, BeatUpMarekDravis.States.KILLED_MAREK);
            }
            
            @Override
            public void greeting(Character character, Player player) {
                Character marek = Area.getGlobalCharacter(CharacterKeys.BlackWater.MAREK_DRAVIS);
                Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                quest.complete();
                
                if (marek.isDead()) {
                    answer("Oh jeez... You had him killed? *sigh* Oh, well... " +
                            "He probably had it comming anyway.\n\n Come, I'll " +
                            "said I'd teach you a thing or two.", character.getName());

                    quest.update("Buck was not so pleased with your task because of your actions.");
                    quest.setState(BeatUpMarekDravis.States.TALKED_TO_BUCK_AFTER_MAREK_KILLED);
                }
                else {
                    answer("Great! I knew you had it in you! That bastard " +
                            "has probably learned his lesson...\n\n Now then, " +
                            "about your reward! Come and I'll teach you some " +
                            "neat tricks I learned from a skilled swordsman " +
                            "when I was young.", character.getName());
                    
                    quest.update("Buck was pleased with your task.");
                    quest.setState(BeatUpMarekDravis.States.TALKED_TO_BUCK_AFTER_MAREK_BEATEN);
                }
                
                Skill oneHanded = player.getSkill(Skill.ONE_HANDED);
                
                if (oneHanded == null) {
                    // TODO create
                }
                else {
                    player.increaseSkill(oneHanded);
                }
                
                quest.setRewarded(true);
            }
        };
        
        marek.setReply("Marek Dravis");
        
        intro.addReply(marek);
    }
}
