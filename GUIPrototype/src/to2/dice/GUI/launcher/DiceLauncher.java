package to2.dice.GUI.launcher;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import to2.dice.GUI.controllers.Controller;
import to2.dice.GUI.controllers.LoginController;
import to2.dice.GUI.model.DiceApplication;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.model.ServerMessageContainer;
import to2.dice.GUI.views.LoginView;
import to2.dice.GUI.views.View;
import to2.dice.game.Dice;
import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
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
				// TODO Auto-generated method stub
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
				return new ArrayList<GameInfo>();
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
