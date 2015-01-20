package to2.dice.GUI.controllers;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.game.GameState;
import to2.dice.game.Player;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

public class GameController extends Controller implements ServerMessageListener {
	private static final int timeForAnimInMiliseconds = 1500;

	private GameAnimController gameAnimController;
	private Player lastPlayer = null;
	private int lastRound;

	public GameController(Model model, GameAnimController gameAnimController) {
		super(model);
		this.gameAnimController = gameAnimController;
		lastRound = -1;
	}

	public void rerollDice() {
		try {
			Response response = model.getConnectionProxy().reroll(model.getSelectedDice());
			if (response.isSuccess()) {

			} else {
				view.showErrorDialog(response.message, "B³¹d wychodzenia", false);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}
	}

	public void clickedStandUpLeaveButton() {
		if (model.isSitting()) {
			try {
				Response response = model.getConnectionProxy().standUp();
				if (response.isSuccess()) {
					model.setSitting(false);
					gameAnimController.hideBoxAndDice();
					view.refresh();
				} else {
					view.showErrorDialog("Nie uda³o siê wstaæ", "B³¹d wstawania", false);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
				view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
			}
		} else if (!model.isSitting()) {
			try {
				Response response = model.getConnectionProxy().leaveRoom();
				if (response.isSuccess()) {
					
					lastRound = -1;
					lastPlayer = null;
					model.setSitting(false);
					GameListController newController = new GameListController(model);
					model.getServerMessageContainer().removeServerMessageListener();
					model.setGameSettings(null);
					model.setGameState(new GameState());
					GameListView newView = new GameListView(model, newController);
					newController.setView(newView);
					newController.refreshGameList();
					model.getDiceApplication().setView(newView);
				} else {
					view.showErrorDialog(response.message, "B³¹d wstawania", false);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
				view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
			}
		}

	}

	// TODO koniec gry
	@Override
	public void onGameStateChange(GameState gameState) {
		model.setGameState(gameState);
		if (lastPlayer == null && lastRound == -1) {
			startGame(gameState);
		}
		if (model.isSitting() && checkKickout(gameState)) {
			model.setSitting(false);
			gameAnimController.hideBoxAndDice();
		}
		if (!gameState.isGameStarted()) {
			endGame(gameState);
		} else if (gameState.getCurrentPlayer() == null) {
			endTour(gameState);
		} else if (lastPlayer == null) {
			nextRound(gameState);
		} else if (!lastPlayer.equals(gameState.getCurrentPlayer())) {
			endTourNextTour(gameState);
		} else {
			// TODO nothing is changed
		}
		model.getDiceApplication().refresh();
	}

	private boolean checkKickout(GameState gameState) {
		for (Player p : gameState.getPlayers()) {
			if (p.getName().equals(model.getLogin())) {
				return false;
			}
		}
		return true;
	}

	private void endTourNextTour(GameState gameState) {
		endTour(gameState);
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				if (model.isMyTurn()) {
					gameAnimController.hideAnotherDice();
					gameAnimController.showText("Twoja tura", 25);
				} else {
					gameAnimController.showAnotherDice();
					gameAnimController.putAnotherDice(gameState.getCurrentPlayer().getDice());
					gameAnimController.hideText();
				}
				if (model.isSitting()) {
					for (Player p : gameState.getPlayers()) {
						if (p.getName().equals(model.getLogin())) {
							gameAnimController.putUserDice(p.getDice());
							break;
						}
					}
				}

			}
		}, timeForAnimInMiliseconds);
		model.setTimer(model.getGameSettings().getTimeForMove());
	}

	private void nextRound(GameState gameState) {
		// TODO pokazaæ ³adny napis na œrodku :)
		lastRound += 1;
		lastPlayer = gameState.getCurrentPlayer();
		if (model.isMyTurn()) {
			gameAnimController.hideAnotherDice();
		} else {
			gameAnimController.showAnotherDice();
			gameAnimController.putAnotherDice(gameState.getCurrentPlayer().getDice());
		}
		if (model.isSitting()) {
			for (Player p : gameState.getPlayers()) {
				if (p.getName().equals(model.getLogin())) {
					gameAnimController.putUserDice(p.getDice());
					break;
				}
			}
		}
	}

	private void endTour(GameState gameState) {
		for (Player p : gameState.getPlayers()) {
			if (p.equals(lastPlayer)) {
				if (p.getName().equals(model.getLogin())) {
					gameAnimController.shakeUserDice(p.getDice());
					model.setSelectedDice(new boolean[model.getGameSettings().getDiceNumber()]);
				} else {
					gameAnimController.shakeAnotherDice(p.getDice());
				}
			}
		}
		gameAnimController.hideText();
		lastPlayer = gameState.getCurrentPlayer();
	}

	private void startGame(GameState gameState) {
		model.setSelectedDice(new boolean[model.getGameSettings().getDiceNumber()]);
		lastPlayer = gameState.getCurrentPlayer();
		if (model.isMyTurn()) {
			gameAnimController.hideAnotherDice();
		} else {
			gameAnimController.showAnotherDice();
			gameAnimController.putAnotherDice(gameState.getCurrentPlayer().getDice());
		}
		if (model.isSitting()) {
			for (Player p : gameState.getPlayers()) {
				if (p.getName().equals(model.getLogin())) {
					gameAnimController.putUserDice(p.getDice());
					break;
				}
			}
		}
		lastRound = 1;
	}

	private void endGame(GameState gameState) {
		Player winner = gameState.getPlayers().get(0);
		for (Player p:gameState.getPlayers()) {
			if (p.getScore() == model.getGameSettings().getRoundsToWin()) {
				winner = p;
				break;
			}
		}
		String endGameString = "Koniec gry\n  Wygral  \n" + winner.getName();
		gameAnimController.showText(endGameString, 25);
		lastRound = -1;
		lastPlayer = null;
		model.setSitting(false);
	}

}
