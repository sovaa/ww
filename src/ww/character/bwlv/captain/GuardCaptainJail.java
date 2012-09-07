package ww.character.bwlv.captain;

import ww.character.Character;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.location.Area;
import ww.location.PlaceKeys;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class GuardCaptainJail {
    private static final String johnName = CharacterNames.BlackWater.CAPTAIN_JOHN;
    private static final String buckName = CharacterNames.BlackWater.BUCK;
    
    public static final void create(Conversation intro) {
        Conversation jail = new Conversation() {
            private static final long serialVersionUID = 7519565180511299300L;
            
            @Override
            public boolean condition(Character character, Player player) {
                return eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, 
                        BeatUpMarekDravis.States.TOLD_JOHN_BUCK_TRIED_TO_KILL_MAREK);
            }
            
            @Override
            public void action(Character character, Player player) {
                Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                quest.setState(BeatUpMarekDravis.States.JOHN_TOLD_LOCATION_OF_JAIL);
                
                quest.update(johnName + " told me the location of the town jail. I should " + 
                        "go there and have a talk with Buck. It seems only fair, since I put " + 
                        "him there...");
                
                Area.allPlaces.get(PlaceKeys.BlackWater.THE_JAIL).setDiscovered(true);
            }
        };
        
        jail.setReply("Can you tell me where the jail is? I would like to talk to " + buckName + "...");
        jail.end("Oh! Well why didn't you say so? It's over at the church. You can see " +
                "the church spire from here, and when you get there you'll see the jail. " + 
                "The jailer's name is " + CharacterNames.BlackWater.JAILER_LARRY + ". Take " + 
                "it easy with him, he's not the brightest of fellows, and he's very proud of " + 
                "his job.");
        
        intro.addReply(jail);
    }
}
