package to2.dice.ai;

import to2.dice.game.BotLevel;
import to2.dice.game.GameType;

public interface IBotFactory {
    public Bot createBot(BotLevel level, GameType gameType, int maxTime);
}
