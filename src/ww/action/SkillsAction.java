package ww.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ww.Skill;
import ww.player.Player;
import ww.util.Common;

public class SkillsAction extends Common implements Action {
    private static final long serialVersionUID = -253057583314021827L;

    public static boolean action(Player player) throws IOException {
        while (true) {
            clear();
            
            Set<Skill> skills = player.getSkills();
            
            Map<String, Skill> mappedSkills = new HashMap<String, Skill>();
            
            println(" Number | Level | Name");
            println("--------|-------|------------------------");
            
            int i = 1;
            for (Skill skill : skills) {
                mappedSkills.put(String.valueOf(i), skill);
                
                String value = String.valueOf(skill.getValue());
                String name = skill.getName();
                
                if (i < 10) {
                    print(" ");
                }
                
                if (i < 100) {
                    print(" ");
                }
                
                print(" (" + i + ")  | " + value);
                
                for (int j = 0; j < 5 - value.length(); j++) {
                    print(" ");
                }
                
                println(" | " + name);
                i++;
            }
            
            println("-----------------------------------------");
            
            printbig("Type a number to get information about a skill or enter to return:");
            
            String what = input(true);
            
            if (what == null || what.length() == 0) {
                return false;
            }
            
            if (mappedSkills.get(what) == null) {
                printbig("No such skill!");
                pause();
                continue;
            }
            
            Skill skill = mappedSkills.get(what);
            
            println();
            println(skill.getName().toUpperCase());
            printbig(skill.getDescription() + ".");
            
            pause();
        }
    }
}
