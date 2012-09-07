package ww.player;

import java.io.Serializable;

import ww.util.Logger;

public class Regen implements Runnable, Serializable {
    private static final long serialVersionUID = -911840872667469910L;
    
    private static final Logger LOG = new Logger(Regen.class);
    
    private Player player;
    private boolean running;
    
    public Regen(Player player) {
        this.player = player;
        this.running = true;
        
        player.setRegen(this);
    }
    
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                
                if (player.getCurHpDouble() < player.getMaxHp()) {
                    double current = player.getCurHpDouble();
                    
                    int dex = player.getDexterity();
                    
                    current += dex * 0.01;
                    
                    if (current > player.getMaxHp()) {
                        current = player.getMaxHp();
                    }
                    
                    player.setCurHp(current);
                }
                
                if (player.getCurManaDouble() < player.getMaxMana()) {
                    double current = player.getCurManaDouble();
                    
                    int spr = player.getSpirit();
                    
                    current += spr * 0.01;
                    
                    if (current > player.getMaxMana()) {
                        current = player.getMaxMana();
                    }
                    
                    player.setCurMana(current);
                }
            }
            catch (InterruptedException e) {
                LOG.error("interrupted: " + e.getMessage(), e);
            }
        }
    }
    
    public void replace() {
        this.running = false;
    }
    
    public static void execute(Player player) {
        new Thread(new Regen(player)).start();
    }
}
