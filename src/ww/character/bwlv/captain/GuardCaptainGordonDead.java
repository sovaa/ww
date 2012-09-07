package ww.character.bwlv.captain;

import ww.character.Character;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class GuardCaptainGordonDead {
    public static final String gordons = CharacterNames.BlackWater.GUARD_GORDON_SHORT;
    public static final String mareks = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
    public static final String johns = CharacterNames.BlackWater.CAPTAIN_JOHN_SHORT;
    public static final String buck = CharacterNames.BlackWater.BUCK;
    
    public static final void create(Conversation intro) {
        Conversation gordon = new Conversation() {
            private static final long serialVersionUID = -173216733469189689L;

        @Override
           public boolean condition(Character character, Player player) {
               return eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, BeatUpMarekDravis.States.FOUND_GORDON_DEAD);
           }
        };
         
        gordon.setReply(String.format("I found %s dead at %ss house.",
                gordons, mareks));
        
        gordon.greet(String.format("Dead? Son of a... *sigh* Did you see %s anywhere?", mareks));
         
        {
            Conversation gone = gordon.reply("No, he was nowhere to be found.");
            gone.greet(String.format("I thought as much... If he's left the village he can't have gotten far. " +
                	"I'll send my men to look for him, they'll find him. Could you go and tell %s? " +
                	"It would be good if he could keep an eye out for %s if he's stupid enought to " +
                	"try and hide there.", buck, mareks));
            
            Conversation ok = new Conversation() {
                private static final long serialVersionUID = 1568404159481667939L;
                
                @Override
                public void action(Character character, Player player) {
                    Quest quest = player.getQuest(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                    
                    quest.update(String.format("I told %s that I had found %s dead in %s' house. %s " +
                        	"thought I should go and tell %s what happened, and to tell him to " +
                        	"keep an eye out for %s.",
                        	johns, gordons, mareks, johns, buck, mareks));
                    
                    quest.setState(BeatUpMarekDravis.States.TOLD_JOHN_GORDON_DEAD);
                }
            };
            
            ok.setReply("I will.");
            ok.end(String.format("Good. Come back and see me later. Maybe my men have catched %s by then.", mareks));
            gone.addReply(ok);
            
            gone.reply("Bye.");
        }
         
        intro.addReply(gordon);
    }
}
