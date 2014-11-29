package to2.dice.server;

import to2.dice.game.Dice;
import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.messaging.Response;

import java.util.List;

/**
 * Created by Fan on 2014-11-17.
 */
public abstract class ConnectionProxy {

    public ConnectionProxy(Object serverLink, ServerMessageListener listener) {
        this.addServerMessageListener(listener);
        this.connect(serverLink);
    }

    public abstract Response login(String login);

    public abstract List<GameInfo> getRoomList();

    public abstract Response createRoom(GameSettings settings, String login);

    public abstract Response joinRoom(String roomName, String login);

    public abstract Response leaveRoom(String login);

    public abstract Response sitDown(String login);

    public abstract Response standUp(String login);

    public abstract Response reroll(Dice dice);

    protected abstract boolean connect(Object serverLink);

    public abstract void addServerMessageListener(ServerMessageListener listener);
}
