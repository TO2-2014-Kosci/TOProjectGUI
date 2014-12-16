package to2.dice.GUI.launcher;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.system.AppSettings;
import com.jme3.system.JmeSystem;

import to2.dice.GUI.controllers.LoginController;
import to2.dice.GUI.model.DiceApplication;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.model.ServerMessageContainer;
import to2.dice.GUI.views.LoginView;
import to2.dice.GUI.views.View;
import to2.dice.game.BotLevel;
import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.game.GameState;
import to2.dice.game.GameType;
import to2.dice.game.Player;
import to2.dice.messaging.Response;
import to2.dice.server.ConnectionProxy;
import to2.dice.server.ServerMessageListener;

public class DiceLauncher {
	public static void main(String[] args) {
		AppSettings settings = new AppSettings(true);
		settings.setAudioRenderer(null);
		Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
		JmeSystem.initialize(settings);
		ServerMessageContainer smc = new ServerMessageContainer();
		
		ConnectionProxy cp = new ConnectionProxy() {
			Random r = new Random();
			@Override
			public Response standUp() {
				// TODO Auto-generated method stub
				System.out.println("Wstaje");
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response sitDown() {
				System.out.println("Siada");
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response reroll(boolean[] dice) {
				// TODO Auto-generated method stub
				System.out.println("Przerzuca");
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response login(String login) {
				// TODO Auto-generated method stub
				System.out.println("Loguje " + login);
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response leaveRoom() {
				// TODO Auto-generated method stub
				System.out.println("Wychodzi");
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response joinRoom(String roomName) {
				// TODO Auto-generated method stub
				System.out.println("Wbija "+roomName);
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public List<GameInfo> getRoomList() {
				// TODO Auto-generated method stub
				System.out.println("Chce listê pokoi");
				List<GameInfo> list = new ArrayList<>();
				GameState gameState = new GameState();
				gameState.setGameStarted(false);
				gameState.setPlayers(new ArrayList<>());
				int k = ((r.nextInt(15)) + 5);
				for (int i = 0; i < k; i++) {
					Map<BotLevel, Integer> botsNumber = new HashMap<>();
					gameState.setGameStarted((r.nextInt() % 2)==0);
					botsNumber.put(BotLevel.HARD, r.nextInt(15));
					botsNumber.put(BotLevel.EASY, r.nextInt(15));
					list.add(new GameInfo(new GameSettings(GameType.POKER, 5, "gra" + i, r.nextInt(15), r.nextInt(15), r.nextInt(15), r.nextInt(15), botsNumber), gameState));
				}
				return list;
			}
			
			@Override
			public Response createRoom(GameSettings settings) {
				// TODO Auto-generated method stub
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public void addServerMessageListener(ServerMessageListener listener) {
				// TODO Auto-generated method stub
				
			}
		};
		DiceApplication da = new DiceApplication();
		Model model = new Model(cp, smc, da);
		LoginController newController = new LoginController(model);
		View newView = new LoginView(model, newController);
		newController.setView(newView);
		
		//TODO to remove
		for (int i = 0; i < 10; i++) {
			model.getGameState().addPlayer(new Player("Kot" + i, (i%2)==0, 5));
		}
		//TODO koniec remove
			
		EventQueue.invokeLater(new Runnable(){
			
			public void run(){
				da.setView(newView);
				da.setVisible(true);
			}
		});
	}
}
