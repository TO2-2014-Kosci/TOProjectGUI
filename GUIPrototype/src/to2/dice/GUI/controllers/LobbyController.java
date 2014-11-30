package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.game.GameState;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

public class LobbyController extends Controller implements ServerMessageListener{
	public LobbyController(Model model) {
		super(model);
	}
	
	public void onGameStateChange(GameState gameState){
		
	}
	
	public void clickedLeaveButton() {
		try{
			Response response = model.getConnectionProxy().leaveRoom(model.login);
			if(response.isSuccess()){
				GameListController newController = new GameListController(model);
				GameListView newView = new GameListView(model, newController);
				newController.setView(newView);
				model.diceApplication.setView(newView);
			}
			else{
				//TODO Could it happen?
			}
		}
		catch(Exception e){
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}

	}
	
	public void clickedSitDownStandUpButton() {
		
	}
}
