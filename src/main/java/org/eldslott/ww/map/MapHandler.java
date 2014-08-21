package org.eldslott.ww.map;

import org.lantern.input.Key;

import rlforj.los.IFovAlgorithm;
import rlforj.los.PrecisePermissive;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public class MapHandler extends Common {
    private static final long serialVersionUID = 2337292678327231443L;

    public static void map(Player player) {
        int col = getMaxPrintableCols();
        int row = getMaxPrintableRows();
        
        MapDrawer drawer = new MapDrawer(col, row);

        String world[] = {
                "                                                                    ",
                "                                                                    ",
                "                                                                    ",
                "                                                                    ",
                "                                                                    ",
                "                                    xxxxxxx  xxxxxxxx               ",
                "                xxxxxxxxx    xxxxx  x     x  x      x               ",
                "                x       x    x   x  x     x  x      x               ",
                "                x       x    xxxxx  xxxxxxx  xxxxxxxx               ",
                "                xxx-xxxxx                                           ",
                "                                  xxxxxxxxx     @                   ",
                "                xxxxxxxx-xxxxx    x       x  xxxxxxxx               ",
                "                x            x    x       x  x      x               ",
                "                x            x    x       x  x      x               ",
                "                xxxxxxxxxxxxxx    xxxxxxxxx  xxxxxxxx               ",
        };
        
        int ox = 0;
        int oy = 0;
        
        int posx = -1;
        int posy = -1;
        
        while (true) {
            drawer.calculateObstacles(world, ox, oy, drawer);
            
            if (posx == -1 && posy == -1) {
                posx = drawer.getPosx();
                posy = drawer.getPosy();
            }
            
            IFovAlgorithm a = new PrecisePermissive();
            a.visitFieldOfView(drawer, posx, posy, 9);

            clear();
            drawer.print(posx, posy);
            refresh();
            
            Key input = getKey();
            
            if (input.getKind().equals(Key.Kind.ArrowUp)) {
                if (canGo(drawer, posx, posy - 1)) {
                    oy--;
                }
            }
            else if (input.getKind().equals(Key.Kind.ArrowDown)) {
                if (canGo(drawer, posx, posy + 1)) {
                    oy++;
                }
            }
            else if (input.getKind().equals(Key.Kind.ArrowLeft)) {
                if (canGo(drawer, posx - 1, posy)) {
                    ox--;
                }
            }
            else if (input.getKind().equals(Key.Kind.ArrowRight)) {
                if (canGo(drawer, posx + 1, posy)) {
                    ox++;
                }
            }
            else if (input.getKind().equals(Key.Kind.Enter)) {
                break;
            }
            
            drawer.resetVisitedAndMarks();
        }
        
        clear();
    }
    
    private static final boolean canGo(MapDrawer drawer, int x, int y) {
        if (drawer.isDoor(x, y)) {
            return true;
        }
        
        if (drawer.isObstacle(x, y)) {
            return false;
        }
        
        return true;
    }
}
