package ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import ww.character.Character;
import ww.character.CharacterKeys;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.character.InventoryUpdater;
import ww.item.Gold;
import ww.location.Place;
import ww.player.Player;

public class Geed extends Character {
    private static final long serialVersionUID = 2461805498418657270L;
    
    public Geed(Player player, Place place) {
        setPlace(place);
        
        new Thread(new InventoryUpdater(inventory, player, this)).start();
        
        id = CharacterKeys.BlackWater.GEED;
        name = CharacterNames.BlackWater.GEED;
        description = "A greedy merchant.";
        
        discovered = true;
        tradable = true;
        gamble = true;
        
        inventory.addItem(new Gold(592));
        
        aliases = new HashSet<String>(Arrays.asList(new String[]{
                "geed",
                "greed",
        }));
        
        Conversation intro = new Conversation();
        intro.greet("Greeting stranger! Wanna try your luck?");
        
        {
            Conversation gamble = intro.reply("Gamble");
            gamble.end("Hehe, good, good! Trade with me and we'll get started...");
        }
        
        intros.add(intro);
    }
    
    @Override
    public void fight(Player player) {
    }
    
    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.GEED);
    }
}
