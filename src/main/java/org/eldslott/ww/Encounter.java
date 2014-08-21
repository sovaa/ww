package org.eldslott.ww;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eldslott.ww.character.Character;
import org.eldslott.ww.location.Area;
import org.eldslott.ww.location.Place;
import org.eldslott.ww.player.Player;
import org.eldslott.ww.util.Common;

public class Encounter extends Common {
    private static final long serialVersionUID = 5089274895773668952L;

    public static void monster(Player player, Monster monster) throws IOException {
    	List<Monster> monsters = new ArrayList<Monster>();
    	
    	monsters.add(monster);
    	monster(player, monsters);
    }
    
    public static void monster(Player player, List<Monster> monsters) throws IOException {
        info("To be implemented anew.");
        return;
        
        /*
    	String monsterNames = "";
    	boolean multiple = false;
    	
    	int xp = 0;
    	int kills = monsters.size();
    	
    	for (Monster monster : monsters) {
    		xp += monster.getExp();
    	}
    	
    	if (monsters.size() == 1) {
    		monsterNames = "a " + monsters.iterator().next().getName();
    	}
    	else {
    		multiple = true;
    		monsterNames = monsters.iterator().next().getName() + "s";
    	}
    	
    	clear();
    	println("Battle!");
    	println("----------------------------------------------------------");
    	println();
    	
    	if (multiple) {
    		println("You encounter " + monsters.size() + " " + monsterNames + "!");
    	}
    	else {
    		println("You encounter a " + monsterNames + "!");
    	}
    	
    	println();
    	println("----------------------------------------------------------");
        player.status();
    	println();
    	
    	Monster currentMonster = monsters.iterator().next();
    	while(true) {
    		if(player.getCurHp() <= 0) {
                player.die();
    		}
    		
    		println("What will you do?");
    		println();
    		String action = input();
    		println();
    		
    		clear();
    		
    		println("Battle!");
    		println("----------------------------------------------------------");
    		
    		if (multiple) {
    			if (monsters.size() > 1) {
    				printbig(monsters.size() + " " + monsterNames + " left!");
    			}
    			else {
    				printbig(monsters.size() + " " + currentMonster.getName() + " left!");
    			}
    		}
    		
    		player.action(action, currentMonster);
    		
    		for (Monster monster : monsters) {
    			monster.action(player);
    		}
    		
    		if(currentMonster.getHp() <= 0) {
    			currentMonster.reset();
    			
    			if (monsters.size() > 0) {
    				monsters.remove(0);
    			}
    			
    			println(currentMonster.getName() + " is defeated!");
    			println("----------------------------------------------------------");
                player.status();
    			println();
    			
    			if (monsters.size() == 0) {
    				break;
    			}
    		}
    		
    		println("----------------------------------------------------------");
            player.status();
    		println();
    	}
    	
    	player.addKills(kills);
    	player.gainExp(xp);
    	
    	*/
    }
    
    public static void character(Player player, Character character) {
    	info("You encounter a character.");
    }
    
    public static void area(Player player, Area area) {
    	info("You discovered a way to get to " + area.getName() + " from here!");
    	area.setDiscovered(true);
    }
    
    public static void place(Player player, Place place) {
    	place.setDiscovered(true);
    }
}
