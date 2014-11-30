package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.CreateGameView;
import to2.dice.GUI.views.GameListView;

public class GameListController extends Controller {

	public GameListController(Model model) {
		super(model);
	}

	//TODO
	public void refreshGameList(){
		
	}
	
	//TODO Needs to be checked
	public void clickedCreateGameButton(){
		CreateGameController newController = new CreateGameController(model);
		CreateGameView newView = new CreateGameView(model, newController);
		newController.setView(newView);
		model.diceApplication.setView(newView);
	}
	
	//TODO
	public void clickedJoinGameButton(){
		GameListView glv = (GameListView) view;
		//model.getConnectionProxy().joinRoom(glv.getSelectedGame()., model.login);
	}
}
