package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.game.GameState;
import to2.dice.server.ServerMessageListener;

public class LobbyController extends Controller implements ServerMessageListener{
	public LobbyController(Model model) {
		super(model);
	}
	
	public void onGameStateChange(GameState gameState){
		
	}
	
	public void clickedLeaveButton() {
		
	}
	
	public void clickedSitDownStandUpButton() {
		
	}
}
