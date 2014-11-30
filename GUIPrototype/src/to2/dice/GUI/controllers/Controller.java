package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.View;

public abstract class Controller {
	protected Model model;
	protected View view;
	
	public Controller(Model model){
		this.model=model;
	}
	
	public void setView(View view) {
		this.view = view;
		
	}
}
