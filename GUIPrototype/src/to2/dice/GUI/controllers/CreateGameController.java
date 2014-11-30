package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.CreateGameView;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.LobbyView;
import to2.dice.GUI.views.View;
import to2.dice.game.GameSettings;
import to2.dice.messaging.Response;

public class CreateGameController extends Controller {

	public CreateGameController(Model model) {
		super(model);
	}
	//TODO
	public void clickedCreateGameButton(){
		CreateGameView cgv = (CreateGameView) view;
		GameSettings gameSettings = cgv.getGameSettings();
		//TODO validation?
		try{
			Response response = model.getConnectionProxy().createRoom(gameSettings, model.login);
			if(response.isSuccess()){
				model.gameSettings = gameSettings;
				LobbyController newController = new LobbyController(model);
				LobbyView newView = new LobbyView(model, newController);
				newController.setView(newView);
				model.diceApplication.setView(newView);
				model.sitting = false;
			}
			else{
				view.showErrorDialog("Nie uda³o siê utworzyæ gry", "B³¹d tworzenia gry", false);
			}
		}
		catch(Exception e){
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}
	}
	public void clickedReturnButton(){
		GameListController newController = new GameListController(model);
		GameListView newView = new GameListView(model,newController);
		newController.setView(newView);
		model.diceApplication.setView(newView);
	}
}
