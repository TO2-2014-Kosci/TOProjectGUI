package to2.dice.GUI.views;

import java.awt.Canvas;

import com.jme3.app.SimpleApplication;

import to2.dice.GUI.controllers.GameAnimController;

//TODO inheritance
public class GameAnimation extends SimpleApplication{
	private GameAnimController gameAnimController;
	
	public GameAnimation() {
	}
	
	public void setController(GameAnimController gameAnimController) {
		this.gameAnimController = gameAnimController;
	}

	public Canvas getCanvas(){
		return this.getCanvas();
	}

	@Override
	public void simpleInitApp() {
		// TODO Auto-generated method stub
		
	}
}
