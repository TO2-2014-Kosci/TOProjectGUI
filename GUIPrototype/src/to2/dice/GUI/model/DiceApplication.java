package to2.dice.GUI.model;

import to2.dice.GUI.views.View;


public class DiceApplication {
	private View view;
	
	
	public void refresh() {
		//TODO
	}
	
	public void setView(View view) {
		this.view = view;
		this.refresh();
		this.view.refresh();
	}
}
