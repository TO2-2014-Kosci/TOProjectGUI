package to2.dice.server;

import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.messaging.Response;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Fan
 * @version 0.3
 */
public interface ConnectionProxy {

    public Response login(String login) throws TimeoutException;

    public List<GameInfo> getRoomList() throws TimeoutException;

    public Response createRoom(GameSettings settings) throws TimeoutException;

    public Response joinRoom(String roomName) throws TimeoutException;

    public Response leaveRoom() throws TimeoutException;

    public Response sitDown() throws TimeoutException;

    public Response standUp() throws TimeoutException;

    public Response reroll(boolean[] dice) throws TimeoutException;

    public void addServerMessageListener(ServerMessageListener listener);
}
