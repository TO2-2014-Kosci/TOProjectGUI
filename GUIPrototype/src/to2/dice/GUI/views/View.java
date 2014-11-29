package to2.dice.GUI.views;

import javax.swing.JPanel;

import to2.dice.GUI.controllers.Controller;
import to2.dice.GUI.model.Model;

public abstract class View extends JPanel {
	public Controller controller;
	private Model model;
	
	public View(Model model){
		this.model = model;
	}
	//TODO
	public void refresh(){
		
	}
	public void setController(Controller controller){
		this.controller = controller;
	}
	public Controller getController(){
		return controller;
	}
}
