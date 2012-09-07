package ww.quests.bwlv;

import java.io.Serializable;

import ww.location.Area;
import ww.location.PlaceNames;
import ww.quests.Quest;
import ww.reputation.Reputation;

public class BeatUpMarekDravis extends Quest {
    private static final long serialVersionUID = -3627252649786860503L;

    public static class States implements Serializable {
        private static final long serialVersionUID = 6460986631044223408L;
        
        public static final String ACCEPTED = "accepted";
        public static final String KILLED_MAREK = "killed-marek";
        public static final String JOHN_SENT_INVESTIGATOR = "john-sent-investigator";
        public static final String MEET_JOHN_AT_INN = "meet-john-at-inn";
        public static final String TALKED_TO_BUCK_AFTER_MAREK_KILLED = "talked-to-buck-after-marek-killed";
        public static final String TALKED_TO_BUCK_AFTER_MAREK_BEATEN = "talked-to-buck-after-marek-beaten";
        public static final String TOLD_JOHN_BUCK_TRIED_TO_KILL_MAREK = "told-john-buck-tried-to-kill-marek";
        public static final String JOHN_TOLD_LOCATION_OF_JAIL = "john-told-location-of-jail";
        public static final String GOT_THROUGH_JAIL_DUNGEON_DOOR_BUCK = "got-through-jail-dungeon-door-buck";
        public static final String INFO_ARTIFACT_TOLD_BUCK_LET_HIM_OUT = "info-artifact-told-buck-let-him-out";
        public static final String INFO_ARTIFACT_TOLD_BUCK_STAY_IN_JAIL = "info-artifact-told-buck-stay-in-jail";
        public static final String TOLD_BUCK_YOU_WOULD_MAKE_JOHN_LET_HIM_GO = "told-buck-you-would-make-john-let-him-go";
        public static final String LET_HIM_GO_TO_GET_INFORMATION_ABOUT_MAREK = "let-him-go-to-get-information-about-marek";
        public static final String MADE_JOHN_LET_BUCK_GO = "made-john-let-buck-go";
        public static final String INFO_ARTIFACT_BUCK_IS_OUT = "info-artifact-buck-is-out";
        public static final String FOUND_GORDON_DEAD = "found-gordon-dead";
        public static final String TOLD_JOHN_GORDON_DEAD = "told-john-gordon-dead";
        public static final String TOLD_BUCK_GORDON_DEAD = "told-buck-gordon-dead";
        public static final String JOHN_COUGHT_MAREK = "john-caught-marek";
        public static final String GOT_THROUGH_JAIL_DUNGEON_DOOR_MAREK = "got-through-jail-dungeon-door-marek";
        public static final String AGREED_TO_BUST_OUT_MAREK = "agreed-to-bust-out-marek";
        public static final String KICKED_GRATES_TO_JAIL_CELL = "kicked-grates-to-jail-cell";
    };
    
    public BeatUpMarekDravis() {
        setState(States.ACCEPTED);
        
        setId(Quest.BUCK_BEAT_UP_MAREK_DRAVIS);
        setName("Beat up Marek Dravis");
        
        setDescription("Buck, the owner of the tavern " + PlaceNames.BlackWater.THE_BUSTY_MAID_INN + " in " +
                Area.THE_BLACK_WATER_LODGE_VILLAGE + " wants you to beat up Marek Dravis, " + 
                "who lives in his house nearby.");
    }
    
    @Override
    public void complete() {
        if (isCompleted()) {
            return;
        }
        
        setCompleted(true);
        
        Reputation.allReputations.get(Reputation.Identity.BLACK_WATER.ID).increase(10);
        Reputation.allReputations.get(Reputation.Identity.NORTHERN_HUMAN_SETTELMENTS.ID).increase(2);
        Reputation.allReputations.get(Reputation.Identity.HUMAN.ID).increase(1);
    }
}
