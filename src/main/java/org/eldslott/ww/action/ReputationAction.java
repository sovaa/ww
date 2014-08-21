package org.eldslott.ww.action;

import java.util.Map;

import org.lantern.terminal.Terminal.Color;

import org.eldslott.ww.player.Player;
import org.eldslott.ww.reputation.Reputation;
import org.eldslott.ww.util.Common;

public class ReputationAction extends Common implements Action {
    private static final long serialVersionUID = 201833843103302706L;
    
    private static final int TABLE_WIDTH = 40;

    public static final boolean action(Player player) {
        Map<String, Reputation> reputations = Reputation.rootReputations;
        
        clear();
        
        for (Reputation reputation : reputations.values()) {
            if (!reputation.isDiscovered()) {
                continue;
            }
            
            printReputation(2, reputation);
            println();
        }
        
        println();
        println();
        
        return true;
    }
    
    private static final void printReputation(int current, Reputation reputation) {
        String indent = builder(current);
        String justify = justify(reputation, current);
        
        String name = reputation.getName();
        String value = String.valueOf(reputation.getValue());
        
        Color color = Color.DEFAULT;
        
        switch (current) {
            case 2:
                color = Color.YELLOW;
                break;
            case 4:
                color = Color.MAGENTA;
                break;
        }
        
        print(indent);
        print(name, color);
        println(justify + value);
        
        Map<String, Reputation> children = reputation.getChildren();
        
        if (children != null && children.size() > 0) {
            for (Reputation child : children.values()) {
                if (!child.isDiscovered()) {
                    continue;
                }

                printReputation(current + 2, child);
            }
        }
    }
    
    private static final String builder(int length) {
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        
        return builder.toString();
    }
    
    private static final String justify(Reputation reputation, int current) {
        String name = reputation.getName();
        String value = String.valueOf(reputation.getValue());
        
        int length = TABLE_WIDTH - name.length() - value.length() - current;
        
        return builder(length);
    }
}
