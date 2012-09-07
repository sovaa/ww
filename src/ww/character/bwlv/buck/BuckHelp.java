package ww.character.bwlv.buck;

import ww.character.Character;
import ww.character.CharacterKeys;
import ww.character.Conversation;
import ww.location.Area;
import ww.location.PlaceKeys;
import ww.player.Player;
import ww.quests.Quest;

public class BuckHelp {
    public static final void create(Conversation intro) {
        Conversation help = new Conversation() {
            private static final long serialVersionUID = -1342811654181163864L;

            @Override
            public boolean condition(Character character, Player player) {
                return !eligible(player, Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
            }
        };

        intro.addReply(help);
        help.setReply("Need a hand?");
        
        help.greet("Actually, yes. A local drunk, Marek Dravis, owe me some money, and he's " +
                "reluctant to pay me back. Can you go over to his house and... convince him " +
                "for me? I would do it myself, but these other drunks would jump on the mead " +
                "as soon as I turn my back on them. So, what do you say? I'll teach you a thing or " +
                "two about sword-play if you do it, you look pretty green. Might give you some gold " +
                "for it to if you do a nice job.");

        {
            Conversation accept = new Conversation() {
                private static final long serialVersionUID = -3953280073717690102L;

                @Override
                public void action(Character character, Player player) {
                    player.addQuest(Quest.getQuest(Quest.BUCK_BEAT_UP_MAREK_DRAVIS));
                    Area.getGlobalCharacter(CharacterKeys.BlackWater.MAREK_DRAVIS).setDiscovered(true);
                    Area.getGlobalPlace(PlaceKeys.BlackWater.MAREK_DRAVIS_HOUSE).setDiscovered(true);
                }
            };
            
            accept.setReply("Sure, I'll teach him a lesson.");
            accept.end("Don't kill him, just break a couple of bones to scare him. His house is in " +
                    "the outskirts, just look for the most crappy house you see and it's his.");
            
            help.addReply(accept);
        }
        
        help.reply("I don't think so.", intro);
        
        {
            Conversation never = help.reply("Never. See you around.");
            never.end("Suit your self.");
        }
    }
}
