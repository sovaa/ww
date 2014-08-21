package org.eldslott.ww.util;

import org.eldslott.ww.player.Player;

public class StatusBar extends Common implements Runnable {
    private static final long serialVersionUID = -7874891472141479544L;
    
    private static final Logger LOG = new Logger(StatusBar.class);
    
    private Player player;
    private CalendarDisplay calendarDisplay = new CalendarDisplay();
    private Phase lastPhase = null;
    private boolean running = true;
    private long lastUpdate = 0;
    
    private static enum Phase {
        NIGHT("Night", 0, 49),
        DAWN("Dawn", 50, 59),
        MORNING_TWILIGHT("Morning twilight", 60, 69),
        SUNRISE("Sunrise", 70, 75),
        MORNING("Morning", 76, 105),
        NOON("Noon", 106, 150),
        AFTERNOON("Afternoon", 151, 209),
        EVENING("Evening", 210, 225),
        SUNSET("Sunset", 226, 231),
        EVENING_TWILIGHT("Evening twilight", 232, 241),
        DUSK("Dusk", 242, 251);
        
        private String name;
        private int start;
        private int end;
        
        private Phase(String name, int start, int end) {
            this.name = name;
            this.start = start;
            this.end = end;
        }
        
        private boolean includes(int diff) {
            return diff >= start && diff <= end;
        }
        
        public static Phase forTime(int diff) {
            for (Phase phase : Phase.values()) {
                if (phase.includes(diff)) {
                    return phase;
                }
            }
            
            return Phase.NIGHT;
        }
        
        @Override
        public String toString() {
            return name;
        }
    };
    
    public StatusBar(Player player) {
        this.player = player;
        this.calendarDisplay = new CalendarDisplay();
        
        player.setStatusBar(this);
        player.setCalendarDisplay(calendarDisplay);
        
        running = true;
        lastUpdate = System.currentTimeMillis();
    }
    
    public void replace() {
        LOG.info("shutting down thread: " + Thread.currentThread().getName());
        running = false;
    }
    
    public static void execute(Player player) {
        new Thread(new StatusBar(player)).start();
    }
    
    public void update() {
        if (!isRunning()) {
            return;
        }
        
        updateStatusBar(false);
    }
    
    public boolean isRunning() {
        return running;
    }
    
    @Override
    public void run() {
        LOG.info("starting new thread: " + Thread.currentThread().getName());
        LOG.info("date of new thread: " + calendarDisplay.updateDisplay());
        
        while (running) {
            updateStatusBar(player, true);
            
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateStatusBar(boolean checkMutex) {
        updateStatusBar(player, checkMutex);
    }
    
    public String future(Player player, long seconds) {
        long current = player.getTimePlayed() + seconds * 1000;
        
        int days = (int)(seconds / 251);
        
        current /= 1000;
        current %= 251;
        
        String date = calendarDisplay.tickFuture(days);
        
        String phase = Phase.forTime((int)current).toString();
        
        return phase + ", " + date;
    }
    
    private void updateStatusBar(Player player, boolean checkMutex) {
        // first time, player not initialized yet
        if (player.getArea() == null || player.getPlace() == null) {
            return;
        }
        
        long currentDiff = System.currentTimeMillis() - lastUpdate;
        long current = player.getTimePlayed();
        
        player.addTimePlayed(currentDiff);
        
        current /= 1000;
        current %= 251;
        
        lastUpdate = System.currentTimeMillis();
        
        int diff = (int)current;
        
        if (lastPhase == null) {
            lastPhase = Phase.forTime(diff);
        }
        else {
            Phase phase = Phase.forTime(diff);
            
            if (phase.equals(Phase.NIGHT) && lastPhase.equals(Phase.DUSK)) {
            	calendarDisplay.tick();
            }
            
            lastPhase = phase;
        }
        
        String phase = Phase.forTime(diff).toString();
        
        String area = player.getArea().getName();
        String place = player.getPlace().getName();
            
        statusOne(" " + area + ", " + place);
        
        if (calendarDisplay != null) {
            statusTwo(" " + phase + ", " + calendarDisplay.updateDisplay());
        }

        refresh(checkMutex);
    }
    
    private static void statusOne(String str) {
        clearLine(0);
        
        screen.putString(0, 0, str, Color.WHITE.toTerminalColor(), Color.DEFAULT.toTerminalColor(), set);
    }
    
    private static void statusTwo(String str) {
        clearLine(1);
        
        screen.putString(0, 1, str, Color.WHITE.toTerminalColor(), Color.DEFAULT.toTerminalColor(), set);
        
        {
            int cols = screen.getTerminalSize().getColumns() - 1;
            while (--cols >= 0) {
                screen.putString(cols, 2, "-", Color.WHITE.toTerminalColor(), Color.DEFAULT.toTerminalColor(), set);
            }
        }
    }

    public CalendarDisplay getCalendarDisplay() {
        return calendarDisplay;
    }

    public void setCalendarDisplay(CalendarDisplay calendarDisplay) {
        this.calendarDisplay = calendarDisplay;
    }
}
