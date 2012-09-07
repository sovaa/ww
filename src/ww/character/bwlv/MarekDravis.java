package ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import ww.character.Character;
import ww.character.CharacterKeys;
import ww.character.CharacterNames;
import ww.character.bwlv.marek.MarekInJail;
import ww.character.bwlv.marek.MarekInJailWaiting;
import ww.character.bwlv.marek.MarekIntro;
import ww.generator.ItemGenerator;
import ww.item.Gold;
import ww.item.StoneInsignia;
import ww.location.Place;
import ww.player.Inventory;
import ww.player.Player;

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
