package to2.dice.game;

public enum GameType {
    NPLUS("N+"),
    NMUL("N*"),
    POKER("Poker")
    ;
    
    private final String name;

    private GameType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}