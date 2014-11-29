package to2.dice.GUI.launcher;

import java.awt.EventQueue;

import to2.dice.GUI.controllers.Controller;
import to2.dice.GUI.controllers.LoginController;
import to2.dice.GUI.model.DiceApplication;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.model.ServerMessageContainer;
import to2.dice.GUI.views.LoginView;
import to2.dice.GUI.views.View;

public class DiceLauncher {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				new ServerMessageContainer();
				//new ConnectionProxy();
				DiceApplication app = new DiceApplication();
				Model appModel = new Model();
				View appView = new LoginView(appModel);
				Controller appController = new LoginController(appModel,appView);
				appView.setController(appController);
				app.setView(appView);
			}
		});
	}

}
