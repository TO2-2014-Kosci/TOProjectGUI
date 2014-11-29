package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameAnimation;

public class GameAnimController {
	private GameAnimation gameAnimation;
	private Model model;
	
	public GameAnimController(GameAnimation gameAnimation,Model model){
		this.gameAnimation = gameAnimation;
		this.model = model ;
	}
	
	//TODO
	 public boolean[] getSelectedDice(){
		return null;
	 }
}
