package org.eldslott.ww.character.bwlv.buck;

import java.util.List;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.PlaceKeys;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.quests.bwlv.BeatUpMarekDravis;

public class BuckJail {
    public static final void create(List<Conversation> intros) {
        Conversation introJail = new Conversation() {
            private static final long serialVersionUID = -5888299380622931047L;

            @Override
            public boolean condition(Character character, Player player) {
                return character.getPlace().equals(Area.getGlobalPlace(PlaceKeys.BlackWater.THE_JAIL_DUNGEON));
            }
        };
        
        introJail.greet(Player.NAME + "... You backstabbing little snot! What are you doing here?");
        
        final String marek = CharacterNames.BlackWater.MAREK_DRAVIS;
        final String john = CharacterNames.BlackWater.CAPTAIN_JOHN;
        final String buck = CharacterNames.BlackWater.BUCK;
        
        {
            Conversation sorry = introJail.reply("It's all a terrible misunderstanding! I never meant to " +
                    "have you put in jail, I don't know why I said that to " + john + "...");
            
            sorry.greet("Then go tell " + john + " that you were wrong! Get me out of here!");
            
            {
                Conversation yes = new Conversation() {
                    private static final long serialVersionUID = -1133230750179282625L;

                    @Override
                    public void action(Character character, Player player) {
                        Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                        
                        quest.setState(BeatUpMarekDravis.States.TOLD_BUCK_YOU_WOULD_MAKE_JOHN_LET_HIM_GO);
                        quest.update("I talked to " + buck + " in the dungeon, and I felt that I " +
                                "had done a terrible thing putting him there! I must go tell " + john + " " + 
                                "it's all a big misunderstanding and to let " + buck + "go!");
                    }
                };
                
                yes.setReply("I'll go tell him right away!");
                yes.end("Well hurry then, lad!");
                
                sorry.addReply(yes);
            }
            
            {
                sorry.reply("I don't think so...", introJail);
            }
        }
        
        {
            Conversation ask = introJail.reply("Why are you really after " + marek + "?");
            
            ask.greet("*sigh* If you get me out of here, I'll tell you...");
            
            {
                Conversation tell = ask.reply("Tell me first, then I'll let you out.");
                
                tell.monologue(buck, "You better not betray me again, lad!");
                tell.monologue(buck, "You see... " + marek + " has something. Some old piece of junk. An artifact " +
                        "you might say. I know a buyer who would pay handsomly for it, but " + marek + " doesn't " +
                        "want to sell it. Apparently he got it from some relative a long time ago. I was just trying " +
                        "to make him see what was clearly the right thing to do!");
                
                tell.monologue(buck, "This has gotten out of hand already... If you get " + marek + " sell " +
                        "you the artifact then keep it, I don't care! I don't want anything more to do with it...");
                
                tell.monologue(buck, "Now, will you let me out of here already!");
                
                {
                    Conversation yes = new Conversation() {
                        private static final long serialVersionUID = -1133230750179282625L;

                        @Override
                        public void action(Character character, Player player) {
                            Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                            
                            quest.setState(BeatUpMarekDravis.States.INFO_ARTIFACT_TOLD_BUCK_LET_HIM_OUT);
                            quest.update("I got some interesting information out of " + buck + ". Apparently " +
                                    "he was trying to get some sort of artifact from " + marek + " that " +
                                    "may have some value to it. Might be worth looking into. I should talk " +
                                    john + " and make him let " + buck + " out. Would be nice to keep my word " +
                                    "atleast...");
                        }
                    };
                    
                    yes.setReply("I'll let " + john + " know you're innocent and he'll let you out.");
                    yes.end("Finally... Hurry will you, lad? I hate this place...");
                    
                    tell.addReply(yes);
                }
                
                {
                    Conversation no = new Conversation() {
                        private static final long serialVersionUID = -1133230750179282625L;

                        @Override
                        public void action(Character character, Player player) {
                            Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                            
                            quest.setState(BeatUpMarekDravis.States.INFO_ARTIFACT_TOLD_BUCK_STAY_IN_JAIL);
                            quest.update("I got some interesting information out of " + buck + ". Apparently " +
                                    "he was trying to get some sort of artifact from " + marek + " that " +
                                    "may have some value to it. Might be worth looking into. " + buck + " " +
                                    "can rot in that dungeon for all I care.");
                        }
                    };
                    
                    no.setReply("I think you'd better stay here.");
                    
                    no.end("You trecherous ass! Come a bit closer and I'll punch your lights " +
                            "out! When I get out of here, you're dead! You hear me? DEAD!");
                    
                    tell.addReply(no);
                }
            }
            
            {
                Conversation ok = new Conversation() {
                    private static final long serialVersionUID = -6176506480797615778L;

                    @Override
                    public void action(Character character, Player player) {
                        Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                        
                        quest.setState(BeatUpMarekDravis.States.LET_HIM_GO_TO_GET_INFORMATION_ABOUT_MAREK);
                        quest.update("I talked to " + buck + " in the dungeon, and it seems like there " +
                                "is more to this " + marek + " story than some tab quarrel. " + buck + " " +
                                "said he'd tell me about it if I made " + john + " get him out of there.");
                    }
                };
                
                ok.setReply("Okey, I'll talk to " + john + " and see if he can let you out.");
                ok.end("That's a good lad! Hurry! This place stinks!");
                
                ask.addReply(ok);
            }
        }
        
        {
            Conversation evil = introJail.reply("To watch you rot.");
            
            evil.end("You slimey piece of shit!");
        }
        
        {
            introJail.reply("Nevermind. I'm out of here.");
        }
        
        intros.add(introJail);
    }
}
