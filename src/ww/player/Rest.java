package ww.player;

import java.util.Random;

import ww.Skill;
import ww.util.Common;

public class Rest extends Common {
    private static final long serialVersionUID = 5725124976471475453L;

    public static final void rest(Player player) {
        clear();
        
        Double skill = calculateRestSkill(player);
        
        int curHp = player.getCurHp();
        int curMana = player.getCurMana();
        int maxHp = player.getMaxHp();
        int maxMana = player.getMaxMana();
        
        int modHp = (int)(skill * 0.8);
        int modMana = (int)(skill * 0.8);
        
        delayRest();
        
        if ((curHp + modHp) > maxHp) {
            println(" Health full. You regained " + (maxHp - curHp) + " health.");
            curHp = maxHp;
        }
        else {
            println(" You regained " + modHp + " health.");
            curHp += modHp;
        }
        
        if ((curMana + modMana) > maxMana) {
            println(" Mana full. You regained " + (maxMana - curMana) + " mana.");
            curMana = maxMana;
            println();
        }
        else {
            println(" You regained " + modMana + " mana.");
            curMana += modMana;
        }
        
        player.setCurHp(curHp);
        player.setCurMana(curMana);
    }
    
    private static final void delayRest() {
        print(" <Resting");
        
        for (int i = 0; i < 10; i++) {
            print(".");
            refresh();
            
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        println(">");
        println();
    }
    
    private static Double calculateRestSkill(Player player) {
        Double skill = 50.0;
        
        
        Skill sleep = player.getSkill(Skill.SLEEP);
        
        if (sleep != null) {
            skill += (double)sleep.getValue() / 2;
            
            if (random(0, 10) == 0) {
                player.increaseSkill(sleep);
            }
        }
        
        if (player.getPlace().isSafeRest()) {
            skill *= 1.3;
        }
        
        if (player.getPlace().isOutdoors()) {
            Skill wilderness = player.getSkill(Skill.WILDERNESS);
            Skill camp = player.getSkill(Skill.CAMP_MAKING);
            Skill cooking = player.getSkill(Skill.COOKING);
            
            if (wilderness != null) {
                skill = calculateWildernessMod(player, skill);
            }
            
            if (camp != null) {
                skill = calculateCampMod(player, skill);
            }
            
            if (cooking != null) {
                skill = calculateCookingMod(player, skill);
            }
        }
        else {
            format("You have a good nights sleep indoors.");
            println();
        }
        
        return skill;
    }
    
    /**
     * TODO
     * 
     * @param player
     * @param skill
     * @return
     */
    private static final double calculateWildernessMod(Player player, double skill) {
        Skill wilderness = player.getSkill(Skill.WILDERNESS);
        
        println(" <WILDERNESS SURVIVAL>");
        
        info("Before you go to sleep you find some berries and roots " +
        		"to use for breakfast in the morning.");
        
        println();
        
        if (random(0, 10) == 0) {
            player.increaseSkill(wilderness);
        }
        
        return skill + ((double)wilderness.getValue() / 4) * random();
    }
    
    /**
     * TODO 
     * 
     * @param player
     * @param skill
     * @return
     */
    private static final double calculateCookingMod(Player player, double skill) {
        Skill cooking = player.getSkill(Skill.COOKING);
        
        println(" <COOKING>");
        
        info("You cook a fine, refreshing meal in the morning.");
        println();
        
        if (random(0, 10) == 0) {
            player.increaseSkill(cooking);
        }
        
        return skill + ((double)cooking.getValue() / 3) * random();
    }
    
    private static final double calculateCampMod(Player player, double skill) {
        Skill camp = player.getSkill(Skill.CAMP_MAKING);
        
        println(" <CAMP MAKING>");
        
        double mod = ((double)camp.getValue() / 2) * random();
        skill += (mod / 2);
        
        if (mod <= 10) {
            int r = random(0, 2);
            
            if (r == 0) {
                info("You lay out your camping gear on the hard ground, " +
                        "but little do you know, their is a pointy rock " +
                        "the size of a grown mans fist right under your " +
                        "back during the whole night. You wake up with a " +
                        "sore back and feeling grumpy. *sigh* You've had " +
                        "better nights...");
                
                skill /= 2;
            }
            else {
                info("You lay out your camping gear on the hard ground. " +
                        "You've had better night, but this was not too bad");
                
                if (random(0, 10) == 0) {
                    player.increaseSkill(camp);
                }
            }
        }
        else if (mod > 10 && mod <= 50) {
            int r = random(0, 3);
            
            if (r == 0) {
                info("You set your tent on the hard ground, hoping to get " +
                        "a good nights sleep. Unfortunately you did not see " +
                        "that you set it with upward slopes all around it. " +
                        "Nothing bad with that, no... Except that this night " +
                        "it rained. A lot. You wake up early in the morning " +
                        "in the middle of a pool of water. All your equipment " +
                        "is wet and you think you've catched a cold.");
                
                skill /= 3;
            }
            else {
                info("Nothing like a little refreshing camping in the wild! " +
                        "You're a bit stiff, but you wake up and smell the " +
                        "cool breeze of the wind knowing this will be a good " +
                        "day. You feel quite rested.");
                
                if (random(0, 10) == 0) {
                    player.increaseSkill(camp);
                }
            }
        }
        else if (mod > 50 && mod <= 150) {
            int r = random(0, 5);
            
            if (r == 0) {
                info("You confidently set up your tent under a big tree, " +
                        "shielded from both wind and most prying eyes. You " +
                        "prepare a campfire for the morning and hide your " +
                        "equipment under some branches. Everything planned " +
                        "perfectly. Or so you thought, until you woke up in " +
                        "the middle of the night with a mighty itch all over " +
                        "your body.\n\n Apparently you've managed to set up camp " +
                        "right next to a huge ant colony. You rush out into " +
                        "the black night wearing nothing and jumping in the " +
                        "cold river that runs nearby. When you finally find " +
                        "your way back, most of the ants have retreated and " +
                        "your quickly gather your belongings and set up a " +
                        "simple camp far, far away. You wake up later in the " +
                        "morning and it feels like you haven't slept at all...");
                
                skill /= 5;
            }
            else {
                info("You feel like the King of the world! Everything " +
                        "about your camp says you're a super hero. When " +
                        "you are done flattering yourself about your supposedly " +
                        "perfect camp, you go to sleep and have long, sweet " +
                        "dreams about your secret childhood crush. When you " +
                        "wake up you feel supremely good.");
                
                if (random(0, 10) == 0) {
                    player.increaseSkill(camp);
                }
            }
        }
        else { //if (mod > 150 && mod < 500) {
            int r = random(0, 10);
            
            if (r == 0) {
                info("You put your tent upside down in a ditch. When you " +
                        "wake up in the morning you really wonder if you " +
                        "actually thought this would work last night...");
                
                skill /= 10;
            }
            else {
                info("With superior skill and efficency you set up an advanced " +
                        "camp, including a rough palisade, a fireplace to keep " +
                        "you warm during the night but hidden from prying eyes. " +
                        "Fresh water from a river nearby and a pile of extra " +
                        "firewood. Your tent is low to conceal it from sight, but " +
                        "comfortable enough to keep you warm and let you have a " +
                        "good nights sleep.");
                
                if (random(0, 10) == 0) {
                    player.increaseSkill(camp);
                }
            }
        }
        
        println();
        
        return skill;
    }
    
    private static final int random(int i, int j) {
        Random random = new Random();
        
        return random.nextInt(j) + i; 
    }
    
    private static final double random() {
        Random random = new Random();
        
        return (((double)random.nextInt(20)) + 20) / 2;
    }
}
