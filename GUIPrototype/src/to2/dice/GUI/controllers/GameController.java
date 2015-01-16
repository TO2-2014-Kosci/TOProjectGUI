package to2.dice.GUI.controllers;

import java.util.concurrent.TimeoutException;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.View;
import to2.dice.game.GameState;
import to2.dice.game.Player;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

/*
 * jak sie wychodzi z tego okna trzeba ubi� animacje
 */
public class GameController extends Controller implements ServerMessageListener {
	private GameAnimController gameAnimController;
	private Player lastPlayer = null;
	private int lastRound;

	public GameController(Model model, GameAnimController gameAnimController) {
		super(model);
		this.gameAnimController = gameAnimController;
	}

	public void rerollDice() {
		try {
			Response response = model.getConnectionProxy().reroll(model.getSelectedDice());
			if (response.isSuccess()) {
				model.setSelectedDice(new boolean[model.getGameSettings().getDiceNumber()]);
			} else {
				view.showErrorDialog(response.message, "B��d wychodzenia", false);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
			view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
		}
	}

	public void clickedStandUpLeaveButton() {
		if (model.isSitting()) {
			try {
				Response response = model.getConnectionProxy().standUp();
				if (response.isSuccess()) {
					model.setSitting(false);
					view.refresh();
				} else {
					view.showErrorDialog("Nie uda�o si� wsta�", "B��d wstawania", false);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		} else {
			try {
				Response response = model.getConnectionProxy().leaveRoom();
				if (response.isSuccess()) {
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
					view.showErrorDialog("Nie uda�o si� wyj��", "B��d wstawania", false);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}

	}

	// TODO koniec gry
	public void onGameStateChange(GameState gameState) {
		model.setGameState(gameState);
		if (lastPlayer == null) {
			// pierwszy gamestate
			// pocz�tek gry lub wbili�my do trwaj�cej gry
			lastPlayer = gameState.getCurrentPlayer(); // potencjalnie moze to
														// by� nasza tura. Ale
														// tym zajmuje si� ju�
														// refresh od GameView
			if (model.isMyTurn()) {
				// teraz jest nasza tura
				gameAnimController.hideAnotherDice();
			} else {
				gameAnimController.showAnotherDice();
				gameAnimController.putAnotherDice(gameState.getCurrentPlayer().getDice());
			}
			for (Player p : gameState.getPlayers()) {
				if (p.getName().equals(model.getLogin())) {
					gameAnimController.putUserDice(p.getDice());
					break;
				}
			}
			lastRound = 0;
		}
		// kolejny gamestate
		if (!gameState.isGameStarted()) {
			// koniec gry
		} else if (gameState.getCurrentPlayer() == null) {
			// koniec rundy
			for (Player p : gameState.getPlayers()) {
				if (p.equals(lastPlayer)) {
					if (p.getName().equals(model.getLogin())) {
						gameAnimController.shakeUserDice(p.getDice());
					} else {
						gameAnimController.shakeAnotherDice(p.getDice());
					}
				}
			}
			lastPlayer = gameState.getCurrentPlayer();
		} else if (!lastPlayer.equals(gameState.getCurrentPlayer())) {
			// kto� przerzuci�. Trzeba wy�wietli� animacj�
			for (Player p : gameState.getPlayers()) {
				if (p.equals(lastPlayer)) {
					if (p.getName().equals(model.getLogin())) {
						gameAnimController.shakeUserDice(p.getDice());
					} else {
						gameAnimController.shakeAnotherDice(p.getDice());
					}
				}
			}
			lastPlayer = gameState.getCurrentPlayer();
			// TODO lepiej to rozwiazac. Aktualnie blokujemy watek odbierania
			// wiadomosci
//			 try {
//			 Thread.sleep(5000);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			if (model.isMyTurn()) {
				// teraz jest nasza tura
				gameAnimController.hideAnotherDice();
				gameAnimController.showText("Twoja tura", 0);
			} else {
				gameAnimController.showAnotherDice();
				gameAnimController.putAnotherDice(gameState.getCurrentPlayer().getDice());
				gameAnimController.showText("", 0);
			}
			for (Player p : gameState.getPlayers()) {
				if (p.getName().equals(model.getLogin())) {
					gameAnimController.putUserDice(p.getDice());
					break;
				}
			}
			model.setTimer(model.getGameSettings().getTimeForMove());
		} else {
			// TODO �adna zmiana <- chyba
		}
		model.getDiceApplication().refresh();
	}

	// TODO
	private void showEndDialog() {
	}

}
