package to2.dice.GUI.launcher;

import java.awt.EventQueue;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeSystem;

import to2.dice.GUI.controllers.LoginController;
import to2.dice.GUI.model.DiceApplication;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.model.ServerMessageContainer;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.GUI.views.LoginView;
import to2.dice.GUI.views.View;
import to2.dice.messaging.RemoteConnectionProxy;
import to2.dice.server.ConnectionProxy;


// TODO odczyt ustawien z pliku
public class DiceLauncher {
	public static void main(String[] args) {
		AppSettings settings = new AppSettings(true);
		settings.setAudioRenderer(null);
//		Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
		JmeSystem.initialize(settings);
		ServerMessageContainer smc = new ServerMessageContainer();
		
		ConnectionProxy cp;
		try {
			cp = new RemoteConnectionProxy("localhost", smc);
			DiceApplication da = new DiceApplication();
			Model model = new Model(cp, smc, da);
			LoginController newController = new LoginController(model);
			View newView = new LoginView(model, newController);
			newController.setView(newView);
			model.getGameAnimation();
			
			EventQueue.invokeLater(new Runnable(){
				
				public void run(){
					da.setView(newView);
					da.setVisible(true);
				}
			});
		} catch (ConnectException e) {
			e.printStackTrace();
		}
	}
}
