package ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import ww.action.Trade;
import ww.character.Character;
import ww.character.CharacterKeys;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.item.Gold;
import ww.location.Place;
import ww.player.Player;

public class Gunnar extends Character {
    private static final long serialVersionUID = 7795442531902454900L;

    public Gunnar(Place place) {
        setPlace(place);
        
        id = CharacterKeys.BlackWater.GUNNAR;
        name = CharacterNames.BlackWater.GUNNAR;
        description = "The towns only blacksmith.";
        
        discovered = true;
        tradable = true;
        
        inventory.addItem(new Gold(592));
        
        aliases = new HashSet<String>(Arrays.asList(new String[]{
                "gunnar",
        }));
        
        Conversation intro = new Conversation();
        intro.greet("Need something repaired?");
        
        {
            Conversation gamble = new Conversation() {
                private static final long serialVersionUID = -2813268087549787944L;

                @Override
                public void action(Character character, Player player) {
                    Trade.repair(player, character);
                }
            };
            
            gamble.setReply("Repair");
            gamble.end("Until next time.");
            intro.addReply(gamble);
        }
        
        {
            Conversation bye = intro.reply("Bye");
            bye.end("Have a good one.");
        }
        
        intros.add(intro);
    }
    
    @Override
    public void fight(Player player) {
    }

    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.GUNNAR);
    }
}
