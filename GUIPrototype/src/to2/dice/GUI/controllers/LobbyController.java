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
	
	//TODO
	public void onGameStateChange(GameState gameState){
		
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
				model.getDiceApplication().setView(newView);
			}
			else{
				//TODO Could it happen?
			}
		}
		catch(Exception e){
			e.printStackTrace();
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
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
					//TODO Could it happen?
					view.showErrorDialog("Nie uda³o siê wstaæ","B³¹d wstawania", false);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
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
					view.showErrorDialog("Nie uda³o siê usi¹œæ","B³¹d siadania", false);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
			}
		}
	}
}
