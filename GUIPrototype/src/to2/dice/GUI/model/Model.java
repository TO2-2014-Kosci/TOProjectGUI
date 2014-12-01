package to2.dice.GUI.model;

import java.util.ArrayList;
import java.util.List;

import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.game.GameState;
import to2.dice.server.ConnectionProxy;

public class Model {
	private static int numberOfDice = 5;
	
	private ConnectionProxy connectionProxy;
	private ServerMessageContainer serverMessageContainer;
	private DiceApplication diceApplication;
	private String login = "";
	private List<GameInfo> roomList = new ArrayList<GameInfo>();
	private GameSettings gameSettings = null;
	private boolean sitting = false;
	private boolean[] selectedDice = new boolean[numberOfDice];
	private GameState gameState = new GameState();
	private int timer = 60;
	
	public Model(ConnectionProxy cp, ServerMessageContainer smc, DiceApplication da) {
		this.connectionProxy = cp;
		this.serverMessageContainer = smc;
		this.diceApplication = da;
	}
	
	
	public ConnectionProxy getConnectionProxy(){
		return connectionProxy;
	}


	public ServerMessageContainer getServerMessageContainer() {
		return serverMessageContainer;
	}

	public DiceApplication getDiceApplication() {
		return diceApplication;
	}

	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public List<GameInfo> getRoomList() {
		return roomList;
	}


	public void setRoomList(List<GameInfo> roomList) {
		this.roomList = roomList;
	}


	public GameSettings getGameSettings() {
		return gameSettings;
	}


	public void setGameSettings(GameSettings gameSettings) {
		this.gameSettings = gameSettings;
	}


	public boolean isSitting() {
		return sitting;
	}


	public void setSitting(boolean sitting) {
		this.sitting = sitting;
	}


	public boolean[] getSelectedDice() {
		return selectedDice;
	}


	public void setSelectedDice(boolean[] selectedDice) {
		this.selectedDice = selectedDice;
	}


	public GameState getGameState() {
		return gameState;
	}


	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}


	public int getTimer() {
		return timer;
	}


	public void setTimer(int timer) {
		this.timer = timer;
	}
}
