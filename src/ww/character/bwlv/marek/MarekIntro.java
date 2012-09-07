package ww.character.bwlv.marek;

import java.util.List;

import ww.Skill;
import ww.character.Character;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class MarekIntro {
    private static final String marek = CharacterNames.BlackWater.MAREK_DRAVIS;
    
    public static final void create(List<Conversation> intros) {
        Conversation intro = new Conversation() {
            private static final long serialVersionUID = -2008605588037993590L;

            @Override
            public boolean condition(Character character, Player player) {
                String questKey = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                String state = BeatUpMarekDravis.States.ACCEPTED;
                
                return eligible(player, questKey, state);
            }
        };
        
        intro.greet("Who... who are you? *hic*");
        
        intros.add(intro);
        
        {
            Conversation talk = intro.reply("I'm " + Player.NAME + "!");
            talk.greet("So? Get out of my house before I throw you out!");
            talk.reply("Okay, okay, I'll go! No need to get so upset...").end("*grumble*");
            
            {
                Conversation stay = talk.reply("I'm here on a request by Buck. You owe him money.");
                stay.greet("This is your last warning. Get out and never show your face again.");
                stay.reply("Okay, you've made your point, I guess... Sorry to trouble you, sir.").end("Wuss...");
                
                {
                    Conversation fight = stay.reply("I'm not leaving until either you've paid or " +
                        	"half of your bones are broken.");
                    
                    fight.greet("Tough guy, ey? Let's see how good you are in a fight!");
                    fight.hostile(true);
                    fight.description("Marek rises from his chair and reaches for his sword.");
                }
            }
        }
        
        {
            Conversation talk = intro.reply("Who are you?");
            talk.greet("My name is " + marek + ", what the fuck do you want?");
            talk.reply("Gee sorry, mister! Gosh, I think I'll just leave...").end("Don't come back, you hear me?");
            
            {
                Conversation fight = talk.reply("I'm here for Buck's money or some ass wooping, what'll it be?");
                fight.end("I'll smash you to bits, you punk!");
                fight.description(marek + " rises quickly and draws his sword. He looks mad. Pretty mad indeed, yes...");
                fight.hostile(true);
            }
        }
        
        {
            Conversation fight = intro.reply("You're dead!");
            fight.end("Oh, you just met your match, boy!");
            fight.description(marek + " draws his sword and enters a fighting stance.");
            fight.hostile(true);
        }
        
        {
            Conversation wit = new Conversation() {
                private static final long serialVersionUID = 7811329285063985474L;

                @Override
                public boolean condition(Character character, Player player) {
                    Skill skill = player.getSkill(Skill.WIT);
                    
                    if (skill != null) {
                        return true;
                    }
                    
                    return false;
                }

                @Override
                public void greeting(Character character, Player player) {
                    player.getSkill(Skill.WIT).increase();
                    character.setDiscovered(false);
                    
                    Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                    
                    quest.setState(BeatUpMarekDravis.States.KILLED_MAREK);
                    quest.update("Upon reaching Marek's house, I opened the conversation " +
                            "with a quick joke, hoping to ease the tension a bit. Unfortunately " +
                            "Marek choked on his food while laughing and died because of it. " +
                            "Buck will not be glad...");
                    
                    character.setDead(true);
                }
            };
            
            wit.setReply("(Skill: WIT) The towns new matchmaker, and your face has a date with my knuckles!");
            wit.end("Hahahah-- *cough* *cough* *wheeze*");
            
            wit.description(CharacterNames.BlackWater.MAREK_DRAVIS + " chokes on his food while " +
                	"laughing and dies. you have some explaining to do to Buck...");
            
            intro.addReply(wit);
        }
        
        intro.reply("Nevermind! Goodbye, sir.").end("Whatever...");
    }
}
