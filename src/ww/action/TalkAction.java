package ww.action;

import java.util.Map;

import ww.character.Character;
import ww.location.Place;
import ww.player.Player;
import ww.util.Common;
import ww.util.Helper;

public class TalkAction extends Common implements Action {
    private static final long serialVersionUID = 1379148019484947800L;

    public static boolean action(Player player, String what) {
        String charKey = null;
        if (what.split(" ").length > 1) {
            // remove "visit " from "visit <place>", so we only get "<place>" in "what"
            charKey = what.substring(what.split(" ")[0].length()).trim();
            
            if (charKey.startsWith("to ")) {
                charKey = charKey.substring(charKey.split(" ")[0].length()).trim();
            }
        }
        
        Place place = player.getPlace();
        Map<String, Character> keys = place.getDiscoveredCharacters();
        
        if (keys == null || keys.size() == 0) {
            printbig("There's no one here to talk to...");
            return true;
        }
        
        if (charKey == null) {
            printbig("There are a few people you can talk to here: ");
            
            
            for (String key : keys.keySet()) {
                println(" * " + keys.get(key).getName());
            }
            
            println();
            
            return true;
        }
        
        Map<String, Character> aliases = Helper.getAliasCharacterAliasMap(keys);
        
        if (!aliases.containsKey(charKey)) {
            printbig("There's no one here called '" + charKey + "'.");
            return true;
        }
        
        Character character = aliases.get(charKey);
        character.beginConversation(player);
        
        return true;
    }
}
