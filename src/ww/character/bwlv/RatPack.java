package ww.character.bwlv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import ww.Encounter;
import ww.Monster;
import ww.character.Character;
import ww.character.CharacterKeys;
import ww.location.Place;
import ww.player.Player;

@Deprecated
public class RatPack extends Character {
    private static final long serialVersionUID = -2492628289565515359L;
	
	private List<Monster> monsters;
	
	public RatPack(Place place) {
	    setPlace(place);
		reset();
	}
	
	private void reset() {
		monsters = new ArrayList<Monster>();
		name = "A rat pack";
		description = "A couple of big rats. They look hungry.";
		hostile = true;
		
        aliases = new HashSet<String>(Arrays.asList(new String[]{
                "ratpack",
                "rat pack",
                "rat",
                "rats",
                "pack"
        }));
		
		for (int i = 0; i < 7; i++) {
			Monster rat = new Monster();
			rat.createRat();
			
			monsters.add(rat);
		}
	}
	
	@Override
	public void fight(Player player) {
		try {
			Encounter.monster(player, monsters);
			reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.RAT_PACK);
    }
}
