package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.CreateGameView;
import to2.dice.GUI.views.GameListView;
import to2.dice.messaging.Response;

public class GameListController extends Controller {

	public GameListController(Model model) {
		super(model);
	}

	public void refreshGameList(){
		try{
			model.roomList = model.getConnectionProxy().getRoomList();
			view.refresh();
		}
		catch(Exception e){
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}
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
		model.gameSettings = glv.getSelectedGame();
		Game
		try{
			Response response = model.getConnectionProxy().joinRoom(glv.getSelectedGame().getName(), model.login);
			if(response.isSuccess()){
				
			}
			else{
				view.showErrorDialog("Nie uda³o siê do³¹czyæ do gry","B³¹d do³¹czania",false);
			}
		}
		catch(Exception e){
			view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
		}
		
		
		
	}
}
