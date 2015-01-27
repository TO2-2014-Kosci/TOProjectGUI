package to2.dice.messaging;

/**
 * Created by Fan on 2014-11-17.
 */
public class RerollAction extends GameAction {
    private boolean[] chosenDice;

    public RerollAction(String sender, boolean[] chosenDice) {
        super(GameActionType.REROLL, sender);
        this.chosenDice = chosenDice;
    }

    public boolean[] getChosenDice() {
        return chosenDice;
    }
}