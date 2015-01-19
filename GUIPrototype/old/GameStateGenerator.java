package to2.dice.GUI.launcher;

import java.util.Random;

import to2.dice.game.Dice;
import to2.dice.game.GameSettings;
import to2.dice.game.GameState;
import to2.dice.game.Player;
import to2.dice.server.ServerMessageListener;

public class GameStateGenerator implements Runnable {
	public boolean generate;
	public boolean isStarted;
	public GameState currentGameState;
	public GameSettings gameSettings;
	public ServerMessageListener serverMessageListener;
	public String login;
	private Random r = new Random();

	public GameStateGenerator() {
		generate = false;
		isStarted = false;
		currentGameState = new GameState();
	}

	@Override
	public void run() {
		int i;
		while (true) {
			if (generate) {
				if (isStarted) {
					currentGameState.getCurrentPlayer().setDice(
							new Dice(new int[] { r.nextInt(6) + 1, r.nextInt(6) + 1, r.nextInt(6) + 1,
									r.nextInt(6) + 1, r.nextInt(6) + 1 }));
					System.out.print("Generuje ");
					for (int k : currentGameState.getCurrentPlayer().getDice().getDiceArray()) {
						System.out.print(k + " ");
					}
					System.out.println();
					for (int a = 0; a < currentGameState.getPlayersNumber(); a++) {
						if (currentGameState.getCurrentPlayer().equals(currentGameState.getPlayers().get(a))) {
							if (a + 1 == currentGameState.getPlayersNumber()) {
								currentGameState.setCurrentPlayer(currentGameState.getPlayers().get(0));
							} else {
								currentGameState.setCurrentPlayer(currentGameState.getPlayers().get(a + 1));
							}
							break;
						}
					}
					if (r.nextInt() % 25 == 0) {
						currentGameState.getCurrentPlayer()
								.setScore(currentGameState.getCurrentPlayer().getScore() + 1);
						for (Player p : currentGameState.getPlayers()) {
							p.setDice(new Dice(new int[] { r.nextInt(6) + 1, r.nextInt(6) + 1, r.nextInt(6) + 1,
									r.nextInt(6) + 1, r.nextInt(6) + 1 }));
						}
						currentGameState.setCurrentRound(currentGameState.getCurrentRound() + 1);
						currentGameState.setCurrentPlayer(currentGameState.getPlayers().get(0));
					}
					if (r.nextInt() % 50 == 0) {
						currentGameState.setGameStarted(false);
					}
					if (currentGameState.getCurrentPlayer().getName().equals(login)) {
						generate = false;
					}
				} else if (currentGameState.getPlayers().size() == gameSettings.getMaxPlayers()) {
					isStarted = true;
					currentGameState.setGameStarted(true);
					for (Player p : currentGameState.getPlayers()) {
						p.setDice(new Dice(new int[] { r.nextInt(6) + 1, r.nextInt(6) + 1, r.nextInt(6) + 1,
								r.nextInt(6) + 1, r.nextInt(6) + 1 }));
					}
					currentGameState.setCurrentRound(1);
					currentGameState.setCurrentPlayer(currentGameState.getPlayers().get(0));
				} else {
					i = r.nextInt(gameSettings.getMaxPlayers());
					if (r.nextBoolean()) {
						currentGameState.addPlayer(new Player("Kot" + i, (i % 2) == 0, 5));
					} else {
						currentGameState.removePlayerWithName("Kot" + i);
					}
				}
				System.out.println("Generuje nowy gameState");
				new Thread(new Runnable() {

					@Override
					public void run() {
						serverMessageListener.onGameStateChange(currentGameState);

					}
				}).start();
				// serverMessageListener.onGameStateChange(currentGameState);
			}
			try {
				if (isStarted) {
					Thread.sleep(7000);
				} else {
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
