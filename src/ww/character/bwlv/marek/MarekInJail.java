package ww.character.bwlv.marek;

import java.util.List;

import ww.character.Character;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.location.Area;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class MarekInJail {
    private static final String buck = CharacterNames.BlackWater.BUCK;
    private static final String john = CharacterNames.BlackWater.CAPTAIN_JOHN;
    private static final String mareks = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
    
    public static final void create(List<Conversation> intros) {
        Conversation jail = new Conversation() {
            private static final long serialVersionUID = -2056349213255034658L;
            
            @Override
            public boolean condition(Character character, Player player) {
                String questName = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                
                return eligible(player, questName, BeatUpMarekDravis.States.GOT_THROUGH_JAIL_DUNGEON_DOOR_MAREK);
            }
        };
        
        jail.greet("Who're you?");
        
        {
            Conversation talk = jail.reply(buck + " sent me.");
            
            talk.greet(buck + "? Great... So he's the one who got me in this mess? When I " +
            		"get out of here I'm gonna cut him from ear to ear! It wasn't my fault " +
            		"that stupid guard got killed... He angered me, is all! Shouldn't have " +
            		"gotten in the way, I say.");
            
            {
                Conversation getout = talk.reply(String.format("I don't think you're getting out of here anytime soon. %s " +
                        "said you're gonna hang for your crimes?", john));
                
                getout.greet("What!? I just thought he was gonna make me rot for a while! " +
                		"Listen kid, you gotta get me out of here.");
                
                {
                    Conversation me = getout.reply("What's in it for me?");
                    
                    me.greet(String.format("I promise, I'll make it worth your while. %s's been up my arse " +
                    		"about this old fucking piece of shit junk I have. Small polished " +
                    		"stone of some kind with some weird markings on it. I didn't want " +
                    		"to sell it to him since it's been in my family for generations. " +
                    		"Have no fucking idea what it is. But mostly I just hate the shit " +
                    		"out of %s, and that's why I don't want him to have it. I'll give it " +
                    		"to you though, if you just get me out of here. What do you say?",
                    		buck, buck));
                    
                    {
                        Conversation how = new Conversation() {
                            private static final long serialVersionUID = 1497481310250708155L;
                            
                            @Override
                            public void action(Character character, Player player) {
                                Quest quest = player.getQuest(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                                
                                quest.setState(BeatUpMarekDravis.States.AGREED_TO_BUST_OUT_MAREK);
                                
                                quest.update(String.format("I agreed to try to bust out %s from jail. " +
                                		"In exchange he would give me some sort of trinket, which " +
                                		"apparently is what %s was after, and not some kind of tab quarrel. " +
                                		"%s told me to try to kick in the gratings on the ground level on " +
                                		"the back of the jail.", 
                                        mareks, mareks, mareks));
                                
                                Place alley = Area.allPlaces.get(PlaceKeys.BlackWater.THE_JAIL_ALLEY);
                                
                                alley.setDiscovered(true);
                            }
                        };
                        
                        how.setReply("How would I be able to get you out of here?");
                        
                        how.greet("You see those gratings up there? They look pretty darn loose. " +
                        		"I bet you could kick it in from the outside. It's too high for me " +
                        		"to reach from here. It's on the ground level outside. And bring some " +
                        		"rope so I can climb up! There is some in my house you can take.");
                        
                        how.reply("Okay, I'll do it.").end("Hah! That's my boy! Kick it in during the " +
                        		"night so no one will notice.");
                        
                        me.addReply(how);
                    }
                }
            }
        }
        
        jail.reply("Nevermind.").end("Whatever...");
        
        intros.add(jail);
    }
}
