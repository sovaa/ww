package org.eldslott.ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterKeys;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.character.InventoryUpdater;
import org.eldslott.ww.item.Gold;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Player;

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
