package to2.dice.GUI.controllers;

import java.util.concurrent.TimeoutException;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.View;
import to2.dice.game.GameState;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;


public class GameController extends Controller implements ServerMessageListener {
	private GameAnimController gameAnimController;

	public GameController(Model model, GameAnimController gameAnimController) {
		super(model);
		this.gameAnimController = gameAnimController;
	}

	public void rerollDice(){
		try{
			Response response = model.getConnectionProxy().reroll(model.getSelectedDice());
			if(response.isSuccess()){
				//TODO
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
	
	//TODO
	public void clickedStandUpLeaveButton() {
		if (model.isSitting()) {
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
		} else {
			try{
				Response response = model.getConnectionProxy().leaveRoom(model.getLogin());
				if(response.isSuccess()){
					model.setSitting(false);
					GameListController newController = new GameListController(model);
					model.getServerMessageContainer().removeServerMessageListener();
					model.setGameSettings(null);
					GameListView newView = new GameListView(model, newController);
					newController.setView(newView);
					newController.refreshGameList();
					model.getDiceApplication().setView(newView);
				}
				else{
					view.showErrorDialog("Nie uda�o si� wyj��","B��d wstawania", false);
				}
			}
			catch(TimeoutException e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}
		
	}
	
	//TODO 
	public void onGameStateChange(GameState gameState){
		
	}
	
	//TODO
	private void showEndDialog() {
		
	}
	
	
}
