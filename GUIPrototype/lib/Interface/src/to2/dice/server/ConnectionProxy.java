package to2.dice.server;

import to2.dice.game.Dice;
import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.messaging.Response;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Fan
 * @version 0.3
 */
public abstract class ConnectionProxy {

    public ConnectionProxy(Object serverLink, ServerMessageListener listener) {
        this.addServerMessageListener(listener);
        this.connect(serverLink);
    }

    public abstract Response login(String login) throws TimeoutException;

    public abstract List<GameInfo> getRoomList() throws TimeoutException;

    public abstract Response createRoom(GameSettings settings, String login) throws TimeoutException;

    public abstract Response joinRoom(String roomName, String login) throws TimeoutException;

    public abstract Response leaveRoom(String login) throws TimeoutException;

    public abstract Response sitDown(String login) throws TimeoutException;

    public abstract Response standUp(String login) throws TimeoutException;

    public abstract Response reroll(boolean[] dice) throws TimeoutException;

    protected abstract boolean connect(Object serverLink);

    public abstract void addServerMessageListener(ServerMessageListener listener);
}
