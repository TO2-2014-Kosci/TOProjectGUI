package to2.dice.game;

public class Player {
    private String name;
    private int score = 0;
    private Dice dice;
    private boolean isBot;

    public Player(String name, boolean isBot, int diceNumber) {
        this.name = name;
        this.isBot = isBot;
        dice = new Dice(diceNumber);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Dice getDice() {
        return dice;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Player other = (Player) obj;
        if (name == null) {
            if (other.getName() != null) return false;
        } else if (!name.equals(other.getName())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
}