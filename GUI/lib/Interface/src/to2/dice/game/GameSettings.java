package to2.dice.game;


import java.util.Map;
import java.util.Map.Entry;

public class GameSettings {
    private GameType gameType;
    private int diceNumber;
    private String name;
    private int maxHumanPlayers;
    private int timeForMove;
    private int maxInactiveTurns;
    private int roundsToWin;
    private Map<BotLevel, Integer> botsNumbers;

    public GameSettings(GameType gameType, int diceNumber, String name, int maxHumanPlayers, int timeForMove, int maxInactiveTurns, int roundsToWin, Map<BotLevel, Integer> botsNumber) {
        this.gameType = gameType;
        this.diceNumber = diceNumber;
        this.name = name;
        this.maxHumanPlayers = maxHumanPlayers;
        this.timeForMove = timeForMove;
        this.maxInactiveTurns = maxInactiveTurns;
        this.roundsToWin = roundsToWin;
        this.botsNumbers = botsNumber;
    }

    public GameType getGameType() {
        return gameType;
    }

    public int getDiceNumber() {
        return diceNumber;
    }

    public String getName() {
        return name;
    }

    public int getMaxHumanPlayers() {
        return maxHumanPlayers;
    }

    public int getTimeForMove() {
        return timeForMove;
    }

    public int getMaxInactiveTurns() {
        return maxInactiveTurns;
    }

    public int getRoundsToWin() {
        return roundsToWin;
    }

    public Map<BotLevel, Integer> getBotsNumbers() {
        return botsNumbers;
    }

    public int getMaxPlayers() {

        int totalNumber = 0;
        for (Entry<BotLevel, Integer> entry : botsNumbers.entrySet()) {
            totalNumber += entry.getValue();
        }
        totalNumber += maxHumanPlayers;

        return totalNumber;
    }
}