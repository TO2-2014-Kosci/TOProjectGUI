package to2.dice.controllers;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.messaging.GameAction;
import to2.dice.messaging.Response;
import to2.dice.server.GameServer;

public class PokerGameController extends GameController {
    public PokerGameController(GameServer server, GameSettings settings, String creator) {
        super(server, settings, creator);
    }

    @Override
    public Response handleGameAction(GameAction action) {
        throw new NotImplementedException();
    }

    @Override
    public GameInfo getGameInfo() {
        throw new NotImplementedException();
    }
}
