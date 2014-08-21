package org.eldslott.ww.generator;

import java.util.Random;

import org.lantern.terminal.Terminal.Color;

import org.eldslott.ww.attribute.Attribute;
import org.eldslott.ww.attribute.Dexterity;
import org.eldslott.ww.attribute.Intellect;
import org.eldslott.ww.attribute.Spirit;
import org.eldslott.ww.attribute.Strength;
import org.eldslott.ww.item.GenericChest;
import org.eldslott.ww.item.GenericFeet;
import org.eldslott.ww.item.GenericHands;
import org.eldslott.ww.item.GenericHead;
import org.eldslott.ww.item.GenericLegs;
import org.eldslott.ww.item.Item;

public class ItemGenerator {
    private static final String[] badPrefix = new String[] {
        "Crude",
        "Broken",
        "Terrible",
        "Cracked",
        "Useless",
    };
    
    private static final String[] mediumPrefix = new String[] {
        "Good",
        "Trustworthy",
        "Faithful",
    };
    
    private static final String[] goodPrefix = new String[] {
        "Great",
        "Superior",
        "Supreme",
        "Reforged",
        "Adjusted",
        "Balanced",
    };
    
    private static final String[] betterPrefix = new String[] {
        "Great Supreme",
        "Improved",
        "Advanced Reforged",
        "Superior Reforged",
        "Advanced",
    };
    
    private static final String[] bestPrefix = new String[] {
        "Modified",
        "Superior Improved",
        "Improved Advanced",
        "Superiorly Reforged",
        "Supremely Advanced",
        "Materfully Balanced",
    };
    
    private static final String[] godlyPrefix = new String[] {
        "Modified Improved",
        "Superior Modified",
        "Improved Modified",
        "Superiorly Modified",
        "Supremely Modified",
        "Materfully Modified",
    };
    
    private static final String[] suffix = new String[] {
        "of the Sun",
        "of the Mad God",
        "of the Hungry Demon",
        "of the Red Dragon",
    };
    
    private static final String[] chestNames = new String[] {
        "Chest",
        "Wool Chest",
        "Studded Chest",
        "Plate Mail",
        "Chain Mail",
        "Leather Harness",
    };
    
    private static final String[] headNames = new String[] {
        "Cap",
        "Helmet",
        "Visor",
        "Face Guard",
        "Crown",
        "Tiara",
        "Hat",
    };
    
    private static final String[] legNames = new String[] {
        "Leggings",
        "Pants",
        "Greaves",
        "Shins",
    };
    
    private static final String[] handNames = new String[] {
        "Gloves",
        "Hand Guards",
        "Mittens",
        "Gauntlets",
    };
    
    private static final String[] feetNames = new String[] {
        "Shoes",
        "Foot Guards",
        "Boots",
        "Moccasins",
        "Sandals",
        "Slippers",
    };
    
    private static final String random(String[] array) {
        Random random = new Random();
        
        return array[random.nextInt(array.length)];
    }
    
    private static final int random(int max) {
        if (max == 0) {
            return 0;
        }
        
        Random random = new Random();
        
        return random.nextInt(max);
    }
    
    public static Item generateChest(int level) {
        GenericChest generic = new GenericChest(generateName(chestNames, level));
        generic.setValue(bonus(generic, level));
        
        return generic;
    }
    
    public static Item generateHead(int level) {
        GenericHead generic = new GenericHead(generateName(headNames, level));
        generic.setValue(bonus(generic, level));
        
        return generic;
    }
    
    public static Item generateLegs(int level) {
        GenericLegs generic = new GenericLegs(generateName(legNames, level));
        generic.setValue(bonus(generic, level));
        
        return generic;
    }
    
    public static Item generateHands(int level) {
        GenericHands generic = new GenericHands(generateName(handNames, level));
        generic.setValue(bonus(generic, level));
        
        return generic;
    }
    
    public static Item generateFeet(int level) {
        GenericFeet generic = new GenericFeet(generateName(feetNames, level));
        generic.setValue(bonus(generic, level));
        
        return generic;
    }
    
    private static final String generateName(String[] names, Integer level) {
        String name = "";
        
        if (level < 6) {
            name += random(badPrefix) + " ";
            name += random(names);
            
            if (random(4) == 0) {
                name += " " + random(suffix);
            }
        }
        else if (level >= 6 && level < 12) {
            name += random(mediumPrefix) + " ";
            name += random(names);
            
            if (random(4) == 0) {
                name += " " + random(suffix);
            }
        }
        else if (level >= 12 && level < 18) {
            name += random(goodPrefix) + " ";
            name += random(names);
            
            if (random(4) == 0) {
                name += " " + random(suffix);
            }
        }
        else if (level >= 18 && level < 24) {
            name += random(betterPrefix) + " ";
            name += random(names);
            
            if (random(4) == 0) {
                name += " " + random(suffix);
            }
        }
        else if (level >= 24 && level < 32) {
            name += random(bestPrefix) + " ";
            name += random(names);
            
            if (random(4) == 0) {
                name += " " + random(suffix);
            }
        }
        else {
            name += random(godlyPrefix) + " ";
            name += random(names);
            
            if (random(4) == 0) {
                name += " " + random(suffix);
            }
        }
        
        return name;
    }
    
    private static final Integer bonus(Item item, Integer level) {
        int value = 10;
        
        if (level < 6) {
            item.setColor(Color.DEFAULT);
        }
        else if (level >= 6 && level < 12) {
            item.setColor(Color.CYAN);
        }
        else if (level >= 12 && level < 18) {
            item.setColor(Color.BLUE);
        }
        else if (level >= 18 && level < 24) {
            item.setColor(Color.GREEN);
        }
        else if (level >= 24 && level < 32) {
            item.setColor(Color.YELLOW);
        }
        else {
            item.setColor(Color.MAGENTA);
        }
        
        // strength
        {
            int bonus = random(level);
            
            if (bonus > 0) {
                Attribute attribute = new Strength(bonus);
                item.addBonus(attribute);
                
                value += 10 * bonus;
            }
        }
        
        // intellect
        {
            int bonus = random(level);
            if (bonus > 0) {
                Attribute attribute = new Intellect(bonus);
                item.addBonus(attribute);
                
                value += 10 * bonus;
            }
        }
        
        // spirit
        {
            int bonus = random(level);
            if (bonus > 0) {
                Attribute attribute = new Spirit(bonus);
                item.addBonus(attribute);
                
                value += 10 * bonus;
            }
        }
        
        // dexterity
        {
            int bonus = random(level);
            
            if (bonus > 0) {
                Attribute attribute = new Dexterity(bonus);
                item.addBonus(attribute);
                
                value += 10 * bonus;
            }
        }
        
        return value;
    }
}
