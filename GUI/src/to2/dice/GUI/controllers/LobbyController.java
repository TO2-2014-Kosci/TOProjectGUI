package to2.dice.GUI.controllers;

import java.util.concurrent.TimeoutException;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.GameView;
import to2.dice.game.GameState;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

public class LobbyController extends Controller implements ServerMessageListener {
	public LobbyController(Model model) {
		super(model);
	}

	@Override
	public void onGameStateChange(GameState gameState) {
		model.setGameState(gameState);
		if (gameState.isGameStarted()) {
			GameAnimation anim = model.getGameAnimation();
			GameController newController = new GameController(model, model.getGameAnimController());
			GameView newView = new GameView(model, newController, anim);
			newController.setView(newView);
			model.getServerMessageContainer().setServerMessageListener(newController);
			model.getDiceApplication().setView(newView);
			model.getDiceApplication().refresh();
			newController.onGameStateChange(gameState);
		} else {
			model.getDiceApplication().refresh();
		}
	}

	public void clickedLeaveButton() {
		try {
			Response response = model.getConnectionProxy().leaveRoom();
			if (response.isSuccess()) {
				GameListController newController = new GameListController(model);
				model.getServerMessageContainer().removeServerMessageListener();
				model.setGameSettings(null);
				model.setGameState(new GameState());
				GameListView newView = new GameListView(model, newController);
				newController.setView(newView);
				newController.refreshGameList();
				model.getDiceApplication().setView(newView);
			} else {
				view.showErrorDialog(response.message, "B³¹d wychodzenia", false);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}

	}

	public void clickedSitDownStandUpButton() {
		if (model.isSitting() == true) {
			standUp();
		} else {
			sitDown();
		}
	}

	private void standUp() {
		try {
			Response response = model.getConnectionProxy().standUp();
			if (response.isSuccess()) {
				model.setSitting(false);
				view.refresh();
			} else {
				view.showErrorDialog("Nie uda³o siê wstaæ", "B³¹d wstawania", false);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}
	}

	private void sitDown() {
		try {
			Response response = model.getConnectionProxy().sitDown();
			if (response.isSuccess()) {
				model.setSitting(true);
				view.refresh();
			} else {
				view.showErrorDialog(response.message, "B³¹d siadania", false);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}
	}
}
