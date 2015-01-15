package to2.dice.server;

import to2.dice.game.GameState;
import to2.dice.controllers.GameController;

/**
 * Created by Fan on 2014-11-16.
 */
public interface GameServer {
    /**
     * Sends specified state of the game to all players associated with the controller
     * @param sender Controller which sends the game state
     * @param state Game state to be received by players
     */
    public void sendToAll(GameController sender, GameState state);
    
    /**
     * Sends information to server that the game has ended
     * @param sender Controller of recently finished game
     */
    public void finishGame(GameController sender);
}