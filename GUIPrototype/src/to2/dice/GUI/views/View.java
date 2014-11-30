package to2.dice.GUI.views;

import javax.swing.JPanel;

import to2.dice.GUI.controllers.Controller;
import to2.dice.GUI.model.Model;

public abstract class View extends JPanel {
	protected Controller controller;
	protected Model model;
	
	public View(Model model, Controller controller){
		this.model = model;
		this.controller = controller;
	}
	//TODO
	public void refresh(){
		
	}
}
