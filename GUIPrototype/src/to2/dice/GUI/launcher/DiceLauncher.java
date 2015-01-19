package to2.dice.GUI.launcher;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeSystem;

import to2.dice.GUI.controllers.LoginController;
import to2.dice.GUI.model.DiceApplication;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.model.ServerMessageContainer;
import to2.dice.GUI.views.LoginView;
import to2.dice.GUI.views.View;
import to2.dice.messaging.RemoteConnectionProxy;
import to2.dice.server.ConnectionProxy;

public class DiceLauncher {
	public static void main(String[] args) {
		AppSettings settings = new AppSettings(true);
		settings.setAudioRenderer(null);
		// Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
		JmeSystem.initialize(settings);
		ServerMessageContainer smc = new ServerMessageContainer();
		ConnectionProxy cp;
		try {
			cp = new RemoteConnectionProxy(loadIPFromFile("conf.cnf"), smc);
			DiceApplication da = new DiceApplication();
			Model model = new Model(cp, smc, da);
			LoginController newController = new LoginController(model);
			View newView = new LoginView(model, newController);
			newController.setView(newView);
			model.getGameAnimation();
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						model.getConnectionProxy().logout();
					} catch (TimeoutException e) {
						e.printStackTrace();
					}
					System.out.println("Koniec");
				}

			}));
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					da.setView(newView);
					da.setVisible(true);
				}
			});
		} catch (ConnectException e) {
			e.printStackTrace();
		}
	}

	private static String loadIPFromFile(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			return line;
		} catch (IOException e) {
			e.printStackTrace();
			return "localhost";
		}
	}
}
