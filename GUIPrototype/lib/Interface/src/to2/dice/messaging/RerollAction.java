package to2.dice.messaging;

/**
 * Created by Fan on 2014-11-17.
 */
public class RerollAction extends GameAction {
    private boolean[] chosenDice;

    public RerollAction(GameActionType type, String sender, boolean[] chosenDice) {
        super(type, sender);
        this.chosenDice = chosenDice;
    }

    public boolean[] getChosenDice() {
        return chosenDice;
    }
}