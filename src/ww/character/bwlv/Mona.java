package ww.character.bwlv;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;

import ww.character.Character;
import ww.character.CharacterKeys;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.generator.ItemGenerator;
import ww.item.Gold;
import ww.location.Area;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.player.Player;
import ww.quests.Quest;

public class Mona extends Character implements Serializable {
    private static final long serialVersionUID = 8265776497490151243L;
    
    public Mona(Place place) {
        setPlace(place);
        
        id = CharacterKeys.BlackWater.MONA;
    	name = CharacterNames.BlackWater.MONA;
    	description = "A busty young lass. Tall, blonde and fair.";
    	
        discovered = true;
    	tradable = true;
    	
        inventory.equip(ItemGenerator.generateChest(2));
        inventory.equip(ItemGenerator.generateFeet(2));
        inventory.equip(ItemGenerator.generateLegs(2));
        
        inventory.addItem(new Gold(63));
        
        aliases = new HashSet<String>(Arrays.asList(new String[]{
                "mona",
                "sexy mona",
                "hot stuff",
                "babe"
        }));
    	
    	Conversation intro = new Conversation();
    	intro.greet("Greetings handsome traveller. Would you be so kind to help me with a task far from suitable to a lady?");
    	
    	intros.add(intro);
    	
    	{
    		Conversation decline = intro.reply("I really dont want to");
    		decline.greet("Well thats not very kind. Perhaps you should have a word with Buck, the barkeep, instead.");
    		
    		decline.reply("I will.", intro);
    	}
    	
    	{
    		Conversation help = intro.reply("Perhaps. What do you have in mind?");
    		
    		help.greet("I've got my hands full here at the moment and we've got ourselves a small problem concerning rodents. " +
    				"The basement flooded a few days back and with most of the food all wet and smelly it have attracted some " +
    				"unplesant rats. They must be taken care of before they start nesting in the whole tavern!\n\n " +
    				"Would you be willing to help me out? I would put in a good word to the owner, Buck.");

    		{
    			Conversation accept = new Conversation() {
                    private static final long serialVersionUID = 2199152376626209497L;

                    @Override
    				public void action(Character character, Player player) {
    					player.addQuest(Quest.getQuest(Quest.RAT_INFESTATION));
                        Area.getGlobalCharacter(CharacterKeys.BlackWater.RAT_PACK).setDiscovered(true);
    					Area.getGlobalPlace(PlaceKeys.BlackWater.THE_BUSTY_MAID_INN_BASEMENT).setDiscovered(true);
    				}
    			};
    			
    			accept.setReply("Of course.");
    			accept.end("Oh thank you so much. Be careful down there!");
    			
    			help.addReply(accept);
    		}
    		
    		help.reply("I don't have the time.", intro);
    		
    		{
    			Conversation never = help.reply("Not a chance");
    			never.end("Can't expect that much from a black man I guess.");
    		}
    	}
    	
    	{
    		Conversation bye = intro.reply("Bye");
    		bye.end("See you around.");
    	}
    }

    @Override
    public void fight(Player player) {
    }

    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.MONA);
    }
}
