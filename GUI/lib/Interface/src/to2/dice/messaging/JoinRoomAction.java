package to2.dice.messaging;

/**
 * Created by Fan on 2014-12-16.
 */
public class JoinRoomAction extends GameAction  {
    private final String gameRoom;

    public JoinRoomAction(String sender, String gameRoom) {
        super(GameActionType.JOIN_ROOM, sender);
        this.gameRoom = gameRoom;
    }

    public String getGameRoom() {
        return gameRoom;
    }
}
