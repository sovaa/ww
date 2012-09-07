package ww.character.bwlv.buck;

import java.util.List;

import ww.character.Character;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class BuckGordonDead {
    private static final String playerName = Player.NAME;
    private static final String mareks = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
    private static final String johns = CharacterNames.BlackWater.CAPTAIN_JOHN_SHORT;
    private static final String gordon = CharacterNames.BlackWater.GUARD_GORDON;
    private static final String gordons = CharacterNames.BlackWater.GUARD_GORDON_SHORT;
    private static final String buck = CharacterNames.BlackWater.BUCK;
    
    public static final void create(List<Conversation> intros) {
        Conversation tell = new Conversation() {
            private static final long serialVersionUID = -4508057814988734644L;

            @Override
            public boolean condition(Character character, Player player) {
                String questName = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                
                if (eligible(player, questName, BeatUpMarekDravis.States.TOLD_JOHN_GORDON_DEAD)) {
                    return true;
                }
                
                return false;
            }
        };
        
        tell.greet(String.format("%s! How goes it? Have you... *cough* spoken to %s yet?", playerName, mareks));
        
        {
            Conversation dead = new Conversation() {
                private static final long serialVersionUID = -3422942075481351483L;
                
                @Override
                public void action(Character character, Player player) {
                    Quest quest = player.getQuest(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                    
                    quest.update(String.format("I told %s about finding %s dead and %s being on the run. %s " +
                        	"suddently wanted me to forget about this whole mess. I bet there is more " +
                        	"going on here than he's letting me in on. Maybe I should get back to %s and " +
                        	"see if he needs any help tracking down %s.",
                        	buck, gordons, mareks, buck, johns, mareks));
                    
                    quest.setState(BeatUpMarekDravis.States.TOLD_BUCK_GORDON_DEAD);
                }
            };
            
            dead.setReply(String.format("Actually, I told %s you had some " +
                	"trouble with him, and he sent one of his men--%s--to speak to %s, but it looks " +
                	"like %s killed %s, and is now on the run. %ss men are out looking for him. " +
                	"%s also told me to ask you to keep an eye out for him.", 
                	johns, gordon, mareks, gordons, johns, johns, mareks));
            
            dead.end("Ye gods... Better forget about this son, this is getting out of hand. You should leave.");
            
            tell.addReply(dead);
        }
        
        intros.add(tell);
    }
}
