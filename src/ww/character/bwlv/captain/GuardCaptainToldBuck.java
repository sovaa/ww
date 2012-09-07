package ww.character.bwlv.captain;

import java.util.List;

import ww.location.Area;
import ww.location.PlaceKeys;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;
import ww.character.CharacterKeys;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.character.Character;

public class GuardCaptainToldBuck {
    private static final String playerName = Player.NAME;
    private static final String mareks = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
    private static final String johns = CharacterNames.BlackWater.CAPTAIN_JOHN_SHORT;
    
    public static final void create(List<Conversation> intros) {
        Conversation told = new Conversation() {
            private static final long serialVersionUID = -8121450999408922593L;
            
            @Override
            public boolean condition(Character character, Player player) {
                String questName = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                
                return eligible(player, questName, BeatUpMarekDravis.States.TOLD_BUCK_GORDON_DEAD);
            }
        };
        
        told.greet(String.format("%s! There you are, good to see you again! We found the " +
            	"little bastard! %s is in the jail now, awaiting execution for his crimes. " +
            	"Thanks for your help, son. Go and see him if you wish, the jail is over there, " +
            	"by the church. Can't miss it.", 
            	playerName, mareks));
        
        Conversation will = new Conversation() {
            private static final long serialVersionUID = 6166181701394168848L;

            @Override
            public void action(Character character, Player player) {
                Quest quest = player.getQuest(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                
                quest.update(String.format("When I got back to %s his men had already found %s. " +
                        "Didn't take long... He told me I could go and talk to %s in the jail " +
                        "if I wanted to. He said I should go to the church to find it.",
                        johns, mareks, mareks));
                
                quest.setState(BeatUpMarekDravis.States.JOHN_COUGHT_MAREK);
                
                Character marek = Area.getGlobalCharacter(CharacterKeys.BlackWater.MAREK_DRAVIS);
                
                marek.setDiscovered(true);
                marek.setPlace(Area.getGlobalPlace(PlaceKeys.BlackWater.THE_JAIL_DUNGEON));
                
                Area.allPlaces.get(PlaceKeys.BlackWater.THE_JAIL).setDiscovered(true);
            }
        };
        
        will.setReply("Will do, take care.");
        will.end("You too.");
        
        told.addReply(will);
        
        intros.add(told);
    }
}
