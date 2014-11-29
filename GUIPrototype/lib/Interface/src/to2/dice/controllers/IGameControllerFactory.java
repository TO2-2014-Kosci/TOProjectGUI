package to2.dice.controllers;

import to2.dice.game.GameSettings;
import to2.dice.server.GameServer;

public interface IGameControllerFactory {
    public GameController createGameControler(GameServer server, GameSettings settings, String creator);
}
