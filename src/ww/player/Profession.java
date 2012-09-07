package ww.player;

public enum Profession {
    TEACHER("Teacher"),
    WARRIOR("Warrior"),
    MONK("Monk"),
    ARTISIAN("Artisian"),
    TRAVELLER("Traveller"),
    THIEF("Thief"),
    HUNTER("Hunter"),
    ASSASSIN("Assassin"),
    ALCHEMIST("Alchemist");
    
    private String name;
    
    Profession(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
