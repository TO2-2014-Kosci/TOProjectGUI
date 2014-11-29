package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.View;

public abstract class Controller {
	private Model model;
	private View view;
	public Controller(Model mode,View view){
		this.model=model;
		this.view=view;
	}
}
