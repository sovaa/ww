package org.eldslott.ww.map;

public class MapResolver{}
/*
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eldslott.ww.common.Color;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public class MapResolver extends Common {
    private static final long serialVersionUID = -75698282499171595L;
    
    public static final void show(Player player) {
        clear();
        
        Area currentArea = player.getArea();
        Map<String, Area> areas = currentArea.getAdjecantAreas();
        Set<String> visited = new HashSet<String>();
        
        visited.add(currentArea.getMainKey());
        
        int hereRow = 0;
        int hereCol = 0;
        
        {
            String name = currentArea.getName();
            
            int row = getTopRow();
            int col = getTopColumn() + 4;
            
            hereRow = row + 1;
            hereCol = col + name.length() / 2;
            
            setCursorPosition(col, row);
            println(name);
            
            setCursorPosition(hereCol, hereRow);
            print("¤", Color.GREEN);
        }
        
        int modRow = 0;
        
        recursiveArea(visited, areas, hereRow, hereCol, modRow);
        
        println();
        println();
        println();
        println();
        
        pause();
        clear();
    }
    
    private static final void recursiveArea(Set<String> visited, Map<String, Area> areas, int hereRow, int hereCol, int modRow) {
        for (Entry<String, Area> entry : areas.entrySet()) {
            Area area = entry.getValue();
            
            if (visited.contains(area.getMainKey())) {
                continue;
            }
            else {
                visited.add(area.getMainKey());
            }
            
            if (!area.isDiscovered()) {
                continue;
            }

            print(hereCol, hereRow + modRow + 1, "|");
            print(hereCol, hereRow + modRow + 2, "|");

            modRow += 2;
            
            print(hereCol, hereRow + modRow, "|-");
            print(hereCol + 2, hereRow + modRow, "¤ " + area.getName());
            
            setCursorPosition(hereCol + 2, hereRow + modRow);
            
            Map<String, Area> adj = area.getAdjecantAreas();
            
            if (adj != null && adj.size() > 0) {
                recursiveArea(visited, adj, hereRow, hereCol + 2, modRow);
            }
        }
    }
}
*/
