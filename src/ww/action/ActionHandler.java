package ww.action;

import java.io.IOException;

import ww.map.MapHandler;
import ww.map.MapResolver;
import ww.player.Player;
import ww.util.Commands;
import ww.util.Common;
import ww.util.MainMenu;

public class ActionHandler extends Common {
    private static final long serialVersionUID = -3236289007339501017L;
    
    public void action(Player player) throws IOException {
        println("What would you like to do?");
        println();
        
        String what = input();
        
        what = what.toLowerCase();
        
        boolean pause = true;
        
        if (escape(what)) {
            pause = showMenu(player);
        }
        else if (is(what, Commands.LOOK)) {
            pause = LookAction.action(player);
        }
        else if (begins(what, Commands.VISIT)) {
            pause = VisitAction.action(player, what);
        }
        else if (begins(what, Commands.TALK)) {
            pause = TalkAction.action(player, what);
        }
        else if (begins(what, Commands.GO)) {
            pause = GoAction.action(player, what);
        }
        else if (is(what, Commands.TAKE)) {
            pause = TakeAction.action(player, what);
        }
        else if (begins(what, Commands.OPEN)) {
            pause = OpenAction.action(player, what);
        }
        else if (is(what, Commands.WALK)) {
            pause = walk(player);
        }
        else if (begins(what, Commands.KICK)) {
            pause = KickAction.action(player, what);
        }
        else if (is(what, Commands.MAP)) {
            pause = map(player);
        }
        else if (is(what, Commands.EXPLORE)) {
            pause = ExploreAction.action(player);
        }
        else if (is(what, Commands.REST)) {
            pause = RestAction.action(player);
        }
        else if (is(what, Commands.DIE)) {
            pause = die(player);
        }
        else if (is(what, Commands.STATUS)) {
            player.status();
        }
        else if (is(what, Commands.SKILLS)) {
            pause = SkillsAction.action(player);
        }
        else if (is(what, Commands.HELP)) {
            pause = HelpAction.action(player);
        }
        else if (is(what, Commands.QUESTS)) {
            pause = QuestAction.action(player);
        }
        else if (is(what, Commands.INVENTORY)) {
            pause = InventoryAction.action(player);
        }
        else if (is(what, Commands.EXIT)) {
            pause = ExitAction.action(player);
        }
        else if (begins(what, Commands.TRADE)) {
            pause = TradeAction.action(player, what);
        }
        else if (begins(what, Commands.LOOT)) {
            pause = LootAction.action(player, what);
        }
        else if (begins(what, Commands.CHARACTER)) {
            pause = CharacterAction.action(player);
        }
        else if (is(what, Commands.REPUTATION)) {
            pause = ReputationAction.action(player);
        }
        else if (what == null || what.length() == 0) {
            clear();
            return;
        }
        else {
            clear();
            printbig("I don't know what '" + what + "' is...");
            refresh();
        }
        
        if (pause) {
            pause();
            clear();
        }
    }
    
    private boolean is(String what, String[] commands) {
        return Commands.is(what, commands);
    }
    
    private boolean begins(String what, String[] commands) {
        return Commands.begins(what, commands);
    }
    
    private boolean escape(String what) {
        if (what == null) {
            return false;
        }
        
        if (Common.ESCAPE.equals(what)) {
            return true;
        }
        
        return false;
    }
    
    private boolean showMenu(Player player) {
        return MainMenu.show(player);
    }
    
    private boolean map(Player player) {
        MapResolver.show(player);
        
        return false;
    }
    
    private boolean walk(Player player) {
        MapHandler.map(player);
        
        return false;
    }
    
    private boolean die(Player player) {
        player.die();
        
        return true;
    }
}
