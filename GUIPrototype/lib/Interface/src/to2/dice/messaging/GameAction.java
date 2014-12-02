package to2.dice.messaging;

/**
 * Created by Fan on 2014-11-16.
 */
public class GameAction {
    GameActionType type;
    String sender;

    public GameAction(GameActionType type, String sender) {
        this.type = type;
        this.sender = sender;
    }

    public GameActionType getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }
}