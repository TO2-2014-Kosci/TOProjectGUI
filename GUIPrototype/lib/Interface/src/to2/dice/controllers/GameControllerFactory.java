package to2.dice.controllers;

import to2.dice.controllers.ngames.MulCountingStrategy;
import to2.dice.controllers.ngames.PlusCountingStrategy;
import to2.dice.game.GameSettings;
import to2.dice.game.GameType;
import to2.dice.server.GameServer;

public class GameControllerFactory {
    public GameController createGameControler(GameServer server, GameSettings settings, String creator){
        GameController gameController = null;
        GameType type = settings.getGameType();
        switch (type){
            case NPLUS:
                gameController = new NGameController(server, settings, creator, new PlusCountingStrategy());
                break;
            case NMUL:
                gameController = new NGameController(server, settings, creator, new MulCountingStrategy());
                break;
            case POKER:
                gameController = new PokerGameController(server, settings, creator);
                break;
            default:
//THROW SOMETHING HERE
                break;
        }
        return gameController;
    }
}