package org.eldslott.ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.character.CharacterKeys;
import org.eldslott.ww.character.CharacterNames;
import org.eldslott.ww.character.Conversation;
import org.eldslott.ww.item.Gold;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Player;

public class GuardGordon extends Character {
    private static final long serialVersionUID = 7691116817786114260L;
    
    public GuardGordon(Place place) {
        setPlace(place);
        
        id = CharacterKeys.BlackWater.GUARD_GORDON;
        name = CharacterNames.BlackWater.GUARD_GORDON;
        description = "A young member of the Town Guard.";
        
        discovered = false;
        
        inventory.addItem(new Gold(43));
        
        aliases = new HashSet<String>(Arrays.asList(new String[] {
                "gordon",
                "guard gordon",
                "town guard gordon"
        }));
        
        Conversation intro = new Conversation();
        intro.greet("Get lost!");
        
        intros.add(intro);
    }

    @Override
    public void fight(Player player) {
    }

    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.GUARD_GORDON);
    }
}
