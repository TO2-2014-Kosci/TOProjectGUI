package to2.dice.GUI.controllers;

import java.util.concurrent.TimeoutException;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.View;
import to2.dice.game.GameState;
import to2.dice.game.Player;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

/*
 * jak sie wychodzi z tego okna trzeba ubiæ animacje
 */
public class GameController extends Controller implements ServerMessageListener {
	private GameAnimController gameAnimController;
	private Player lastPlayer;

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
				Response response = model.getConnectionProxy().standUp();
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
			try{
				Response response = model.getConnectionProxy().leaveRoom();
				if(response.isSuccess()){
					gameAnimController.destroy();
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
					view.showErrorDialog("Nie uda³o siê wyjœæ", "B³¹d wstawania", false);
				}
			}
			catch(TimeoutException e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
			}
		}
		
	}
	
	//TODO koniec gry
	public void onGameStateChange(GameState gameState){
		model.setGameState(gameState);
		if (lastPlayer != null && !lastPlayer.equals(model.getGameState().getCurrentPlayer())) {
			gameAnimController.refresh();
			lastPlayer = model.getGameState().getCurrentPlayer();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameAnimController.putAnotherDice();
		} else {
			if (lastPlayer == null) {
				lastPlayer = model.getGameState().getCurrentPlayer();
			}
		}
		model.setTimer(model.getGameSettings().getTimeForMove());
		model.getDiceApplication().refresh();
	}
	
	//TODO
	private void showEndDialog() {
		// jak sie wychodzi z tego okna trzeba ubiæ animacje, za pomoc¹ tego
//		gameAnimController.destroy();
		
	}
	
	
}
