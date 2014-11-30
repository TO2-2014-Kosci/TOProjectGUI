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
	public ServerMessageContainer serverMessageContainer;
	public DiceApplication diceApplication;
	public String login = "";
	public List<GameInfo> roomList = new ArrayList<GameInfo>();
	public GameSettings gameSettings = null;
	public boolean sitting = false;
	public boolean[] selectedDice = new boolean[numberOfDice];
	public GameState gameState = new GameState();
	
	public Model(ConnectionProxy cp, ServerMessageContainer smc, DiceApplication da) {
		this.connectionProxy = cp;
		this.serverMessageContainer = smc;
		this.diceApplication = da;
	}
	
	
	public ConnectionProxy getConnectionProxy(){
		return connectionProxy;
	}
}
