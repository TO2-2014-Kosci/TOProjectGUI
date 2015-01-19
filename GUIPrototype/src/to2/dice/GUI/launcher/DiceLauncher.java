package to2.dice.GUI.launcher;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;
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
import to2.dice.game.GameState;
import to2.dice.game.NGameState;
import to2.dice.messaging.RemoteConnectionProxy;
import to2.dice.server.ConnectionProxy;

// TODO odczyt ustawien z pliku
public class DiceLauncher {
	public static void main(String[] args) {
		AppSettings settings = new AppSettings(true);
		settings.setAudioRenderer(null);
		// Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
		JmeSystem.initialize(settings);
		ServerMessageContainer smc = new ServerMessageContainer();
		ConnectionProxy cp;
		try {
			cp = new RemoteConnectionProxy("localhost", smc);
	//		cp = new ConnectionProxyStub();
	//		cp.addServerMessageListener(smc);
			DiceApplication da = new DiceApplication();
			Model model = new Model(cp, smc, da);
			LoginController newController = new LoginController(model);
			View newView = new LoginView(model, newController);
			newController.setView(newView);
			model.getGameAnimation();
	
			// TODO
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						model.getConnectionProxy().logout();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
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
	
	private String loadIPFromFile(String fileName) throws IOException{
	     try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
	         String line = br.readLine();
	         return line;
	     }
	 }
}
