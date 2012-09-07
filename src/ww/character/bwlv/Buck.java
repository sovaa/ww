package ww.character.bwlv;

import java.util.Arrays;
import java.util.HashSet;

import ww.character.Character;
import ww.character.CharacterKeys;
import ww.character.CharacterNames;
import ww.character.Conversation;
import ww.character.bwlv.buck.BuckGordonDead;
import ww.character.bwlv.buck.BuckHelp;
import ww.character.bwlv.buck.BuckJail;
import ww.character.bwlv.buck.BuckLetGo;
import ww.character.bwlv.buck.BuckLetOut;
import ww.character.bwlv.buck.BuckMarek;
import ww.character.bwlv.buck.BuckMeetJohn;
import ww.character.bwlv.buck.BuckStay;
import ww.generator.ItemGenerator;
import ww.item.Gold;
import ww.location.Place;
import ww.location.PlaceKeys;
import ww.player.Player;
import ww.quests.Quest;
import ww.quests.bwlv.BeatUpMarekDravis;

public class Buck extends Character {
    private static final long serialVersionUID = -309133046957338301L;
	
	public Buck(Place place) {
	    setPlace(place);
		
	    id = CharacterKeys.BlackWater.BUCK;
	    name = CharacterNames.BlackWater.BUCK;
        description = "A strongly build man, well past his fiftith " +
                "winter, with a grim, weather-worn face.";
        
		discovered = true;
		tradable = true;
		
		inventory.addItem(new Gold(180));
        inventory.addItem(ItemGenerator.generateChest(6));
        
        aliases = new HashSet<String>(Arrays.asList(new String[]{
                "buck",
        }));
        
        Conversation intro = new Conversation() {
            private static final long serialVersionUID = -5571604815668735094L;

            @Override
            public boolean condition(Character character, Player player) {
                String questKey = Quest.BUCK_BEAT_UP_MAREK_DRAVIS;

                boolean johnInn = eligible(player, questKey, BeatUpMarekDravis.States.MEET_JOHN_AT_INN);
                boolean atJail = character.getPlace().getMainKey().equals(PlaceKeys.BlackWater.THE_JAIL_DUNGEON);
                boolean letout = eligible(player, questKey, BeatUpMarekDravis.States.MADE_JOHN_LET_BUCK_GO);
                boolean gordonDead = eligible(player, questKey, BeatUpMarekDravis.States.TOLD_JOHN_GORDON_DEAD);
                
                String[] alreadyTalkedStates = {
                        BeatUpMarekDravis.States.TOLD_BUCK_YOU_WOULD_MAKE_JOHN_LET_HIM_GO,
                        BeatUpMarekDravis.States.INFO_ARTIFACT_TOLD_BUCK_LET_HIM_OUT,
                        BeatUpMarekDravis.States.LET_HIM_GO_TO_GET_INFORMATION_ABOUT_MAREK,
                        BeatUpMarekDravis.States.INFO_ARTIFACT_TOLD_BUCK_STAY_IN_JAIL,
                };
                
                boolean alreadyTalked = eligible(player, questKey, alreadyTalkedStates);
                
                if (atJail || johnInn || alreadyTalked || letout || gordonDead) {
                    return false;
                }
                
                return true;
            }
        };
        
        intro.greet("Greetings. Something to drink and a hot meal?");
        intros.add(intro);

        BuckGordonDead.create(intros);
        BuckStay.create(intros);
        BuckLetOut.create(intros);
        BuckMeetJohn.create(intros);
        BuckJail.create(intros);
        BuckHelp.create(intro);
        BuckMarek.create(intro);
        BuckLetGo.create(intros);
		
		{
			Conversation gossip = intro.reply("Gossip");
			gossip.greet("I don't do gossip. Speak with the old ladies in the marketplace for that.");
			gossip.reply("Will do", intro);
		}
		
		{
			Conversation bye = intro.reply("Bye");
			bye.end("Until next time.");
		}
	}

	@Override
	public void fight(Player player) {
	}

    @Override
    public void setPlace(Place place) {
        super.setPlace(place, CharacterKeys.BlackWater.BUCK);
    }
}
