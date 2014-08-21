package org.eldslott.ww.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lantern.input.Key;
import org.lantern.input.Key.Kind;

import org.eldslott.ww.player.Player;
import org.eldslott.ww.quests.Quest;
import org.eldslott.ww.util.Common;

public class QuestAction extends Common implements Action {
    private static final long serialVersionUID = 941500759195063157L;

    public static boolean action(Player player) throws IOException {
        Map<String, Quest> quests = player.getQuests();
        
        clear();
        
        if (quests.size() == 0) {
            println("You don't have any active quests.");
            return true;
        }
        
        println("Active quests:");
        println();

        Map<Integer, Object> choiceQuests = new HashMap<Integer, Object>();
        Map<Integer, Quest> mapQuests = new HashMap<Integer, Quest>();

        choiceQuests.put(0, "Back");
        
        int i = 1;
        for (Quest quest : quests.values()) {
            choiceQuests.put(i, quest.getName());
            mapQuests.put(i, quest);
        }
        
        int choice = menu(choiceQuests);
        
        if (choice == 0) {
            clear();
            return false;
        }
        
        Quest quest = mapQuests.get(choice);
        
        showQuest(quest);
        clear();
        
        return false;
    }
    
    private static void showQuest(Quest quest) {
        int offset = 0;
        int introw = -1;
        
        while (true) {
            int row = 0;
            
            showQuestText(quest, offset);
            
            if (introw == -1) {
                introw = getRow();
            }
            
            row = getRow();
            
            showScroller(offset, introw);
            refresh();
            
            while (true) {
                Key key = getKey();
                Kind kind = key.getKind();
                clearKeyQueue();
                
                if (key == null || kind.equals(Kind.Escape) || kind.equals(Kind.Enter) || key.getCharacter() == 'q') {
                    return;
                }
                
                if (key.getKind().equals(Kind.ArrowDown)) {
                    if (row > screen.getTerminalSize().getRows()) {
                        if (row - 4 > screen.getTerminalSize().getRows()) {
                            offset += 4;
                        }
                        else {
                            offset++;
                        }
                        
                        break;
                    }
                }
                
                if (key.getKind().equals(Kind.ArrowUp)) {
                    if (offset > 0) {
                        if (offset - 4 > 0) {
                            offset -= 4;
                        }
                        else {
                            offset = 0;
                        }
                        
                        break;
                    }
                }
            }
        }
    }
    
    private static void showScroller(int offset, int introw) {
        int allrows = introw - getTopRow() - 4;
        int maxrows = screen.getTerminalSize().getRows() - getTopRow() - 4;
        int maxscroll = allrows - maxrows;
        
        setCursorPosition(screen.getTerminalSize().getColumns() - 4, getTopRow());
        
        print("o");
        increaseRow();
        decreaseColumn();
        
        double diff = (double)offset / (double)maxscroll * (double)maxrows;
        
        if (diff > (maxrows - 1)) {
            diff = maxrows - 1;
        }
        
        for (int i = 0; i < maxrows; i++) {
            if (i == Math.round(diff)) {
                decreaseColumn();
                print("[=]");
                decreaseColumn();
            }
            else {
                print(":");
            }

            increaseRow();
            decreaseColumn();
        }
        
        print("o");
    }
    
    private static void showQuestText(Quest quest, int offset) {
        clear();
        
        if (offset > 0) {
            decreaseRow(offset);
        }
        
        printbig("    " + quest.getName().toUpperCase());
        notice(quest.getDescription());
        
        List<String> updates = quest.getUpdates();
        
        if (updates == null || updates.size() == 0) {
            return;
        }

        println();
        for (String update : updates) {
            println("     --------");
            println(getFormat(update));
        }
    }
}
