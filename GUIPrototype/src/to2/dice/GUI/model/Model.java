package to2.dice.GUI.model;

import java.util.List;

import to2.dice.game.GameInfo;
import to2.dice.game.GameSettings;
import to2.dice.server.ConnectionProxy;

public class Model {
	private ConnectionProxy connectionProxy;
	public String login;
	public ServerMessageContainer serverMessageContainer;
	public DiceApplication diceApplication;
	public List<GameInfo> roomList;
	public GameInfo gameInfo;
	public GameSettings gameSettings;
	public boolean sitting;
	public boolean[] selectedDice;
	
	
	public ConnectionProxy getConnectionProxy(){
		return connectionProxy;
	}
}
