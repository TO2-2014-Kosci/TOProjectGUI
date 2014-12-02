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
				view.showErrorDialog(response.message, "B³¹d wychodzenia", false);
			}
		}
		catch(TimeoutException e){
			e.printStackTrace();
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
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
					view.showErrorDialog("Nie uda³o siê wstaæ","B³¹d wstawania", false);
				}
			}
			catch(TimeoutException e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
			}
		} else {
			// wychodzi
		}
		
	}
	
	//TODO 
	public void onGameStateChange(GameState gameState){
		
	}
	
	//TODO
	private void showEndDialog() {
		
	}
	
	
}
