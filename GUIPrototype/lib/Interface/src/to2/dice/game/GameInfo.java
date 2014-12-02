package to2.dice.game;

public class GameInfo {
    private GameSettings settings;
    private boolean gameStarted;
    private int playersNumber;

    public GameInfo(GameSettings settings, GameState state) {
        this.settings = settings;
        this.gameStarted = state.isGameStarted();
        this.playersNumber = (state.getPlayers()).size();
    }

    public GameSettings getSettings() {
        return settings;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }
}