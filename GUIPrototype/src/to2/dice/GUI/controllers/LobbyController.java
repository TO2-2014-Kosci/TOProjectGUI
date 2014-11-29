package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.View;
import to2.dice.common.GameState;
import to2.dice.server.ServerMessageListener;

public class LobbyController extends Controller implements ServerMessageListener{
	private GameAnimController gameAnimController;
	public LobbyController(Model mode, View view) {
		super(mode, view);
	}
	
	public void onGameStateChange(GameState gameState){
		
	}
}
