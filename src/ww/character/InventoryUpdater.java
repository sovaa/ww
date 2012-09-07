package ww.character;

import java.util.HashMap;
import java.util.Random;

import ww.generator.ItemGenerator;
import ww.item.Gold;
import ww.item.Item;
import ww.player.Inventory;
import ww.player.Player;
import ww.util.Logger;

public final class InventoryUpdater implements Runnable {
    private static final Logger LOG = new Logger(InventoryUpdater.class);
    
    private boolean running = true;
    
    private Inventory inventory;
    private Player player;
    private Character character;
    
    public InventoryUpdater(Inventory inventory, Player player, Character character) {
        this.inventory = inventory;
        this.player = player;
        this.character = character;
    }
    
    private int random(int level) {
        int max = (int)(level * 2) + 10;
        
        Random random = new Random();
        int r = random.nextInt(max);
        
        if (random.nextInt(10) == 0) {
            r += random.nextInt(max);
        }
        
        return r;
    }
    
    @Override
    public void run() {
        while (running) {
            Item gold = inventory.getItem(Gold.id);
            
            inventory.setBackpack(new HashMap<String, Item>());

            inventory.addItem(ItemGenerator.generateChest(random(player.getLevel())));
            inventory.addItem(ItemGenerator.generateChest(random(player.getLevel())));

            inventory.addItem(ItemGenerator.generateFeet(random(player.getLevel())));
            inventory.addItem(ItemGenerator.generateFeet(random(player.getLevel())));

            inventory.addItem(ItemGenerator.generateHands(random(player.getLevel())));
            inventory.addItem(ItemGenerator.generateHands(random(player.getLevel())));

            inventory.addItem(ItemGenerator.generateHead(random(player.getLevel())));
            inventory.addItem(ItemGenerator.generateHead(random(player.getLevel())));

            inventory.addItem(ItemGenerator.generateLegs(random(player.getLevel())));
            inventory.addItem(ItemGenerator.generateLegs(random(player.getLevel())));
            
            if (gold != null) {
                inventory.addItem(gold);
            }
            
            try {
                int sleep = 300;
                
                if (player.getStatusBar() != null) {
                    character.setUpdateInventory(player.getStatusBar().future(player, sleep));
                }
                
                Thread.sleep(sleep * 1000);
            }
            catch (InterruptedException e) {
                LOG.error("sleep interrupted: " + e.getMessage(), e);
            }
        }
    }
    
    public void quit() {
        this.running = false;
    }
}
