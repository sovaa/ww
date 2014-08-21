package org.eldslott.ww.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lantern.terminal.Terminal.Color;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.item.Gold;
import org.eldslott.ww.item.Item;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public class Trade extends Common {
    private static final long serialVersionUID = 6893200629226969267L;
    
    public static final void repair(Player player, Character character) {
        clear();
        println("Repair!");
    }

    public static final void trade(Player player, Character character) {
        Map<Integer, Object> buySell = new HashMap<Integer, Object>();
        buySell.put(0, " <Back>");
        
        if (character.isGamble()) {
            buySell.put(1, "Gamble");
            
            while (true) {
                clear();
                printbig("  Trading with " + character.getName() + ".");
                
                int choice = menu(buySell);
                
                if (choice == 0) {
                    return;
                }
                
                if (choice == 1) {
                    gamble(player, character);
                    continue;
                }
            }
        }
        else {
            buySell.put(1, "Buy");
            buySell.put(2, "Sell");
            
            while (true) {
                clear();
                printbig("  Trading with " + character.getName() + ".");
                
                int choice = menu(buySell);
                
                if (choice == 0) {
                    return;
                }
                
                if (choice == 1) {
                    buy(player, character);
                    continue;
                }
                
                if (choice == 2) {
                    sell(player, character);
                    continue;
                }
            }
        }
    }
    
    public static final void buy(Player player, Character character) {
        while (true) {
            clear();
            printbig("  Buying");
            
            Gold goldPlayer = (Gold)(player.getInventory().getItem(Gold.id));
            Gold goldCharacter = (Gold)(character.getInventory().getItem(Gold.id));
            
            int playerGold = goldPlayer.getAmount();
            int characterGold = goldCharacter.getAmount();
            
            println("  Your gold: " + playerGold);
            println("  " + character.getName() + "'s gold: " + characterGold);
            println();
            
            List<Item> items = character.getInventory().getItems();
            Map<Integer, Object> buyItems = new HashMap<Integer, Object>();
            Map<Integer, Item> mappedItems = new HashMap<Integer, Item>();
            
            buyItems.put(0, "<Back>");
            
            int i = 1;
            for (Item item : items) {
                if (item.getValue() == 0) {
                    continue;
                }
                
                if (item.getId().equals(Gold.id)) {
                    continue;
                }
                
                buyItems.put(i, item.getNameWithBonus() + " (" + item.getValue() + " gold)");
                mappedItems.put(i, item);
                i++;
            }
            
            int buy = menu(buyItems);
            
            if (buy == 0) {
                return;
            }
            
            Item item = mappedItems.get(buy);
            
            int cost = item.getValue();
            
            if (cost > playerGold) {
                printbig("  You don't have enough gold.");
                pause();
                continue;
            }
            
            goldPlayer.setValue(goldPlayer.getValue() - cost);
            goldCharacter.setValue(goldCharacter.getValue() + cost);
            
            player.getInventory().addItem(item);
            character.getInventory().removeItem(item);
        }
    }
    
    private static final int gambleCost(Player player, Item item) {
        return player.getLevel() * 2 * 990;
    }
    
    public static final void gamble(Player player, Character character) {
        while (true) {
            clear();
            printbig("  Gambling with " + character.getName() + ".");
            
            String update = character.getUpdateInventory();
            
            if (update != null && update.length() > 0) {
                print("  " + character.getName() + " is expecting a new stock at: ");
                print(character.getUpdateInventory(), Color.GREEN);
                
                println();
                println();
            }
            
            Gold goldPlayer = (Gold)(player.getInventory().getItem(Gold.id));
            Gold goldCharacter = (Gold)(character.getInventory().getItem(Gold.id));
            
            int playerGold = goldPlayer.getAmount();
            int characterGold = goldCharacter.getAmount();
            
            print("  Your gold: ");
            println(String.valueOf(playerGold), Color.YELLOW);
            
            print("  " + character.getName() + "'s gold: ");
            println(String.valueOf(characterGold), Color.YELLOW);
            
            println();
            
            List<Item> items = character.getInventory().getItems();
            Map<Integer, Object> buyItems = new HashMap<Integer, Object>();
            Map<Integer, Item> mappedItems = new HashMap<Integer, Item>();
            
            buyItems.put(0, "<Back>");
            
            int i = 1;
            for (Item item : items) {
                if (item.getValue() == 0) {
                    continue;
                }
                
                if (item.getId().equals(Gold.id)) {
                    continue;
                }
                
                int cost = gambleCost(player, item);
                
                buyItems.put(i, item.getSlot() + " (" + cost + " gold)");
                mappedItems.put(i, item);
                i++;
            }
            
            int buy = menu(buyItems);
            
            if (buy == 0) {
                return;
            }
            
            Item item = mappedItems.get(buy);
            
            int cost = gambleCost(player, item);
            
            if (cost > playerGold) {
                printbig("  You don't have enough gold.");
                pause();
                continue;
            }
            
            goldPlayer.setValue(goldPlayer.getValue() - cost);
            goldCharacter.setValue(goldCharacter.getValue() + cost);
            
            player.getInventory().addItem(item);
            character.getInventory().removeItem(item);
        }
    }
    
    public static final void sell(Player player, Character character) {
        while (true) {
            clear();
            printbig("  Selling");
            
            Gold goldPlayer = (Gold)(player.getInventory().getItem(Gold.id));
            Gold goldCharacter = (Gold)(character.getInventory().getItem(Gold.id));
            
            int playerGold = goldPlayer.getAmount();
            int characterGold = goldCharacter.getAmount();
            
            println("  Your gold: " + playerGold);
            println("  " + character.getName() + "'s gold: " + characterGold);
            println();
            
            List<Item> items = player.getInventory().getItems();
            Map<Integer, Object> sellItems = new HashMap<Integer, Object>();
            Map<Integer, Item> mappedItems = new HashMap<Integer, Item>();
            
            sellItems.put(0, "<Back>");
            
            int i = 1;
            for (Item item : items) {
                if (item.getValue() == 0) {
                    continue;
                }
                
                if (item.getId().equals(Gold.id)) {
                    continue;
                }
                
                sellItems.put(i, item.getNameWithBonus() + " (" + item.getValue() + " gold)");
                mappedItems.put(i, item);
                i++;
            }
            
            int buy = menu(sellItems);
            
            if (buy == 0) {
                return;
            }
            
            Item item = mappedItems.get(buy);
            
            int cost = item.getValue();
            
            if (cost > characterGold) {
                printbig("  " + character.getName() + " doesn't have enough gold.");
                pause();
                continue;
            }
            
            goldPlayer.setValue(goldPlayer.getValue() + cost);
            goldCharacter.setValue(goldCharacter.getValue() - cost);
            
            character.getInventory().addItem(item);
            player.getInventory().removeItem(item);
        }
    }
}
