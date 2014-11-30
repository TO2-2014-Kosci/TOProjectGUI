package to2.dice.GUI.launcher;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import to2.dice.GUI.controllers.Controller;
import to2.dice.GUI.controllers.LoginController;
import to2.dice.GUI.model.DiceApplication;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.model.ServerMessageContainer;
import to2.dice.GUI.views.LoginView;
import to2.dice.GUI.views.View;
import to2.dice.game.BotLevel;
import to2.dice.game.Dice;
import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.game.GameState;
import to2.dice.game.GameType;
import to2.dice.messaging.Response;
import to2.dice.server.ConnectionProxy;
import to2.dice.server.ServerMessageListener;

public class DiceLauncher {
	public static void main(String[] args) {
		ServerMessageContainer smc = new ServerMessageContainer();
		ConnectionProxy cp = new ConnectionProxy(null, null) {
			
			@Override
			public Response standUp(String login) {
				// TODO Auto-generated method stub
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response sitDown(String login) {
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response reroll(Dice dice) {
				// TODO Auto-generated method stub
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response login(String login) {
				// TODO Auto-generated method stub
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response leaveRoom(String login) {
				// TODO Auto-generated method stub
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public Response joinRoom(String roomName, String login) {
				// TODO Auto-generated method stub
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			public List<GameInfo> getRoomList() {
				// TODO Auto-generated method stub
				List<GameInfo> list = new ArrayList<>();
				Map<BotLevel, Integer> botsNumber = new HashMap<>();
				botsNumber.put(BotLevel.HIGH, 10);
				botsNumber.put(BotLevel.LOW, 10);
				GameState gameState = new GameState();
				gameState.setGameStarted(false);
				gameState.setPlayers(new ArrayList<>());
				list.add(new GameInfo(new GameSettings(GameType.POKER, 5, "dupa", 10, 10, 10, 10, botsNumber), gameState));
				list.add(new GameInfo(new GameSettings(GameType.NMUL, 5, "dupa1", 40, 10, 10, 10, botsNumber), gameState));
				list.add(new GameInfo(new GameSettings(GameType.NPLUS, 5, "dupa2", 20, 10, 10, 10, botsNumber), gameState));
				return list;
			}
			
			@Override
			public Response createRoom(GameSettings settings, String login) {
				// TODO Auto-generated method stub
				return new Response(Response.Type.SUCCESS);
			}
			
			@Override
			protected boolean connect(Object serverLink) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public void addServerMessageListener(ServerMessageListener listener) {
				// TODO Auto-generated method stub
				
			}
		};
		DiceApplication app = new DiceApplication();
		Model appModel = new Model(cp, smc, app);
		LoginController appController = new LoginController(appModel);
		View appView = new LoginView(appModel, appController);
		appController.setView(appView);
		EventQueue.invokeLater(new Runnable(){
			
			public void run(){
				app.setView(appView);
				app.setVisible(true);
			}
		});
	}

}
