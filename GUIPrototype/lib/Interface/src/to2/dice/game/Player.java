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
}