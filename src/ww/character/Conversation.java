package ww.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ww.player.Player;
import ww.quests.Quest;
import ww.util.Common;

public class Conversation extends Common implements Serializable {
    private static final long serialVersionUID = 771853952831515051L;

    private static final class Monologue implements Serializable {
        private static final long serialVersionUID = 1837508433333313100L;
        
        private String character;
        private String line;
        
        public Monologue(String character, String line) {
            this.character = character;
            this.line = line;
        }
        
        public String getCharacter() {
            return character;
        }
        
        public String getLine() {
            return line;
        }
    }
    
    private String uuid = UUID.randomUUID().toString();
    
    private String greeting;
    private String description;
    private String end;
    private String reply;
    
    private Map<String, String> returnReplies;
    
    private List<Monologue> monologue;
    
    private boolean hostile;
    
    private List<Conversation> conversations;
    
    public Conversation() {
    	this.conversations = new ArrayList<Conversation>();
    	this.returnReplies = new HashMap<String, String>();
    	this.monologue = new ArrayList<Conversation.Monologue>();
    }
    
    public void action(Character character, Player player) {
    	// to be overwritten
    }
    
    public void fight(Character character, Player player) {
    	// to be overwritten
    	println("fighting");
    	
    	character.fight(player);
    }
    
    public boolean condition(Character character, Player player) {
        // to be overwritten
        return true;
    }

    public void death(Character character, Player player) {
    	println("death");
    }
    
    public void greeting(Character character, Player player) {
        // to be overwritten
    }
    
    public boolean eligible(Player player, String questName, String state) {
        String[] states = {
                state
        };
        
        return eligible(player, questName, states);
    }
    
    public boolean eligible(Player player, String questName, String[] states) {
        Quest quest = player.getQuests().get(questName);
        
        if (quest == null) {
            return false;
        }
        
        for (String state : states) {
            if (quest.getState().equals(state)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean eligible(Player player, String questName) {
        Quest quest = player.getQuests().get(questName);
        
        if (quest == null) {
            return false;
        }
        
        if (quest.isCompleted()) {
            return false;
        }
        
        return true;
    }
    
    public void monologue(String character, String line) {
        monologue.add(new Monologue(character, line));
    }
    
    public void begin(Character character, Player player) {
    	Map<Integer, Conversation> convos = new HashMap<Integer, Conversation>();
    	
    	clear();
    	
    	Integer key = null;
    	
    	while (key == null) {
    		if (monologue != null && monologue.size() > 0) {
    		    for (int i = 0; i < monologue.size(); i++) {
    		        int j = 0;
    		        for (Monologue mono : monologue) {
                        answer(mono.getLine(), mono.getCharacter());
    		            
    		            if (i == j) {
    		                break;
    		            }
    		            
    		            j++;
    		        }
                    
                    // don't clear on last, before replies
                    if (i < monologue.size() - 1) {
                        pause();
                        clear();
                    }
    		    }
    		}
    		else {
    		    if (getDescription() != null) {
    		        info(getDescription());
    		    }
    		    
    		    answer(greeting, character.getName());
    		    println();
    		}

            convos = new HashMap<Integer, Conversation>();
            Map<Integer, Object> choices = new HashMap<Integer, Object>(); 
    		
            int i = 0;
            for (Conversation talk : conversations) {
                String reply = talk.getReply();
                
                // not allowed to talk about this yet, might be because of a quest not completed
                if (!talk.condition(character, player)) {
                    continue;
                }
                
                if (talk.getReturnReply(uuid) != null) {
                    reply = talk.getReturnReply(uuid);
                }
                
                convos.put(i, talk);
                choices.put(i, reply);
                i++;
            }
            
            if (choices.size() == 0) {
                action(character, player); // do action before exiting
                return;
            }
            
            key = menu(choices);
            
    		println();
    		clear();
    	}

    	Conversation talk = convos.get(key);

    	talk.action(character, player);
    	
    	if (talk.getDescription() != null) {
    		info(talk.getDescription());
    	}
    	
    	if (talk.getHostile()) {
    		if (talk.getGreeting() != null) {
    			answer(talk.getGreeting(), character.getName());
    		}
    		
    		fight(character, player);
    		death(character, player);
    		return;
    	}

        talk.greeting(character, player);
    	
    	if (talk.replies() == null || talk.replies().size() == 0) {
    	    if (talk.getEnd() != null && talk.getEnd().length() > 0) {
    	        answer(talk.getEnd(), character.getName());
    	    }
    	    
    		return;
    	}
    	
    	talk.begin(character, player);
    }
    
    private String getGreeting() {
    	return greeting;
    }
    
    private String getDescription() {
    	return description;
    }
    
    private boolean getHostile() {
    	return hostile;
    }
    
    public Conversation addReply(Conversation conversation) {
    	conversations.add(conversation);
    	
    	return conversation;
    }
    
    public void answer(String str, String name) {
        println("  " + name.toUpperCase());
    	println(getFormat("    -\"" + str + "\""));
    }
    
    private String getEnd() {
    	return end;
    }
    
    public Conversation reply(String reply, Conversation returnTo) {
    	returnTo.addReturnReply(uuid, reply);
    	
    	conversations.add(returnTo);
    	
    	return returnTo;
    }
    
    public Conversation reply(String reply) {
    	Conversation conversation = new Conversation();
    	conversation.setReply(reply);
    	
    	conversations.add(conversation);
    	
    	return conversation;
    }
    
    private void addReturnReply(String uuid, String reply) {
    	returnReplies.put(uuid, reply);
    }
    
    private String getReturnReply(String uuid) {
    	return returnReplies.get(uuid);
    }

    public void setReply(String reply) {
    	this.reply = reply;
    }
    
    public List<Conversation> replies() {
    	return conversations;
    }
    
    public void greet(String greeting) {
    	this.greeting = greeting;
    }
    
    public void description(String description) {
    	this.description = description;
    }
    
    public void end(String end) {
    	this.end = end;
    }
    
    public String getReply() {
    	return reply;
    }
    
    public void hostile(boolean hostile) {
    	this.hostile = hostile;
    }
}
