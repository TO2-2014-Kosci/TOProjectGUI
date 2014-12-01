package to2.dice.GUI.controllers;

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
		catch(Exception e){
			e.printStackTrace();
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}
	}
	
	//TODO
	public void clickedStandUpLeaveButton() {
		
	}
	
	//TODO 
	public void onGameStateChange(GameState gameState){
		
	}
	
	//TODO
	private void showEndDialog() {
		
	}
	
	
}
