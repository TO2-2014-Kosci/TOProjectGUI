package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.CreateGameView;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.GameView;
import to2.dice.GUI.views.LobbyView;
import to2.dice.GUI.views.View;
import to2.dice.game.GameSettings;
import to2.dice.messaging.Response;
import to2.dice.server.ServerMessageListener;

public class CreateGameController extends Controller {

	public CreateGameController(Model model) {
		super(model);
	}
	//TODO
	public void clickedCreateGameButton(){
		CreateGameView cgv = (CreateGameView) view;
		GameSettings gameSettings = cgv.getGameSettings();
		//TODO validation?
		if (isProper(gameSettings)) {
			model.setGameSettings(gameSettings);
			model.setTimer(gameSettings.getTimeForMove());
			Controller newController;
			View newView;
			newController = new LobbyController(model);
			newView = new LobbyView(model, (LobbyController) newController);
			newController.setView(newView);
			model.getServerMessageContainer().setServerMessageListener((ServerMessageListener) newController);
			try{
				Response response = model.getConnectionProxy().createRoom(gameSettings, model.getLogin());
				if(response.isSuccess()){
					model.setSitting(false);
					model.getDiceApplication().setView(newView);
				}
				else{
					model.getServerMessageContainer().setServerMessageListener((ServerMessageListener) newController);
					view.showErrorDialog("Nie uda³o siê utworzyæ gry", "B³¹d tworzenia gry", false);
				}
			}
			catch(Exception e){
				view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
			}
		} else {
			view.showErrorDialog("Proszê wprowadziæ poprawne dane", "B³¹d tworzenia gry", false);
		}
	}
	public void clickedReturnButton(){
		GameListController newController = new GameListController(model);
		GameListView newView = new GameListView(model,newController);
		newController.setView(newView);
		model.getDiceApplication().setView(newView);
	}
	
	private boolean isProper(GameSettings gameSettings) {
		if (gameSettings.getName().equals("")) {
			return false;
		}
		if (gameSettings.getMaxPlayers() == 0) {
			return false;
		}
		return true;
	}
}
