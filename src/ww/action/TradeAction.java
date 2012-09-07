package ww.action;

import java.util.HashMap;
import java.util.Map;

import ww.character.Character;
import ww.player.Player;
import ww.util.Common;

public class TradeAction extends Common implements Action {
    private static final long serialVersionUID = -8736221303463046168L;

    public static boolean action(Player player, String what) {
        String tradeKey = null;
        
        if (what.split(" ").length > 1) {
            // remove "trade " from "trade <character>", so we only get "<character>" in "what"
            tradeKey = what.substring(what.split(" ")[0].length()).trim();
        }
        
        if (tradeKey == null || tradeKey.trim().length() == 0) {
            tradableCharacters(player);
            return false;
        }
        
        clear();
        return false;
    }
    
    private static void tradableCharacters(Player player) {
        Map<String, Character> allCharacters = player.getPlace().getCharacters();
        
        if (allCharacters == null || allCharacters.size() == 0) {
            clear();
            printbig("  There is noone to trade with here!");
            pause();
            clear();
            return;
        }

        Map<Integer, Object> characters = new HashMap<Integer, Object>();
        Map<Integer, Character> characterMap = new HashMap<Integer, Character>();
        characters.put(0, "<Back>");
        
        int i = 1;
        for (Character character : allCharacters.values()) {
            if (!character.isTradable()) {
                continue;
            }

            characters.put(i, character.getName());
            characterMap.put(i, character);
            i++;
        }
        
        if (characters.size() == 0) {
            clear();
            printbig("  Noone here wants to trade with you...");
            pause();
            clear();
            return;
        }
        
        clear();
        
        printbig("  Who do you want to trade with?");
        
        int choice = menu(characters);
        
        if (choice == 0) {
            clear();
            return;
        }
        
        player.trade(characterMap.get(choice));
        
        clear();
    }
}
