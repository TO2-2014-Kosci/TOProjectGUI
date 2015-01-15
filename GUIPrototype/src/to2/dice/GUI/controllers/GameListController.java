package to2.dice.GUI.controllers;

import java.util.concurrent.TimeoutException;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.CreateGameView;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.GameView;
import to2.dice.GUI.views.LobbyView;
import to2.dice.GUI.views.View;
import to2.dice.game.GameInfo;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

public class GameListController extends Controller {

	public GameListController(Model model) {
		super(model);
	}

	public void refreshGameList(){
		try{
			model.setRoomList(model.getConnectionProxy().getRoomList());
			view.refresh();
		}
		catch(TimeoutException e){
			e.printStackTrace();
			view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);			
		}
	}
	
	public void clickedCreateGameButton(){
		CreateGameController newController = new CreateGameController(model);
		CreateGameView newView = new CreateGameView(model, newController);
		newController.setView(newView);
		model.getDiceApplication().setView(newView);
	}
	
	public void clickedJoinGameButton(){
		GameListView glv = (GameListView) view;
		GameInfo selectedGame = glv.getSelectedGame();
		if(selectedGame!=null){
			model.setGameSettings(selectedGame.getSettings());
			model.setTimer(selectedGame.getSettings().getTimeForMove());
			Controller newController;
			View newView;
			if (selectedGame.isGameStarted()) {
				GameAnimController gameAnimController = model.getGameAnimController();
				GameAnimation gameAnimation = model.getGameAnimation();
				newController = new GameController(model, gameAnimController);
				newView = new GameView(model, (GameController) newController, gameAnimation);
				newController.setView(newView);
				
			} else {
				newController = new LobbyController(model);
				newView = new LobbyView(model, (LobbyController) newController);
				newController.setView(newView);
			}
			model.getServerMessageContainer().setServerMessageListener((ServerMessageListener) newController);
			try{
				Response response = model.getConnectionProxy().joinRoom(model.getGameSettings().getName());
				if(response.isSuccess()){
					model.setSitting(false);
					model.getDiceApplication().setView(newView);
				}
				else{
					model.getServerMessageContainer().removeServerMessageListener();
					view.showErrorDialog("Nie uda�o si� do��czy� do gry","B��d do��czania",false);
				}
			}
			catch(TimeoutException e){
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}
	}
}
