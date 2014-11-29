package to2.dice.GUI.model;

import javax.swing.JFrame;

import to2.dice.GUI.views.View;


public class DiceApplication extends JFrame {
	private View view;
	
	
	public void refresh() {
		//TODO
	}
	
	public void setView(View view) {
		this.view = view;
		//Niby dlaczego dwa?
		this.refresh();
		this.view.refresh();
	}
}
