package ww.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lantern.terminal.Terminal.Color;

import rlforj.los.ILosBoard;
import rlforj.math.Point2I;
import ww.util.Common;

public class MapDrawer extends Common implements ILosBoard  {
    private static final long serialVersionUID = 5255615603068959043L;
    
    private int w, h;
    
    public static enum Obstacle {
        WALL,
        DOOR,
        WINDOW,
        WATER,
        NONE,
    };
    
    private Obstacle[][] obstacles;
    private boolean[][] visited;
    
    private Map<Point2I, Character> marks = new HashMap<Point2I, Character>();
    
    private final char visibleFloor          = '.';
    private final char invisibleFloor        = ' ';
    private final char invisibleWall         = ' ';
    private final char invisibleDoor         = ' ';
    private final char visibleWall           = '#';
    private final char visibleDoor           = '+';
    private final char player                = '@';
    
    private int posx = 0;
    private int posy = 0;
    
    public MapDrawer(int w, int h) {
        this.w = w;
        this.h = h;
        
        obstacles = new Obstacle[w][h];
        visited = new boolean[w][h];
    }
    
    public void resetVisitedAndMarks() {
        marks.clear();
        visited = new boolean[w][h];
    }

    public void mark(int x, int y, char c) {
        marks.put(new Point2I(x, y), c);
    }
    
    private boolean overflow(boolean[][] array, int x, int y) {
        try {
            if (x < 0 || y < 0) {
                return true;
            }
            
            if (array[x] == null || array.length < x - 2 || array[x].length < y - 2) {
                return true;
            }
            
            return false;
        }
        catch (Exception e) {
            return true;
        }
    }
    
    private boolean overflow(Obstacle[][] array, int x, int y) {
        try {
            if (x < 0 || y < 0) {
                return true;
            }
            
            if (array[x] == null || array.length < x - 2 || array[x].length < y - 2) {
                return true;
            }
            
            return false;
        }
        catch (Exception e) {
            return true;
        }
    }
    
    public void resetObstacles() {
        obstacles = new Obstacle[w][h];
    }
    
    public void setObstacle(int x, int y) {
        setObstacle(x, y, Obstacle.WALL);
    }
    
    public void setObstacle(int x, int y, Obstacle obstacle) {
        try {
            if (overflow(obstacles, x, y)) {
                return;
            }
            
            obstacles[x][y] = obstacle;
        }
        catch (Exception e) {
            
        }
    }
    
    public boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && x < w && y < h;
    }

    public boolean isObstacle(int x, int y) {
        if (overflow(obstacles, x, y)) {
            return false;
        }
        
        Set<Obstacle> obs = new HashSet<Obstacle>(); 
        
        obs.addAll(Arrays.asList(new Obstacle[] {
                Obstacle.WALL,
                Obstacle.WINDOW,
                Obstacle.DOOR,
        }));
        
        if (obs.contains(obstacles[x][y])) {
            return true;
        }
        
        return false;
    }

    public void visit(int x, int y) {
        if (overflow(visited, x, y)) {
            return;
        }
        
        visited[x][y] = true;
    }

    public boolean wasVisited(int i, int j) {
        if (overflow(visited, i, j)) {
            return false;
        }
        
        return visited[i][j];
    }
    
    public void calculateObstacles(String[] world, int ox, int oy, MapDrawer drawer) {
        drawer.resetObstacles();
        
        int j = 0;
        for (String x : world) {
            List<java.lang.Character> ca = new ArrayList<java.lang.Character>();
            
            for (char c : x.toCharArray()) {
                ca.add(c);
            }
            
            Iterator<java.lang.Character> iter = ca.iterator();
            
            int i = 0;
            while (iter.hasNext()) {
                Character c = iter.next();
                
                if (c.equals('x')) {
                    drawer.setObstacle(i - ox, j - oy, Obstacle.WALL);
                }
                else if (c.equals('-')) {
                    drawer.setObstacle(i - ox, j - oy, Obstacle.DOOR);
                }
                else if (c.equals('@')) {
                    posx = i - ox;
                    posy = j - oy;
                }
                
                i++;
            }
            
            j++;
        }
    }
    
    public void print(int ox, int oy) {
        Point2I p = new Point2I(0, 0);
        
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                p.x = i;
                p.y = j;
                
                Character c = marks.get(p);
                
                if (c != null) {
                    print(c);
                }
                else
                    if (i == ox && j == oy) {
                        print(player, Color.GREEN, Color.DEFAULT);
                    }
                    else {
                        if (wasVisited(i, j)) {
                            if (isWall(i, j)) {
                                print(visibleWall, Color.BLUE, Color.DEFAULT);
                            }
                            else if (isDoor(i, j)) {
                                print(visibleDoor, Color.YELLOW, Color.DEFAULT);
                            }
                            else {
                                print(visibleFloor);
                            }
                        }
                        else {
                            if (isWall(i, j)) {
                                print(invisibleWall, Color.MAGENTA, Color.DEFAULT); 
                            }
                            else if (isDoor(i, j)) {
                                print(invisibleDoor, Color.MAGENTA, Color.DEFAULT);
                            }
                            else {
                                print(invisibleFloor);
                            }
                        }
                    }
            }
            
            println();
        }
    }
    
    public boolean isWall(int i, int j) {
        return Obstacle.WALL.equals(obstacles[i][j]);
    }
    
    public boolean isDoor(int i, int j) {
        return Obstacle.DOOR.equals(obstacles[i][j]);
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }
}
