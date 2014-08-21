package org.eldslott.ww.character.bwlv.captain;

import java.util.List;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterKeys;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.location.PlaceNames;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class GuardCaptainMeet {
    private static final String johnName = CharacterNames.BlackWater.CAPTAIN_JOHN;
    private static final String buckName = CharacterNames.BlackWater.BUCK;
    private static final String marekName = CharacterNames.BlackWater.MAREK_DRAVIS;
    private static final String squareName = PlaceNames.BlackWater.THE_TOWN_SQUARE;
    private static final String playerName = Player.NAME;
    
    public static void create(List<Conversation> intros) {
        Conversation meet = new Conversation() {
            private static final long serialVersionUID = -1342811654181163864L;

            @Override
            public boolean condition(Character character, Player player) {
                return eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, BeatUpMarekDravis.States.MEET_JOHN_AT_INN);
            }
        };
        
        meet.greet("Ready to talk to buck?");
        intros.add(meet);
        
        {
            Conversation johnInn = new Conversation() {
                private static final long serialVersionUID = 3828884960121522344L;
                
                @Override
                public boolean condition(Character character, Player player) {
                    return eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS, BeatUpMarekDravis.States.MEET_JOHN_AT_INN);
                }
            };
             
            johnInn.setReply("Yes.");
             
            johnInn.monologue(johnName, "Mr. Buck, may we have a word with you?");
            johnInn.monologue(buckName, "Cap'n.");
            johnInn.monologue(johnName, "I've come to understand that you're trying to have " + marekName + " killed...");
            johnInn.monologue(buckName, "What!? No! I asked " + playerName + " here to... soften him up a little!");
            johnInn.monologue(johnName, playerName + ", is this true? Did he or did he not want you to kill " + marekName + "?");
             
            {
                Conversation kill = new Conversation() {
                    private static final long serialVersionUID = -3570399497504315325L;

                    @Override
                    public void action(Character character, Player player) {
                        Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                        
                        quest.setState(BeatUpMarekDravis.States.TOLD_JOHN_BUCK_TRIED_TO_KILL_MAREK);
                        quest.update("You told " + johnName + " that " + buckName + " tried to kill " +
                                marekName + ", and " + johnName + " has now taken " + buckName + 
                                " to the jail.");
                        
                        Character buck = Area.allCharacters.get(CharacterKeys.BlackWater.BUCK);
                        Character john = Area.allCharacters.get(CharacterKeys.BlackWater.CAPTAIN_JOHN);
                        
                        buck.setPlace(Area.allPlaces.get(PlaceKeys.BlackWater.THE_JAIL_DUNGEON));
                        john.setPlace(Area.allPlaces.get(PlaceKeys.BlackWater.THE_TOWN_SQUARE));
                    }
                };
                
                kill.setReply("Yes, he did.");
                kill.end("Okay. I'm sorry " + CharacterNames.BlackWater.BUCK + ", but I'll have to take " +
                        "you to the jail for now to sort this thing through. If it's just a " + 
                        "misunderstanding you'll be out in notime, but I'm sure you understand... " +
                        playerName + ", if you need to talk to me again I'll be at " + squareName + 
                        ". Good day, now.");
                
                johnInn.addReply(kill);
            }
            
            {
                Conversation hurt = johnInn.reply("No... he only wanted me to hurt him.");
                hurt.end("Well, fuck off then!");
            }
            
            meet.addReply(johnInn);
        }
        
        {
            Conversation no = meet.reply("No.");
            no.end("Come see me when you are. Time is of the essence.");
        }
    }
}
