package to2.dice.controllers;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import to2.dice.controllers.ngames.CountingStrategy;
import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.messaging.GameAction;
import to2.dice.messaging.Response;
import to2.dice.server.GameServer;

public class NGameController extends GameController {
    private CountingStrategy strategy;
    public NGameController(GameServer server, GameSettings settings, String creator, CountingStrategy strategy){
        super(server, settings, creator);
        this.strategy = strategy;
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