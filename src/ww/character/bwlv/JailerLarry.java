package ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import ww.Skill;
import ww.character.Character;
import ww.character.CharacterKeys;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.item.BlackWaterJailDungeonKey;
import ww.item.Cheese;
import ww.item.Gold;
import ww.location.Area;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class JailerLarry extends Character {
    private static final long serialVersionUID = 8487045786961679521L;

    public JailerLarry(Place place) {
        setPlace(place);
        
        id = CharacterKeys.BlackWater.JAILER_LARRY;
        name = CharacterNames.BlackWater.JAILER_LARRY;
        description = "The towns jailer.";
        
        discovered = true;
        
        inventory.addItem(new Gold(7));
        inventory.addItem(new Cheese());
        inventory.addItem(new BlackWaterJailDungeonKey());
        
        aliases = new HashSet<String>(Arrays.asList(new String[]{
                "larry", "larry the jailer", "jailer larry",
        }));
        
        Conversation intro = new Conversation();
        intro.greet("*Zzz Zzz Zzz...*");
        intro.description(name + " is sitting on a footstool, sleeping, behind his desk.");
        
        {
            Conversation clear = intro.reply("<Clear your throat>");
            
            clear.greet("*Yawn* Who're you? Get out of my jail!");
        
            {
                Conversation persuade = new Conversation() {
                    private static final long serialVersionUID = -8417184732974287112L;
    
                    @Override
                    public boolean condition(Character character, Player player) {
                        Skill speech = player.getSkill(Skill.SPEECH);
                        
                        if (speech == null) {
                            return false;
                        }
                        
                        if (speech.getValue() < 2) {
                            return false;
                        }
                        
                        return true;
                    }
                    
                    @Override
                    public void action(Character character, Player player) {
                        Area.getGlobalPlace(PlaceKeys.BlackWater.THE_JAIL_DUNGEON).setLocked(false);
                        
                        Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);

                        boolean marekFork = false;
                        
                        final String buck = CharacterNames.BlackWater.BUCK;
                        final String larry = CharacterNames.BlackWater.JAILER_LARRY;
                        final String mareks = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
                        
                        String characterName = buck;
                        
                        String questName = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                        if (eligible(player, questName, BeatUpMarekDravis.States.JOHN_COUGHT_MAREK)) {
                            marekFork = true;
                            characterName = mareks;
                        }
                        
                        if (marekFork) {
                            quest.setState(BeatUpMarekDravis.States.GOT_THROUGH_JAIL_DUNGEON_DOOR_MAREK);
                        }
                        else {
                            quest.setState(BeatUpMarekDravis.States.GOT_THROUGH_JAIL_DUNGEON_DOOR_BUCK);
                        }

                        quest.update(String.format("I managed to persuade %s to unlock the door " +
                                "to the dungeon and let me through. Now I can speak to %s.", 
                                larry, characterName));
                    }
                };
                
                persuade.setReply("(Skill: " + Skill.SPEECH + ") I'm the new health inspector. Can you " +
                		"open the dungeon door so I can check on the prisoners conditions?");
                
                persuade.greet("Oh, right away mister! I assure you, the prisoners are as healthy as they can be!");
                persuade.description(name + " unlocks the door to the dungeon.");
                
                clear.addReply(persuade);
            }
            
            {
                Conversation speak = clear.reply("I'm " + Player.NAME + ", can I go into the dungeon and speak to " + 
                        CharacterNames.BlackWater.BUCK + "?");
                
                speak.end("No! No one goes through unless they have the key! And " +
                		"only I have the key, so only I can go through! Now scram!");
            }
        }
        
        {
            Conversation shout = new Conversation() {
                private static final long serialVersionUID = 7554434595030840631L;

                @Override
                public void action(Character character, Player player) {
                    character.setDead(true);
                    character.setDiscovered(false);
                    
                    boolean marekFork = false;
                    
                    String questName = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;
                    if (eligible(player, questName, BeatUpMarekDravis.States.JOHN_COUGHT_MAREK)) {
                        marekFork = true;
                    }
                    
                    Quest quest = player.getQuests().get(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
                    
                    String larry = CharacterNames.BlackWater.JAILER_LARRY;
                    String buck = CharacterNames.BlackWater.BUCK;
                    String marek = CharacterNames.BlackWater.MAREK_DRAVIS_SHORT;
                    
                    String name = buck;
                    
                    if (marekFork) {
                        name = marek;
                    }
                    
                    quest.update("When I got to the jail where " + name + " was being held I accidentally got " +
                            larry + " killed... Is this normal? Anyway, I bet it's a lot easier to get " +
                            "to " + name + " now, hah!");
                    
                    if (marekFork) {
                        quest.setState(BeatUpMarekDravis.States.GOT_THROUGH_JAIL_DUNGEON_DOOR_MAREK);
                    }
                    else {
                        quest.setState(BeatUpMarekDravis.States.GOT_THROUGH_JAIL_DUNGEON_DOOR_BUCK);
                    }
                    
                    player.increaseSkill(player.getSkill(Skill.FINESS));
                }
            };
            
            shout.setReply("<Shout as loud as possible>");
            shout.description(name + " wakes flailing with his arms, making his " +
            		"chair tip over backwards. Little did he know that he'd put his sword " +
            		"hilt-down in a bucked behind him. " + name + " is impaled on his sword " +
            		"and dies a painful death, gurgling a curse at you.");
            
            intro.addReply(shout);
        }
        
        {
            intro.reply("<Leave>");
        }
        
        
        intros.add(intro);
    }
    
    @Override
    public void fight(Player player) {
    }

    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.JAILER_LARRY);
    }
}
