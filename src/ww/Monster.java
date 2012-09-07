package ww;

import ww.player.Player;
import ww.util.Common;

public class Monster extends Common {
    private static final long serialVersionUID = 5400048564944950270L;
    
    private int startHp;
    private int hp;
    private int dmg;
    private int lvl;
    private int exp;
    private int hit;
    private int rndDmg = 0;
    private String name;
    private String klass;
    
    public void createRat() {
    	name = "Rat";
    	hp = 19;
    	startHp = hp;
    	dmg = 1;
    	rndDmg = 4;
    	hit = 60;
    	exp = 10;
    }
    
    public void createZombieRat() {
    	name = "Zombie Rat";
    	hp = 10;
    	startHp = hp;
    	dmg = 4;
    	rndDmg = 9;
    	hit = 50;
    	exp = 12;
    }
    
    public void createOrc() {
    	name = "Orc";
    	hp = 50;
    	startHp = hp;
    	dmg = 15;
    	rndDmg = 10;
    	hit = 70;
    	exp = 50;
    }
    
    public void createGolem() {
    	name = "Golem";
    	hp = 100;
    	startHp = hp;
    	dmg = 15;
    	rndDmg = 10;
    	hit = 70;
    	exp = 100;
    }
    
    public void createThief() {
    	name = "Thief";
    	hp = 50;
    	startHp = hp;
    	dmg = 20;
    	hit = 70;
    	exp = 70;
    }
    
    public void createBandit() {
    	name = "Bandit";
    	hp = 40;
    	startHp = hp;
    	dmg = 15;
    	rndDmg = 5;
    	hit = 70;
    	exp = 50;
    }
    
    public void createHumanScavenger() {
    	name = "Human Scavenger";
    	hp = 35;
    	startHp = hp;
    	dmg = 18;
    	rndDmg = 9;
    	hit = 80;
    	exp = 55;
    }
    
    public void createTroll() {
    	name = "Troll";
    	hp = 80;
    	startHp = hp;
    	dmg = 20;
    	rndDmg = 15;
    	hit = 60;
    	exp = 110;
    }
    
    public void createSkeleton() {
    	name = "Skeleton";
    	hp = 25;
    	startHp = hp;
    	dmg = 12;
    	rndDmg = 5;
    	hit = 80;
    	exp = 30;
    }
    
    public void createZombie() {
    	name = "Zombie";
    	hp = 45;
    	startHp = hp;
    	dmg = 5;
    	rndDmg = 30;
    	hit = 40;
    	exp = 60;
    }
    
    public void createBear() {
    	name = "Bear";
    	hp = 65;
    	startHp = hp;
    	dmg = 15;
    	rndDmg = 15;
    	hit = 65;
    	exp = 40;
    }
    
    public void createDireWolf() {
    	name = "Bear";
    	hp = 35;
    	startHp = hp;
    	dmg = 25;
    	rndDmg = 15;
    	hit = 80;
    	exp = 45;
    }
    
    public void createGiantSpider() {
    	name = "Giant Spider";
    	hp = 30;
    	startHp = hp;
    	dmg = 5;
    	rndDmg = 20;
    	hit = 70;
    	exp = 35;
    }
    
    public void createRock() {
    	name = "Angry rock";
    	hp = 200;
    	startHp = hp;
    	dmg = 1;
    	rndDmg = 2;
    	hit = 20;
    	exp = 200;
    }
    
    public void createDragon() {
    	name = "Dragon";
    	hp = 500;
    	startHp = hp;
    	dmg = 45;
    	rndDmg = 60;
    	hit = 90;
    	exp = 400;
    }
    
    void action(Player player) {
    	if(hp <= 0) {
    		return;
    	}
    	double randomDmg = Math.random() * rndDmg;
    	double randomHit = Math.random() * 100;
    	double randomDodge = Math.random() * 100;
    	
    	if(randomDodge <= player.calcDodge()) {
    		player.getSkill(Skill.DODGE).increase();
    		println("Enemy: Miss cause you dodged lol!");
    	}
    	else {
    	
    		if(randomHit < hit) {
    			int damage = dmg + (int)randomDmg;
    			player.setCurHp(player.getCurHp() - damage);
    			println("Enemy: " + name + " hits you for " + damage + " damage!");
    		}
    		else {
    			println("Enemy: " + name + " miss!");
    		}
    	}
    }
    
    public void reset() {
    	hp = startHp;
    }

    public int getLvl() {
    	return lvl;
    }

    public void setLvl(int lvl) {
    	this.lvl = lvl;
    }

    public int getExp() {
    	return exp;
    }

    public void setExp(int exp) {
    	this.exp = exp;
    }

    public String getKlass() {
    	return klass;
    }

    public void setKlass(String klass) {
    	this.klass = klass;
    }

    public int getHp() {
    	return hp;
    }

    public void setHp(int hp) {
    	this.hp = hp;
    }

    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }
}
