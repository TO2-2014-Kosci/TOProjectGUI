package to2.dice.GUI.views;

import javax.swing.JOptionPane;
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
	public void refresh(){};

	public void showErrorDialog(String message,String title,Boolean closeApp){
		JOptionPane.showMessageDialog(null,message,title,JOptionPane.ERROR_MESSAGE);
		if(closeApp){
			System.exit(0);
		}
	}
}
