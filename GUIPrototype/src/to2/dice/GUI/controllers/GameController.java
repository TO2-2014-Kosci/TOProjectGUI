package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.View;
import to2.dice.common.GameState;
import to2.dice.server.ServerMessageListener;


public class GameController extends Controller implements ServerMessageListener {
	private GameAnimController gameAnimController;

	public GameController(Model mode, View view) {
		super(mode, view);
	}

	//TODO
	public void rerollDice(){
	  
	}
	
	//TODO 
	public void onGameStateChange(GameState gameState){
		
	}
	
	//TODO
	private void showEndDialog() {
		
	}
}
