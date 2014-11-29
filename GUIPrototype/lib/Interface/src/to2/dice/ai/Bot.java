package to2.dice.ai;

import to2.dice.game.Dice;
import java.util.List;

public abstract class Bot {
    public abstract void makeMove(Dice dice, List<Dice> otherDice);
}
