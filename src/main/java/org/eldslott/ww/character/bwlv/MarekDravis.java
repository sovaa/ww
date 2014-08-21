package org.eldslott.ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterKeys;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.bwlv.marek.MarekInJail;
import org.eldslott.ww.character.bwlv.marek.MarekInJailWaiting;
import org.eldslott.ww.character.bwlv.marek.MarekIntro;
import org.eldslott.ww.generator.ItemGenerator;
import org.eldslott.ww.item.Gold;
import org.eldslott.ww.item.StoneInsignia;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Inventory;
import org.eldslott.ww.player.Player;

public class MarekDravis extends Character {
    private static final long serialVersionUID = 5944139697870049625L;
    
    public MarekDravis(Player player, Place place) {
        setPlace(place);
        
        id = CharacterKeys.BlackWater.MAREK_DRAVIS;
    	name = CharacterNames.BlackWater.MAREK_DRAVIS;
        
        description = 
            "Marek... At first sight you get a feeling " +
            "you will hate him before long. At second sight you decide that " +
            "your instincts were right; you truly, utterly despise him. Your " +
            "nose wrinkles when you get closer to the obviously drunk man. " +
            "He's above average height, strongly build, clad in dirty rags.\n\n" +
            "You look down at your hands and pity your knuckles; they will " +
            "have to touch him...";
        
        inventory = new Inventory();
        inventory.addItem(new Gold(86));
        inventory.addItem(new StoneInsignia());
        inventory.addItem(ItemGenerator.generateChest(3));
    	
    	aliases = new HashSet<String>(Arrays.asList(new String[]{
    	        "marek",
    	        "dravis",
    	        "marek dravis",
    	}));
    	
    	MarekIntro.create(intros);
    	MarekInJail.create(intros);
    	MarekInJailWaiting.create(intros);
    }
    
    @Override
    public void fight(Player player) {
    }

    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.MAREK_DRAVIS);
    }
}
