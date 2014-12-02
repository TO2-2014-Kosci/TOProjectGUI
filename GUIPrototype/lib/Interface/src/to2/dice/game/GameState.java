package to2.dice.game;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<Player> players = new ArrayList<Player>();
    private boolean gameStarted = false;
    private Player currentPlayer = null;
    private int currentRound = 0;


    public List<Player> getPlayers() {
        return players;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}