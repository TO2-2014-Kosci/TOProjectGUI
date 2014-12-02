package to2.dice.GUI.controllers;

import java.util.concurrent.TimeoutException;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.GameView;
import to2.dice.game.GameState;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

public class LobbyController extends Controller implements ServerMessageListener{
	public LobbyController(Model model) {
		super(model);
	}
	
	//TODO
	public void onGameStateChange(GameState gameState){
		model.setGameState(gameState);
		if(gameState.isGameStarted()){
			GameAnimController animController = new GameAnimController(model);
			GameAnimation anim = new GameAnimation(model, animController);
			animController.setGameAnimation(anim);
			GameController newController = new GameController(model,animController);
			GameView newView = new GameView(model, newController, anim);
			newController.setView(newView);
			model.getServerMessageContainer().setServerMessageListener(newController);
			model.getDiceApplication().setView(newView);
			model.getDiceApplication().refresh();
			newController.onGameStateChange(gameState);
		}
	}
	
	public void clickedLeaveButton() {
		try{
			Response response = model.getConnectionProxy().leaveRoom(model.getLogin());
			if(response.isSuccess()){
				GameListController newController = new GameListController(model);
				model.getServerMessageContainer().removeServerMessageListener();
				model.setGameSettings(null);
				GameListView newView = new GameListView(model, newController);
				newController.setView(newView);
				newController.refreshGameList();
				model.getDiceApplication().setView(newView);
			}
			else{
				view.showErrorDialog(response.message, "B��d wychodzenia", false);
			}
		}
		catch(TimeoutException e){
			e.printStackTrace();
			view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
		}

	}
	
	public void clickedSitDownStandUpButton() {
		if(model.isSitting()==true){
			try{
				Response response = model.getConnectionProxy().standUp(model.getLogin());
				if(response.isSuccess()){
					model.setSitting(false);
					view.refresh();
				}
				else{
					view.showErrorDialog("Nie uda�o si� wsta�","B��d wstawania", false);
				}
			}
			catch(TimeoutException e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}
		else{
			try{
				Response response = model.getConnectionProxy().sitDown(model.getLogin());
				if(response.isSuccess()){
					model.setSitting(true);
					view.refresh();
				}
				else{
					view.showErrorDialog("Nie uda�o si� usi���","B��d siadania", false);
				}
			}
			catch(TimeoutException e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}
	}
}
