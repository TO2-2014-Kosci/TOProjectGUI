package to2.dice.controllers;

import to2.dice.game.GameInfo;
import to2.dice.messaging.GameAction;
import to2.dice.messaging.Response;

public interface GameController {
    public GameInfo getGameInfo();
    public Response handleGameAction(GameAction action);
}