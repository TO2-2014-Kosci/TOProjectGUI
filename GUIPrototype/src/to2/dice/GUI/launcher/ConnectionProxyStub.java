package to2.dice.GUI.launcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import to2.dice.game.BotLevel;
import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.game.GameState;
import to2.dice.game.GameType;
import to2.dice.game.Player;
import to2.dice.messaging.Response;
import to2.dice.server.ConnectionProxy;
import to2.dice.server.ServerMessageListener;

public class ConnectionProxyStub implements ConnectionProxy {
	private GameStateGenerator gameStateGenerator;
	private Thread generatorThread;
	private ServerMessageListener serverMessageListener;
	private List<GameInfo> list;
	private String login;
	
	public ConnectionProxyStub() {
		gameStateGenerator = new GameStateGenerator();
		generatorThread = new Thread(gameStateGenerator);
		generatorThread.start();
	}
	
	private Random r = new Random();
	@Override
	public Response standUp() {
		System.out.println("Wstaje");
		gameStateGenerator.currentGameState.removePlayerWithName(login);
		return new Response(Response.Type.SUCCESS);
	}
	
	@Override
	public Response sitDown() {
		System.out.println("Siada");
		gameStateGenerator.currentGameState.addPlayer(new Player(login, false, 5));
		return new Response(Response.Type.SUCCESS);
	}
	
	@Override
	public Response reroll(boolean[] dice) {
		System.out.println("Przerzuca");
		gameStateGenerator.generate = true;
		return new Response(Response.Type.SUCCESS);
	}
	
	@Override
	public Response login(String login) {
		this.login = login;
		System.out.println("Loguje " + login);
		gameStateGenerator.login = login;
		return new Response(Response.Type.SUCCESS);
	}
	
	@Override
	public Response leaveRoom() {
		this.standUp();
		System.out.println("Wychodzi");
		
		gameStateGenerator.generate = false;
		gameStateGenerator.isStarted = false;
		return new Response(Response.Type.SUCCESS);
	}
	
	@Override
	public Response joinRoom(String roomName) {
		System.out.println("Wbija " + roomName);
		for (GameInfo gi: list) {
			int number = 0;
			for (int i: gi.getSettings().getBotsNumbers().values()) {
				number += i;
			}
			if (gi.getSettings().getName().equals(roomName)) {
				if (gi.isGameStarted()) {
					number += gi.getSettings().getMaxHumanPlayers();
				}
				gameStateGenerator.currentGameState = new GameState();
				for (int i = 0; i < gi.getPlayersNumber() + number; i++) {
					gameStateGenerator.currentGameState.addPlayer(new Player("Bot Kot" + i, (i%2)==0, 5));
				}
				gameStateGenerator.gameSettings = gi.getSettings();
			}
		}
		gameStateGenerator.generate = true;
		return new Response(Response.Type.SUCCESS);
	}
	
	@Override
	public List<GameInfo> getRoomList() {
		System.out.println("Chce listê pokoi");
		list = new ArrayList<>();
		GameState gameState = new GameState();
		gameState.setGameStarted(false);
		gameState.setPlayers(new ArrayList<>());
		int k = ((r.nextInt(15)) + 5);
		for (int i = 0; i < k; i++) {
			Map<BotLevel, Integer> botsNumber = new HashMap<>();
			gameState.setGameStarted((r.nextInt() % 2)==0);
			botsNumber.put(BotLevel.HARD, r.nextInt(15));
			botsNumber.put(BotLevel.EASY, r.nextInt(15));
			list.add(new GameInfo(new GameSettings(GameType.POKER, 5, "gra" + i, r.nextInt(15), r.nextInt(15) + 20, r.nextInt(15), r.nextInt(15), botsNumber), gameState));
		}
		return list;
	}
	
	@Override
	public Response createRoom(GameSettings settings) {
		return new Response(Response.Type.SUCCESS);
	}
	
	@Override
	public void addServerMessageListener(ServerMessageListener listener) {
		serverMessageListener = listener;
		gameStateGenerator.serverMessageListener = serverMessageListener;
	}
}
