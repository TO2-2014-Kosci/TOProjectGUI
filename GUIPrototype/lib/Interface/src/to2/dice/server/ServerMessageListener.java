package to2.dice.server;

import to2.dice.game.GameState;

/**
 * Created by Fan on 2014-11-17.
 */
public interface ServerMessageListener {
    /**
     * Handler called in case new game state has been received
     * @param state New state of the game
     */
    public void onGameStateChange(GameState state);
}