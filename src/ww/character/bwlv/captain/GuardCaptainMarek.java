package ww.character.bwlv.captain;

import ww.character.Character;
import ww.character.CharacterKeys;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.location.Area;
import ww.location.PlaceKeys;
import ww.location.PlaceNames;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class GuardCaptainMarek {
    public static final void create(Conversation intro) {
        Conversation marek = new Conversation() {
            private static final long serialVersionUID = -173216733469189689L;

            @Override
            public boolean condition(Character character, Player player) {
                return eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, BeatUpMarekDravis.States.ACCEPTED);
            }
        };
         
        marek.setReply("Do you know about Marek Dravis?");
        marek.greet("What about him?");
         
        {
            Conversation trouble = new Conversation() {
                private static final long serialVersionUID = -7063649730951327056L;

                @Override
                public void action(Character character, Player player) {
                    Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                    
                    String john = CharacterNames.BlackWater.CAPTAIN_JOHN;
                    String gordon = CharacterNames.BlackWater.GUARD_GORDON;
                    String marek = CharacterNames.BlackWater.MAREK_DRAVIS;
                    
                    quest.update(john + " sent " + gordon + " to investigate " + marek + ". Maybe I should follow him...");
                    quest.setState(BeatUpMarekDravis.States.JOHN_SENT_INVESTIGATOR);
                    
                    Area.getGlobalCharacter(CharacterKeys.BlackWater.MAREK_DRAVIS).setDiscovered(false);
                    
                    Character gordonCharacter = 
                        Area.getGlobalCharacter(CharacterKeys.BlackWater.GUARD_GORDON);
                    
                    gordonCharacter.setPlace(Area.getGlobalPlace(PlaceKeys.BlackWater.MAREK_DRAVIS_HOUSE));
                    gordonCharacter.setDead(true);
               }
            };
             
            trouble.setReply("I heard " + CharacterNames.BlackWater.BUCK + " the innkeeper is " +
                    "having some trouble with him.");
            
            trouble.end("I will send someone to investigate.");
            
            marek.addReply(trouble);
        }
         
        {
            Conversation kill = new Conversation() {
                private static final long serialVersionUID = -634663754588267191L;

            @Override
               public void action(Character character, Player player) {
                   Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                    
                   quest.update(CharacterNames.BlackWater.CAPTAIN_JOHN + " wants to meet me at " + 
                           PlaceNames.BlackWater.THE_BUSTY_MAID_INN + ".");
                   
                   quest.setState(BeatUpMarekDravis.States.MEET_JOHN_AT_INN);
                    
                   character.setPlace(Area.getGlobalPlace(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN));
               }
            };
             
            kill.setReply("Buck da man is trying to have him killeded");
            kill.end("Meet me at " + PlaceNames.BlackWater.THE_BUSTY_MAID_INN + 
                    " and we will have a word with " + CharacterNames.BlackWater.BUCK + ".");
             
            marek.addReply(kill);
        }
         
        intro.addReply(marek);
    }
}
